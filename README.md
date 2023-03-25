# SQL-Database-Simulation-Java

In this program, we will create a simplified in-memory database (databaseSimulator).
The interface information of the class(s) you are expected to develop is defined below.

– A method of the databaseSimulator class like void createTable(String createTableCommand) must exist. 
With this method, the database object creates a table by reading a file from the disk.

1) File read commands

CREATE TABLE FROM PathToFile
– The databaseSimulator class must have a method such as String[] query(String query). This method
Thanks to this, queries will be run on the database tables read and the results produced will be returned.
will be returned.

2) Data Query

SELECT singleColumnName FROM tableName; //Lists only one column from the table
SELECT commaSeparatedColumnList FROM tableName; //lists the desired columns
SELECT * FROM tableName; //Lists all columns in the table

3) Querying Data by Filtering

SELECT columnOrColumnFamily FROM tableName WHERE columnName=SomeValue;
//Find the row(s) that satisfies the condition to the right of the WHERE statement in the given table and the given column
or return the values of the columns.
– The databaseSimulator class must have a method named string printSchema(String tableName) and its name
should print the list of column names of the given table to the screen.

Parser(About Command and Query Solver methods)

You need to write a query parser for this. So a CREATE or SELECT statement
You will need to split this query into tokens when it arrives. The above queries
You can assume that it will fit the format perfectly. Split each query according to the space character “ ”
separate the keywords and keywords specific to the queries given in CAPITALS above.
It should understand and resolve tokens like columnName.

Exceptional Circumstances

– In any case, if there is a problem in query analysis, throw an exception and incoming query ignored and the program should continue to run.

– If the file cannot be found in the path given in the CREATE statement, an error is pressed on the screen and the program should continue to work. 

– If the table name given in the CREATE statement is already loaded, you should drop the old table and create the new table. If the file format read does not comply with the rules given here, an exception message will appear on the screen. pressing the CREATE statement is ignored.

– The table mentioned in the SELECT statements may not have been created yet. In this case the table not found error should be pressed and the query should be ignored

– Column(s) mentioned in SELECT statements may not exist in that table. In this case, the screen an exception message should be printed and the query should be ignored. acceptances

– The files you will read will always be csv files, that is, the columns are separated by commas. You can also assume that the first line in any csv file you read is the header. Example A file is attached.

– The Create method should read the header in the first line after reading the file from the disk. So csv will find the names of the columns separated by commas in the first line of the file.

– Column and table names must be single words or combined in camelCase if more than one should be written.

– If more than one column name is listed in the query, they should be separated by commas only. There should be no spaces.

– The type of all columns must be String, both in the file you will read and in the database.

– No operator other than equality will be used in WHERE expressions.

Implementation

Many details about the databaseSimulator class have been given above. This class can be used with any CREATE statement.
when it loads a table (each row is a linkedlist ring) it should load.
For this, you need to organize it to create two classes named row and table.
is our recommendation. In this way, it has databaseSimulator tables; tables are also linked to a linkedlist of rows.
composition can be established.
You can implement only the parts of the linkedlist and tree data structures that you will use. Other
parts are not required.

Index Creation

In the second part of the homework, you need to create an index for the first column of each table of the databaseSimulator class.
Waiting. Binary search tree data structure should be used as index. Each of the databaseSimulator class
There must be a member index in the tree structure of the table object. BST at the nodes of this tree
The values used to do the routing should be the values of the first column of the table and each
node must have references(list) pointing to row(s) of linkedlist.
In this way, in a query with a WHERE expression, if the column used is the first column, the linkedlist's
Thanks to the tree structure, only the relevant row(s) can be accessed without scanning the whole.
