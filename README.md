## Spring Security with JWT for REST API
Application to demonstrate Spring Security for authentication/authorization with JWT for RESTfull application. Add the DB details in application.properties and application will create a user table in DB upon startup.
Security is disabled for user Registration and Authoriztion API request for the endpoints  "/auth" and "/registeruser" respecitvely (File: WebSecurityConfig.java). 


### Technology Stack
Component         | Technology
---               | ---
Backend (REST)    | [SpringBoot](https://projects.spring.io/spring-boot) (Java)
Security          | Token Based (Spring Security and [JWT](https://github.com/auth0/java-jwt) )
DB                | MySQL 
Persistence       | JPA  
Server Build Tools| Maven 
 
## Spring security
When security is enabled, none of the REST API will be accessesble directly.
To test security access `http://localhost:8080/health` API and you should get a forbidden/Access denied error. 
In order to access these secured API you must first obtain a token. Tokens can be obtained by passing a valid username and password.

username and password are stored in username table. To add user call below `POST` API 

```
curl --location --request POST 'http://localhost:8080/registeruser' \
--header 'Content-Type: application/json' \
--data-raw '{
  "userId": "demo",
  "password": "demo",
  "firstName": "demo",
  "lastName": "demo",
  "email" :"demo@gmail.com",
  "phone": "0000000000"
}'


```
<br/>

To get a token call `POST` API with a valid userId and password. For example you may you can use the folliwing curl command to get a token 


```
curl --location --request POST 'http://localhost:8080/auth' \
--header 'Content-Type: application/json' \
--data-raw '{
"username": "demo",
"password": "demo"
}'

```
 
the above curl command will return you a token, which should be in the format of `xxx.xxx.xxx`. This is a JSON web token format. 
You can decode and validate this token at [jwt.io wesite](https://jwt.io/). Just paste the token there and decode the information.
 To validate the token you should provide the secret key which is `UEE2FsdB4G=` that i am using in this app.
<br/>

After receiving this token you must provide the token in the request-header of every API request. For instance try the below `GET /version` API using the below 
curl command (replace xxx.xxx.xxx with the token that you received in above command) and you should be able to access the API which will return OK.
```
curl --location --request GET 'http://localhost:8080/health' \ --header 'Authorization: Bearer xxx.xxx.xxx'
``` 
 

### Build Backend (SpringBoot Java)
```bash
# Maven Build : Navigate to the root folder where pom.xml is present 
mvn clean install
```

### Start the server
```bash
# Start the server (8080)
# port and other configurations for API server is in [./src/main/resources/application.properties](/src/main/resources/application.properties) file

# If you build with maven jar location will be 
java -jar ./target/app-1.0.0.jar

```
 
