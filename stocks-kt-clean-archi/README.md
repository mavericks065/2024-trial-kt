# stocks

This Kotlin based [Spring Boot](https://spring.io/projects/spring-boot) application is a simple REST API to manage stocks.

These stocks are currently stored in memory and are not persisted. I am using H2 as the database as of now. 

The application provides the following endpoints:
- GET /stocks - you can get all the stocks or use the symbol query parameter to filter by symbol
