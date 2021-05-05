# FX Rates Widget API
-This is an app that converts foreign currencies
-This app uses free subscription from: http://api.exchangeratesapi.io/
-This app uses an in memory database

## Compiling and Running the app
```
cd fx-rates-widget-api
mvn clean package
java -jar target/fx-rates-widget-api-0.0.1-SNAPSHOT.jar
```

## Testing and Using the application

Go to http://localhost:8080/swagger-ui.html for the documentation of this API

This has a configuratble virtual wallet and api key for further customization (api keys with paid subscription). located in:
https://github.com/plurantee/fx-rates-widget-api/blob/master/src/main/resources/application.properties



