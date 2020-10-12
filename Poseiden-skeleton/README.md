# Poseidon

<p>The java application "Poseidon" is run with Maven and Spring Boot on server port 9090.</p>

## Informations / Technical

- **Java** 1.8 
- **Spring Boot** 2.3.2 
- **Spring Data Jpa** 
- **Spring Security** 
- **Hibernate** 5
- **MySql** 8.0.21
- **Thymeleaf**
- **Bootstrap** v4.3.1
- **Log4j2** 
- **Actuators** in service: health, info, httptrace & metrics (port 8080)
- **API documentation:** http://localhost:9090/swagger-ui.html

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

- The users password are encrypted with BCryptPasswordEncoder (Spring Security).

-

## Testing

- The app has unit tests and integration tests written. You must launch 'mvn site' (all reports available in *"/target/site"*).

**To test endpoints:**
<ol>
	<li>Install Postman https://www.postman.com/</li> 
		OR
<li>Use port 9090 http://localhost:9090/{}</li> 
</ol>

