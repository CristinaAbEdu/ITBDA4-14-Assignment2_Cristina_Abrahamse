# Question 1: Hadoop MapReduce Keyword Search

This folder contains the Java project and supporting files for the
Hadoop MapReduce keyword search.

## Where to start

Open **KeywordSearch.java** to view the Java source code. It contains
the mapper, reducer and Hadoop job configuration for the keyword search.

The program searches the input documents for a keyword supplied when
the job is run. The search is case-insensitive and reports the document
name, row and column of each match.

## Files and folders

- **KeywordSearch.java**: Java source code for the MapReduce program.
- **KeywordSearch.jar**: Compiled program used to run the Hadoop job.
- **map_reduce_test_docs**: Input text documents used for testing.
- **Output folders**: Results produced by demonstration searches. Open
  the `part-r-00000` file within an output folder to view its results.
- **.class files**: Compiled Java classes generated from the source code.

The project documentation contains the logical layout diagram,
explanation and demonstration evidence for Question 1.
