> [![forthebadge](https://forthebadge.com/images/badges/made-with-Java.svg)](https://forthebadge.com) * * * [![forthebadge](https://forthebadge.com/images/badges/uses-html.svg)](https://forthebadge.com)

# Poseidon

The Java application **'Poseidon'** is run with Maven and Spring Boot, on server port 9090.
<p>Views controllers and API REST controllers are implemented with CRUD.</p>
<p>Only admins can manage user accounts.</p>

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
- **API documentation:** http://localhost:9090/swagger-ui.html (Swagger 2)

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

- Database password and username encryption with **Jasypt**. 

- Users password are encrypted with **BCryptPasswordEncoder**.

- Basic Authentication for login with **Spring Security**.

## Testing

- The app has unit tests and integration tests written. You must launch 'mvn site' (all reports available in *"/target/site"*).

**To test API REST endpoints:**

- Install Postman: https://www.postman.com/

**To test web app:**

- Use port 9090: http://localhost:9090/

# API REST

## >>> /v1/user <<<

**POST** - Add a new user. Username is unique, password has a regexp and role are admin or user.
<pre><code>
	{
	        "username": "myusername",
	        "password": "Password1&",
	        "fullname": "fullname",
	        "role": "user"
	}
		</pre></code>

**GET** - Get all users list.

**PUT** - Update user's informations. Username can't be changed.
<pre><code>
	{
	        "username": "myusername",
	        "password": "Password1&",
	        "fullname": "fullnameUpdated",
	        "role": "user"
	}
		</pre></code>

**DELETE** - Delete an user: param 'username'.

## >>> /v1/bidList  <<<

**POST** - Add a new bid list. Param: 'bidAccount', 'bidType', 'bidQuantity'.

**GET** - Get all bid list.

**PUT** - Update a bid list. 'bidListDate' must be a date-time before actual date. Revision name can't be empty.	
<pre><code>
    {
        "bidListId": 1,
        "account": "account",
        "type": "type",
        "bidQuantity": 1500.0,
        "askQuantity": null,
        "bid": null,
        "ask": null,
        "benchmark": null,
        "bidListDate": "01/10/1990 10:00",
        "commentary": "NEW COMMENTARY",
        "security": null,
        "status": null,
        "trader": null,
        "book": null,
        "creationName": "creation name",
        "revisionName": "new revision",
        "dealName": null,
        "dealType": null,
        "sourceListId": null,
        "side": null
    }
		</pre></code>

**DELETE** - Delete a bid list: param 'bidId' with 'bidAccount' for security before delete it.

## >>> /v1/curvePoint <<< 

**POST** - Add a new Curve Point. Param: 'curveId', 'term', 'value'.

**GET** - Get all Curve Point list.

**PUT** - Update a Currve Point.
<pre><code>
    {
        "id": 14,
        "curveId": 15,
        "term": 90.0,
        "value": 50.0 
    }
</pre></code>

**DELETE** - Delete a Curve Point: param 'id'.

## >>> /v1/rating <<< 

**POST** - Add a new Rating.
<pre><code>
    {
        "moodysRating": "moodys",
        "sandPRating": "sandprating",
        "fitchRating": "fitch ",
        "orderNumber": 1
    }
</pre></code>

**GET** - Get all Rating list.

**PUT** - Update a Rating. Param 'id' and body. Order number can't be changed.
<pre><code>
    {
        "moodysRating": "moodys",
        "sandPRating": "sandpUpdated",
        "fitchRating": "fitch",
        "orderNumber": 1
    }
</pre></code>

**DELETE** - Delete a Rating: param 'id'.

## >>> /v1/rulename <<<

**POST** - Add a new Rule Name.
<pre><code>
    {
		"name" : "name",
		"description": "description",
		"json" : "json",
		"template" : "template",
		"sqlStr" : "sqlStr",
		"sqlPart" : "sqlpart"
    }
</pre></code>

**GET** - Get all Rule Name list.

**PUT** - Update a Rule Name. Param 'id' and body.
<pre><code>
    {
        "name": "updated",
        "description": "description",
        "json": "json",
        "template": "template",
        "sqlStr": "sql str",
        "sqlPart": "sql part"
    }
</pre></code>

**DELETE** - Delete a Rule Name: param 'id'.

## >>> /v1/trade <<< 

**POST** - Add a new Trade. 'account' and 'type' and 'creationName' can't be empty, 'tradeDate' must be a date before actual date. 'revisionName' must be empty.
<pre><code>
    {
		"account" : "account",
		"type": "type",
		"buyQuantity" : 10,
		"sellQuantity" : 10,
		"buyPrice" : 10,
		"sellPrice" : 10,
		"tradeDate": "01/01/2010 23:59",
		"security" : "security 1",
		"status" : "status 1",
		"trader" : "trader 1",
		"benchmark" : "benchmark 1",
		"book": "book 1",
		"creationName" : "creationName 1",
		"revisionName" : "",
		"dealName": "dealName 1",
		"dealType" : "dealType 1",
		"sourceListId" : "sourceListId 1",
		"side" : "side 1"
    }
</pre></code>

**GET** - Get all Trade list.

**PUT** - Update a Trade. Param 'id'. 'account' and 'type' and 'revisionName' can't be empty, 'tradeDate' must be a date before actual date. 'creationName' can't be changed.
<pre><code>
    {
        "account": "account",
        "type": "type",
        "buyQuantity": 10.0,
        "sellQuantity": 10.0,
        "buyPrice": 10.0,
        "sellPrice": 10.0,
        "tradeDate": "01/01/2010 23:59",
        "security": "update 1",
        "status": "status 1",
        "trader": "update 1",
        "benchmark": "benchmark 1",
        "book": "book 1",
        "creationName": "creationName 1",
        "creationDate": "01/10/2020 13:42",
        "revisionName": "REVISION UPDATE",
        "revisionDate": null,
        "dealName": "dealName 1",
        "dealType": "dealType 1",
        "sourceListId": "sourceListId 1",
        "side": "side 1"
    }
</pre></code>

**DELETE** - Delete a Trade: param 'id'.