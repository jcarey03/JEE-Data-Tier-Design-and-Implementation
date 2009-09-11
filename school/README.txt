README

Setup
=====

This project is configured to communicate with the Oracle 10g database. 
Unfortunately, due to the binary license there is no public Maven2 repository 
with the Oracle Driver JAR.  Therefore, you must manually install this jar using
the following Maven2 command:

mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 \
     -Dversion=10.2.0.3.0 -Dpackaging=jar -Dfile=/path/to/jar

where you must specify the path to the JAR on your computer.  You can download
the JAR using the below URL: 

http://www.oracle.com/technology/software/tech/java/sqlj_jdbc/index.html. 

You also need to install an Oracle 10g database.  I suggest Oracle Express, which
supports Windows and Linux.  You can download the database at the following URL:

http://www.oracle.com/technology/software/products/database/xe/index.html

Lastly, you need to execute the 'school_schema.sql' SQL file to populate the
database with the schema.

I know it's a lot of setup, but it could be worse :)
