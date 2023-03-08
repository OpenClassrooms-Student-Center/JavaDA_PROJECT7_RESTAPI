Poseidon
========================

Description
------------
Poseidon is a financial aggregator powered by various market tools.
## Technical:

1. Framework: Spring Boot v2.7.4
2. Java 8
3. Thymeleaf
4. Bootstrap v.4.3.1


## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

# Application.properties
```
spring.datasource.password=your_password
```

- Build the project

```
mvn clean install
```

- Run the project

```
mvn spring-boot:run
```