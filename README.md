Welcome to Prizy-Pricer!
===================


Hey! This is my prizy-pricer application test.

----------


**Documentation**
-------------
> **General configuration:**
>-Grails version used: 2.4.3
>-Java version used: 1.8

    Database configuration:

>**Current properties already set:**
> - **Type**: mysql
> - **Database Name**: 
		<i>For Development</i>: prizydb 
>- **Username:** root
>- **Password:** java
>> - If database is not present it will be auto created while deploying or testing application  **after giving the correct database username and password** Make sure your database properties in the file **/grails-app/conf/prizy-config.properties**  <i class="icon-refresh"></i>.
>- Also default demo database will be inserted at application startup.
>-  You can disable auto insertion by commenting lines in the file **/grails-app/conf/BootStrap.groovy** or change the number of products in csv file **/my-prizy/grails-app/conf/products.csv**.
>- Anyways you can change all the database properties in the file **/my-prizy/grails-app/conf/prizy-config.properties**. 
>- Access the application in context path of 
>/my-prizy

>**Deploying WAR**
>> - Put **war** file in **web-app** directory of the tomcat.
>- Put **products.csv** and **prizy-config.properties** in to classpath of tomcat directory(in the **lib directory** of tomcat).
>-  You can disable auto insertion by changing the number of products in csv file **products.csv**
>- Start the tomcat and access the application in context path of 
>/my-prizy