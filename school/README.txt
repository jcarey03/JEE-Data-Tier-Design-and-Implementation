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

It is probably best to get the latest available JAR, so tweak the mvn install 
command to account for this.  Also, you will need to update any Maven2 POM files
that depend on it.

You also need to install an Oracle 10g database.  I suggest Oracle Express, which
supports Windows and Linux.  You can download the database at the following URL:

http://www.oracle.com/technology/software/products/database/xe/index.html

It is best to configure Oracle Express 10g to handle more than the default number of
connections, so follow the below steps to change this:

1.  In your cmd window, connect to your Oracle database instance as the SYSTEM user.
          sqlplus system/[sys-passwd]
2.  While still connected to Oracle, run the following command to fix a known issue with XE
          ALTER SYSTEM SET PROCESSES=150 SCOPE=SPFILE;
3.  Exit sqlplus
          exit
4.  Stop and start the Oracle instance for the changes to take effect
          Start Menu > Programs > Oracle Database 10g Express Edition > Start/Stop 

FYI, the project is currently setup for a database user account with username = 'school' 
and password = 'school'.  This will be more easily configurable in the near future.

Lastly, you need to execute the 'school_schema.sql' SQL file to populate the
database with the schema.

I know it's a lot of setup, but it could be worse :)
