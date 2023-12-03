# CryptoService

This is a application that helps you monitor and manage your cryptocurrency. Main functions:
* transfer between different accounts
* swap your cryptocurrency for each other
* deposit cryptocurrency
* withdraw cryptocurrency
* check all your accounts information or particular
* check your transactions history
* check all crypto rates or particular
* check your own rewards on deposit accounts


## Technologies

Project is created with:
* java 17 version;
* Spring (modules: Spring boot, Spring Data, Spring Security);
* lombok;
* H2 in-memory database;
* Liquibase;
* Swagger;
* Maven;
* JUnit 5;

# Database

The in memory database h2 is connected to the project. You can connect to it by going to "http://localhost:8080/h2-console/". It contains 6 tables.
At the start of the program, there will be 3-6 entities in each table

## Run

* First of all install CryptoService from gitHub.
* Then go to "http://localhost:8080/security/registration" via swagger or postman.
* User have to enter the first name, last name, username and password in JSON format.
* You can easily log in and get your jwt token by clicking on the link "http://localhost:8080/security".

### Additional Information
Once a day, one percent of the total amount is added to all deposit accounts.
Pagination and sorting have been added to some get methods.
A commission is deducted from each transfer or swap and added to transaction fees table.
