# Build a Cryptocurrency Price Tracker

## Build and Package

Run `mvn clean install`  
Image will be built as `creationk/crypto-tracking-docker:latest`. This will take a while.

## Modify environment variables

Modify environment variables under docker-compose.yml file as required

## Start the containers and run the application

Navigate to the location of the docker-compose file and run the command `docker-compose up --build`

## Swagger UI

`http://localhost:8080/`

## Endpoint to test

`http://localhost:8080/api/prices/btc?date=25-07-2022&offset=0&limit=4`
