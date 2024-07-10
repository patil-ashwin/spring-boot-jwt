Sure! Here are 100 Oracle SQL interview questions with answers for experienced professionals:

### Basic SQL Questions

1. **What is SQL?**
   - SQL (Structured Query Language) is a standard programming language specifically designed for managing and manipulating relational databases.

2. **What are the different types of SQL commands?**
   - SQL commands are classified into DDL (Data Definition Language), DML (Data Manipulation Language), DCL (Data Control Language), and TCL (Transaction Control Language).

3. **What is a primary key?**
   - A primary key is a unique identifier for a record in a table. It must contain unique values and cannot contain NULLs.

4. **What is a foreign key?**
   - A foreign key is a field (or collection of fields) in one table, that uniquely identifies a row of another table.

5. **What is a unique key?**
   - A unique key ensures that all values in a column are unique. Unlike a primary key, it can accept one NULL value.

### Advanced SQL Questions

6. **What is a subquery and its types?**
   - A subquery is a query nested inside another query. Types include single-row subqueries, multiple-row subqueries, and correlated subqueries.

7. **Explain the difference between DELETE and TRUNCATE.**
   - DELETE removes rows one by one and can have a WHERE clause; TRUNCATE removes all rows without logging individual row deletions.

8. **What is a view?**
   - A view is a virtual table based on the result-set of an SQL statement. It can contain rows and columns from one or more tables.

9. **What is an index?**
   - An index is a database object that improves the speed of data retrieval operations on a table.

10. **What are the types of indexes?**
    - Types include unique index, composite index, bitmap index, function-based index, and domain index.

### PL/SQL Questions

11. **What is PL/SQL?**
    - PL/SQL (Procedural Language/SQL) is Oracle's procedural extension for SQL, combining SQL with procedural features of programming languages.

12. **Explain the PL/SQL block structure.**
    - A PL/SQL block consists of a declaration section, an executable section, and an exception-handling section.

13. **What are cursors in PL/SQL?**
    - A cursor is a pointer to the context area, which holds the result set of an SQL query. Types include implicit and explicit cursors.

14. **What are triggers in PL/SQL?**
    - A trigger is a stored procedure that runs automatically when specific events occur in the database, such as INSERT, UPDATE, or DELETE operations.

15. **What are packages in PL/SQL?**
    - Packages are a way to group related procedures, functions, variables, and other PL/SQL constructs.

### Performance Tuning Questions

16. **What is an execution plan?**
    - An execution plan is a sequence of operations that the database performs to execute an SQL statement.

17. **How do you analyze and optimize a SQL query performance?**
    - Techniques include examining execution plans, using indexes, avoiding full table scans, and using query hints.

18. **What are bind variables and their benefits?**
    - Bind variables are placeholders in SQL statements that are replaced with actual values at runtime. They help in reducing parsing overhead and improving performance.

19. **Explain the difference between a hard parse and a soft parse.**
    - A hard parse involves compiling a SQL statement and creating an execution plan, while a soft parse reuses an existing execution plan.

20. **What is Oracle's Cost-Based Optimizer (CBO)?**
    - The CBO generates execution plans based on the estimated cost of operations, considering factors like CPU, I/O, and memory.

### Advanced Oracle SQL Techniques

21. **What is a materialized view?**
    - A materialized view is a database object that contains the results of a query and can be refreshed periodically.

22. **Explain the concept of a WITH clause in SQL.**
    - The WITH clause, also known as common table expressions (CTEs), is used to create temporary result sets that can be referenced within a SELECT, INSERT, UPDATE, or DELETE statement.

23. **What are analytical functions in Oracle SQL?**
    - Analytical functions perform calculations across a set of table rows and return a single value for each row, examples include RANK(), DENSE_RANK(), and ROW_NUMBER().

24. **What is partitioning in Oracle?**
    - Partitioning is a database design technique that divides large tables into smaller, more manageable pieces, improving performance and manageability.

25. **Explain the difference between a UNION and a UNION ALL.**
    - UNION combines the results of two queries and removes duplicates, while UNION ALL combines the results and includes duplicates.

### Practical Scenario-Based Questions

26. **How would you identify and resolve locking issues in Oracle?**
    - Identify using V$LOCK and DBA_LOCK views, resolve by analyzing blocking sessions and taking corrective actions like killing the session or resolving deadlocks.

27. **How do you perform bulk operations in PL/SQL?**
    - Using bulk collect and FORALL statements to perform operations on large sets of data efficiently.

28. **Explain the use of hints in Oracle SQL.**
    - Hints are directives placed in SQL statements to instruct the optimizer on how to execute queries, such as /*+ INDEX(table_name index_name) */.

29. **What are sequences in Oracle SQL?**
    - Sequences are database objects that generate unique numeric values, often used for primary keys.

30. **How do you handle exceptions in PL/SQL?**
    - Using the EXCEPTION block to catch and handle runtime errors.

### Expert Level Questions

31. **What is Oracle Real Application Clusters (RAC)?**
    - Oracle RAC is a clustering solution that allows multiple computers to run Oracle RDBMS software simultaneously while accessing a single database.

32. **Explain Oracle Data Guard.**
    - Data Guard provides high availability, data protection, and disaster recovery by maintaining standby databases.

33. **What is Oracle ASM (Automatic Storage Management)?**
    - ASM is a storage management solution that simplifies database storage and provides performance improvements.

34. **How do you perform a database backup and recovery?**
    - Using RMAN (Recovery Manager) for backup and recovery operations, including full, incremental, and archive log backups.

35. **What is Oracle GoldenGate?**
    - GoldenGate is a real-time data integration and replication solution.

### Complex Query Questions

36. **Write a SQL query to find the second highest salary in a table.**
    ```sql
    SELECT MAX(salary) 
    FROM employees 
    WHERE salary < (SELECT MAX(salary) FROM employees);
    ```

37. **How would you find the nth highest salary?**
    ```sql
    SELECT salary 
    FROM (SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) AS rank 
          FROM employees) 
    WHERE rank = :n;
    ```

38. **Write a query to display the department-wise total salary.**
    ```sql
    SELECT department_id, SUM(salary) 
    FROM employees 
    GROUP BY department_id;
    ```

39. **How do you find duplicate rows in a table?**
    ```sql
    SELECT column1, column2, COUNT(*)
    FROM table_name
    GROUP BY column1, column2
    HAVING COUNT(*) > 1;
    ```

40. **Explain the use of the RANK() function.**
    - RANK() assigns a unique rank to each distinct row within a partition of a result set, allowing for tie handling by giving the same rank to identical values.

### Specific Oracle Features

41. **What are Oracle Data Types?**
    - Oracle supports various data types including VARCHAR2, NUMBER, DATE, TIMESTAMP, CLOB, BLOB, and more.

42. **Explain the difference between CHAR and VARCHAR2.**
    - CHAR is fixed-length, while VARCHAR2 is variable-length. VARCHAR2 saves space by using only the necessary amount of storage.

43. **What is a cluster in Oracle?**
    - A cluster is a schema object that contains one or more tables, which share the same data blocks.

44. **What is a synonym in Oracle?**
    - A synonym is an alias for a database object, making it easier to reference.

45. **Explain Oracle Flashback Technology.**
    - Flashback technology allows you to query past states of database objects, recover from logical corruption, and perform selective data recovery.

### Data Warehousing Questions

46. **What is a star schema?**
    - A star schema is a type of database schema that is optimized for data warehousing and consists of fact tables and dimension tables.

47. **What is a snowflake schema?**
    - A snowflake schema is a type of database schema where dimension tables are normalized, splitting data into additional tables.

48. **Explain the use of OLAP in Oracle.**
    - OLAP (Online Analytical Processing) allows for complex analytical queries, including trend analysis and data mining.

49. **What is ETL?**
    - ETL stands for Extract, Transform, Load. It is a process used to extract data from different sources, transform it into a suitable format, and load it into a database.

50. **What is a fact table?**
    - A fact table contains quantitative data for analysis and is usually denormalized.

### Security and Management Questions

51. **How do you manage user privileges in Oracle?**
    - Using GRANT and REVOKE commands to assign and remove privileges.

52. **What are roles in Oracle?**
    - Roles are named groups of related privileges that

 can be granted to users or other roles.

53. **Explain the use of profiles in Oracle.**
    - Profiles manage user resource limits and password management.

54. **What is an Oracle Wallet?**
    - An Oracle Wallet is a secure software container that stores authentication and signing credentials.

55. **How do you audit database activities in Oracle?**
    - Using the AUDIT command to track and log database activities.

### Miscellaneous Questions

56. **What are Oracle Hints?**
    - Hints are special comments in SQL statements that direct the Oracle optimizer on how to execute queries.

57. **What is Oracle AWR (Automatic Workload Repository)?**
    - AWR is a repository for performance statistics, used for diagnosing performance issues.

58. **Explain Oracle Grid Infrastructure.**
    - Grid Infrastructure provides the necessary services to run Oracle RAC and Oracle ASM.

59. **What are Oracle Data Pump utilities?**
    - Data Pump utilities (expdp and impdp) are used for fast data and metadata export/import operations.

60. **What is Oracle Transparent Data Encryption (TDE)?**
    - TDE encrypts database files to secure sensitive data at rest.

### Complex Concepts and Troubleshooting

61. **What is a mutating table and how do you resolve it?**
    - A mutating table is a table that is being modified by an insert, update, or delete operation and is being read by a trigger. Resolve it by using temporary tables or compound triggers.

62. **Explain the use of ROWID in Oracle.**
    - ROWID is a unique identifier for a row in a table and can be used to quickly locate a row.

63. **What are V$ views in Oracle?**
    - V$ views are dynamic performance views that provide information about the database instance.

64. **How do you handle large object data types (LOBs) in Oracle?**
    - Using CLOB, BLOB, and BFILE data types, and managing them with DBMS_LOB package.

65. **What is Oracle's Multitenant Architecture?**
    - Multitenant Architecture allows multiple pluggable databases (PDBs) to be managed within a single container database (CDB).

### In-depth PL/SQL Questions

66. **What is the difference between a function and a procedure in PL/SQL?**
    - A function returns a value and can be used in SQL expressions, while a procedure does not return a value and is used for executing business logic.

67. **What are collections in PL/SQL?**
    - Collections are ordered groups of elements of the same type. Types include associative arrays, nested tables, and VARRAYs.

68. **Explain the use of PRAGMA in PL/SQL.**
    - PRAGMA is a compiler directive used to provide additional information to the compiler. Examples include PRAGMA EXCEPTION_INIT and PRAGMA AUTONOMOUS_TRANSACTION.

69. **What is an autonomous transaction in PL/SQL?**
    - An autonomous transaction is an independent transaction started by another transaction, allowing actions like logging or auditing without affecting the main transaction.

70. **How do you optimize PL/SQL code?**
    - Techniques include bulk binding, avoiding context switching, using collections, and analyzing PL/SQL execution plans.

### Oracle Database Administration

71. **What is Oracle Recovery Manager (RMAN)?**
    - RMAN is a tool for backing up, restoring, and recovering Oracle databases.

72. **Explain the difference between physical and logical backups.**
    - Physical backups involve copying the physical files of the database, while logical backups involve exporting database objects and data.

73. **What is Oracle Enterprise Manager (OEM)?**
    - OEM is a web-based tool for managing Oracle databases and applications, providing performance monitoring and administrative capabilities.

74. **What are Oracle Database Links?**
    - Database links are schema objects that allow you to access objects on a remote database.

75. **Explain the concept of Oracle Tablespaces.**
    - Tablespaces are logical storage units in an Oracle database, used to group related logical structures together.

### Advanced SQL Techniques

76. **What is a recursive query in SQL?**
    - A recursive query is one that references itself, commonly used to traverse hierarchical data.

77. **How do you use the MERGE statement in Oracle SQL?**
    - The MERGE statement allows you to insert, update, or delete rows in a table based on the results of a join with another table.

78. **What is the CONNECT BY clause used for?**
    - The CONNECT BY clause is used to perform hierarchical queries, traversing parent-child relationships in a table.

79. **How do you create and use a sequence in Oracle?**
    ```sql
    CREATE SEQUENCE seq_name START WITH 1 INCREMENT BY 1;
    SELECT seq_name.NEXTVAL FROM dual;
    ```

80. **Explain the concept of dynamic SQL in Oracle.**
    - Dynamic SQL allows you to construct and execute SQL statements at runtime using the EXECUTE IMMEDIATE statement or DBMS_SQL package.

### Error Handling and Debugging

81. **What is an exception in PL/SQL?**
    - An exception is an error condition that can be caught and handled within a PL/SQL block.

82. **How do you raise and handle user-defined exceptions?**
    ```sql
    DECLARE
      ex_custom EXCEPTION;
    BEGIN
      IF some_condition THEN
        RAISE ex_custom;
      END IF;
    EXCEPTION
      WHEN ex_custom THEN
        DBMS_OUTPUT.PUT_LINE('Custom exception caught.');
    END;
    ```

83. **What are system-defined exceptions in PL/SQL?**
    - System-defined exceptions are pre-defined by Oracle and include exceptions like NO_DATA_FOUND, TOO_MANY_ROWS, and ZERO_DIVIDE.

84. **How do you use the DBMS_OUTPUT package in PL/SQL?**
    - The DBMS_OUTPUT package is used to display output from PL/SQL blocks. Enable it using `SET SERVEROUTPUT ON`.

85. **Explain the use of the DBMS_DEBUG package.**
    - DBMS_DEBUG provides a way to debug PL/SQL code by setting breakpoints, examining variables, and stepping through code.

### Database Design and Modeling

86. **What is normalization and its types?**
    - Normalization is the process of organizing data to reduce redundancy. Types include 1NF, 2NF, 3NF, BCNF, and 4NF.

87. **What is denormalization?**
    - Denormalization is the process of combining normalized tables to improve performance, often at the cost of redundancy.

88. **Explain the concept of a database schema.**
    - A schema is a logical collection of database objects like tables, views, indexes, and procedures.

89. **What is an Entity-Relationship (ER) model?**
    - The ER model is a diagrammatic representation of the database structure, showing entities, attributes, and relationships.

90. **How do you design a relational database?**
    - Steps include requirement analysis, conceptual design using ER diagrams, logical design with normalization, and physical design considering performance.

### Miscellaneous Expert-Level Questions

91. **What are Oracle Text and its use cases?**
    - Oracle Text is a full-text search engine that enables searching and indexing of text in the database, used for applications like document management systems.

92. **Explain Oracle Streams.**
    - Oracle Streams provide data replication, message queuing, and data warehouse loading.

93. **What is Oracle Advanced Queuing (AQ)?**
    - AQ is a message queuing system integrated into the Oracle database, providing asynchronous communication between applications.

94. **What is Oracle Spatial?**
    - Oracle Spatial provides advanced geographic information system (GIS) capabilities, allowing for the storage, analysis, and querying of spatial data.

95. **What are Oracle Bitmap Indexes?**
    - Bitmap indexes use bitmaps to represent index keys, optimized for read-heavy applications with low cardinality columns.

### Real-world Scenario Questions

96. **How do you handle database migrations in Oracle?**
    - Using tools like Data Pump, GoldenGate, or custom scripts to export/import data and schema definitions.

97. **What are the steps to troubleshoot a slow-running query?**
    - Steps include examining execution plans, checking for inefficient joins, analyzing statistics, and considering indexing.

98. **How do you secure an Oracle database?**
    - Implementing security measures like user roles, encryption, auditing, and network security.

99. **What is the role of Oracle Cloud in modern database management?**
    - Oracle Cloud provides cloud-based database services, offering scalability, performance, and managed services.

100. **How do you ensure high availability in an Oracle database?**
    - Using techniques like Oracle RAC, Data Guard, and regular backups to ensure continuous availability and disaster recovery.

These questions and answers should help prepare for interviews and improve your understanding of Oracle SQL concepts and practices.
