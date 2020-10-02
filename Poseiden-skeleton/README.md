# Poseidon

<p>The java application "Poseidon" is run with Maven and Spring Boot.</p>

## Infos

- **Author:** Ludovic Tuccio

- **Actuators in service:** health, info, httptrace & metrics

- **Tests:** the app has unit tests and integration tests written.

## Technical:

- **Java** 1.8, **MySql** 8.0, **Spring Boot** 2.3.2, **Spring Data Jpa**, **Hibernate** 5, **Thymeleaf**, **Bootstrap** v4.3.1

## Installing

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install Lombok in your IDE before import project:

https://www.baeldung.com/lombok-ide (you must execute an external JAR)

4.Install MySql:

https://dev.mysql.com/downloads/installer/

## Database

- The file **data.sql** (available in *"/src/main/resources"*) contains scrypt SQL to create "dev" database.

## Security

- Database password and username encryption with Jasypt. 

- The user passwords are encrypted with BCryptPasswordEncoder (Spring Security).
