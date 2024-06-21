# stocks

This Kotlin based [Spring Boot](https://spring.io/projects/spring-boot) application is a simple REST API to manage stocks.

These stocks are currently stored in memory and are not persisted. I am using H2 as the database as of now. 

The application provides the following endpoints:
- GET /stocks - you can get all the stocks or use the symbol query parameter to filter by symbol or fromDate and you have paginated result

Example of requests:
- http://localhost:8080/internalApi/v1/stocks?symbol=AAPL
- http://localhost:8080/internalApi/v1/stocks?symbol=AAPL&page=1
- http://localhost:8080/internalApi/v1/stocks?symbol=AAPL&size=20
- http://localhost:8080/internalApi/v1/stocks?fromDate=2020-07-01


In order to get the openapi spec: 
- http://localhost:8080/api-docs
- to download it: http://localhost:8080/api-docs.yaml
- to visualize it: http://localhost:8080/swagger-ui.html

To generate the openapi spec, I am using [springdoc-openapi](https://springdoc.org/):

```bash
$ gradle generateOpenApiDocs
```
Then get the file from: `/build/openapi.json`
