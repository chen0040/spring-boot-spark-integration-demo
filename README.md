# spring-boot-spark-integration-demo

Demo on how to integrate Spring and Apache spark

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
