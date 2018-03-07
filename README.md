# spring-boot-spark-integration-demo

Demo on how to integrate Spring and Apache spark

# Features

The current project contains the following features:

* loading data from mariadb or mysql using spring-data-jpa
* spring boot support 
* spark for big data analytics
* hadoop integration
* redis for publishing spark job progress
* graphx from graph mining such as page rank
* integration of both Java and Scala codes in a single code base
* self-deployment of spark job into mesos-sphere and chronos

# ETL Flow

The application follows the following ETL flow:

* Step 1: data is extracted from mysql or mariadb database
* Step 2: data is transformed using spark 
* Step 3: transformed data is then stored into into hadoop distributed file system (HDFS)
* Step 4: spark+graphx job is then run by load HDFS data into a graph structure and run graph mining

# Database Configuration

To use this project create a database named my_sga in your mysql database (make sure it is running at localhost:3306)

```sql
CREATE DATABASE my_sga CHARACTER SET utf8 COLLATE utf8_unicode_ci;
```

Note that the default username and password for the mysql is configured to 

* username: root
* password: chen0469

If your mysql or mariadb does not use these configuration, please change the settings in src/resources/config/application-default.properties

After the database is setup and configure, load the database demo data by unzipping [my_sga.sql.zip](my_sga.sql.zip)
and load the my_sga.sql into the my_sga database
