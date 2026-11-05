# ITBDA4-14: Big Data Analytics — Project 2

This repository contains the project documentation, code, notebooks
and datasets for Questions 1–4.

## Question 1: Hadoop MapReduce

Open the **Question_1_Hadoop_MapReduce** folder for the Java source code,
test documents and demonstration outputs. Start with
`KeywordSearch.java` to review the mapper, reducer and configuration.
The folder's README explains the supporting files.

The project documentation PDF contains the Question 1.1 diagram and
explanation, as well as the demonstration evidence.

## Question 2: News topic modelling

Open the **Notebooks** folder and select the Question 2 R notebook.
It contains the news preprocessing, LDA topic modelling and
visualisations. The **sa_news** folder contains its input documents.

## Question 3: Web text summarisation

Open the **Notebooks** folder and select the Question 3 R notebook.
It contains the extraction, preprocessing and summarisation of text
from Books to Scrape. The project documentation PDF includes the
visualisations and findings.

### Running the PostgreSQL queries

To reproduce the Question 4.3 results:

1. Open PostgreSQL using pgAdmin and connect to a database.
2. Use the Query Tool to create a table:

   ```sql
   CREATE TABLE clean_books (
       title TEXT,
       price_gbp NUMERIC(10, 2),
       rating INTEGER,
       availability TEXT
   );
   ```

3. Import **clean_books.csv** into the `clean_books` table using
   pgAdmin's Import/Export Data option. Select CSV format, a comma
   delimiter and enable the header option.
4. Open **Question4_queries.sql** in the Query Tool and run each query.

## Running the project

Download the repository files to your computer to run the code.
Questions 2–4 require R and the packages used in the notebooks.
Question 1 requires Java and Hadoop. The Question 4 SQL queries
require PostgreSQL and the cleaned CSV imported into a table.

The **sa_news** folder should remain alongside the Question 2 notebook
so that its relative file path can locate the news documents.

## Project documentation

The PDF on the repository's main page contains the project
documentation, selected screenshots, explanations and references.
