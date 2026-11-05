import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class KeywordSearch {

    /*
     * Keep each document in a single input split.
     * This allows the Mapper to count rows from the beginning of each file.
     */
    public static class WholeFileTextInputFormat extends TextInputFormat {
        @Override
        protected boolean isSplitable(
                org.apache.hadoop.mapreduce.JobContext context,
                Path filename) {
            return false;
        }
    }

    // Question 1.2: Mapper
    public static class SearchMapper
            extends Mapper<LongWritable, Text, Text, Text> {

        private Pattern keywordPattern;
        private String documentName;
        private long rowNumber;

        @Override
        protected void setup(Context context) throws IOException {
            String keyword = context.getConfiguration().get("search.keyword");

            if (keyword == null || keyword.trim().isEmpty()) {
                throw new IOException("A search keyword must be provided.");
            }

            // Treat the keyword as literal text and ignore letter case.
            keywordPattern = Pattern.compile(
                    Pattern.quote(keyword),
                    Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
            );

            FileSplit split = (FileSplit) context.getInputSplit();
            documentName = split.getPath().getName();
            rowNumber = 0;
        }

        @Override
        protected void map(LongWritable offset, Text value, Context context)
                throws IOException, InterruptedException {

            rowNumber++;
            String line = value.toString();
            Matcher matcher = keywordPattern.matcher(line);

            while (matcher.find()) {
                // Count Unicode characters before the match, starting at 1.
                int column = line.codePointCount(0, matcher.start()) + 1;

                context.write(
                        new Text(documentName),
                        new Text("Row " + rowNumber + ", Column " + column)
                );
            }
        }
    }

    // Question 1.3: Reducer
    public static class SearchReducer
            extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text document, Iterable<Text> locations,
                              Context context)
                throws IOException, InterruptedException {

            for (Text location : locations) {
                context.write(document, location);
            }
        }
    }

    // Question 1.4: Hadoop job configuration
    public static void main(String[] args) throws Exception {

        if (args.length != 3 || args[2].trim().isEmpty()) {
            System.err.println(
                    "Usage: KeywordSearch <input> <output> <keyword>"
            );
            System.exit(1);
        }

        Configuration config = new Configuration();
        config.set("search.keyword", args[2]);

        Job job = Job.getInstance(config, "Case-insensitive keyword search");
        job.setJarByClass(KeywordSearch.class);

        job.setInputFormatClass(WholeFileTextInputFormat.class);
        job.setMapperClass(SearchMapper.class);
        job.setReducerClass(SearchReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
