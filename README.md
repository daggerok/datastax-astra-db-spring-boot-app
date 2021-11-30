# Spring Data Cassandra + DataStax Astra DB

* SignUp / SignIn in [Datastax Astra](https://astra.datastax.com/) to get your DB for free
* Create `datastax_astra_db_spring_boot_app_database` database with `datastax_astra_db_spring_boot_app_keyspace` keyspace
* Click connect on your database
* Chose `Java` in `Connect using a driver` section
* Click `Download Bundle` button and save zip archive as `src/main/resources/secure-connect-datastax-astra-db-spring-boot-app-database.zip`
* Create token for `Administrator User`, grab your `ClientID` and `ClisentSecret`
* Add `src/main/resources/application-datastax.properties` file with content:
  ```properties
  datastax.astra.secure-connect-bundle=/path/to/project/src/main/resources/secure-connect-datastax-astra-db-spring-boot-app-database.zip
  datastax.astra.client-id=$yourClientID
  datastax.astra.client-secret=$yourClientSecret
  datastax.astra.keyspace=datastax_astra_db_spring_boot_app_keyspace
  ```
* Add dependency to your `pom.xml` file:
  ```xml
  <dependency>
    <groupId>com.datastax.oss</groupId>
    <artifactId>java-driver-core</artifactId>
    <version>4.13.0</version>
  </dependency>
  ```
* Run app:
  ```bash
  mvn compile spring-boot:run
  ```
* Post few messages and load them via rest api
  ```bash
  http :8080/api/v1/messages content=Hello,\ World\!
  http :8080/api/v1/messages content=Hello,\ again...
  http :8080/api/v1/messages
  ```
* Done

## RTFM
* youtube.com/watch?v=nBoHQOcwPS4
* https://github.com/DataStax-Examples/spring-petclinic-reactive
* https://github.com/DataStax-Examples/getting-started-with-astra-java
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.1/maven-plugin/reference/html/#build-image)
* [Coroutines section of the Spring Framework Documentation](https://docs.spring.io/spring/docs/5.3.13/spring-framework-reference/languages.html#coroutines)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.6.1/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [Spring Data Reactive for Apache Cassandra](https://docs.spring.io/spring-boot/docs/2.6.1/reference/htmlsingle/#boot-features-cassandra)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
