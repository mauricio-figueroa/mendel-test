# Transaction  test

## Description
The application contains the logic to persist transactions, calculate the value of the transaction (and its bindings) and return ids by type

## Technologies
* JAVA 11
* SpringBoot
* Gradle

## Run app
Execute
`gradle bootRun` in terminal

## Documentation

Inside the documentation folder there is a collection of postman for use with the application services

### How to consume the services of the application

### Create Transaction
<pre><code>
curl --location --request PUT 'localhost:8080/transactions/2' \
--header 'Content-Type: application/json' \
--data-raw '{
    "amount":150,
    "type":"cards"
}'
</code></pre>

### GetSum
<pre><code>
curl --location --request GET 'localhost:8080/transactions/sum/4' \
--data-raw ''
</code></pre>

### Get Tx IDs by type
<pre><code>
curl --location --request GET 'localhost:8080/transactions/types/cards' \
--data-raw ''
</code></pre>
