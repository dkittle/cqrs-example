# CQRS Example

## Overview

This project explores using CQRS to implement a service that accepts simple ecommerce transactions or `Orders`.
 
 The system allows for customers to have an account balance with the store which is used to fund order purchases.
 
 The system will have a tight coupling between the record of transactions and individual account balances to maintain 
 balance integrity; we cannot loose messages between the storing of a transaction and the updating of the account balance.
 All other views of transactions are implemented as separate _aggregates_ listening on an event bus.

## Running the sample
Start the REST interface using:

    sbt run
    
This starts an `akka-http` server listening on all local interfaces, port 9000. This can be changed in `application.conf`.

## Sample interactions with the REST interface

### Post a basic order
    curl -i -X POST -H "Content-Type: application/json" http://localhost:9000/order -d '{"orderId":"83123","items":[{"name":"salt & vinegar chips","quantity":2.0,"tags":["snacks","chips"],"itemId":"83451","unitPrice":1.25},{"name":"club soda","quantity":1.0,"tags":["drinks","carbonated","2l"],"itemId":"9988563","unitPrice":2.55}],"amount":4.75,"posId":"145a","customerId":"8300","taxAmount":0.0,"date":"2017-08-08T12:43:10.709+02:00[Europe/Berlin]"}'


### missing POS ID
    curl -i -X POST -H "Content-Type: application/json" http://localhost:9000/order -d '{"orderId":"83123","items":[{"name":"salt & vinegar chips","quantity":2.0,"tags":["snacks","chips"],"itemId":"83451","unitPrice":1.25},{"name":"club soda","quantity":1.0,"tags":["drinks","carbonated","2l"],"itemId":"9988563","unitPrice":2.55}],"amount":4.75,"posId":"","customerId":"8300","taxAmount":0.0,"date":"2017-08-08T12:43:10.709+02:00[Europe/Berlin]"}'

### missing order & POS ID
    curl -i -X POST -H "Content-Type: application/json" http://localhost:9000/order -d '{"orderId":"","items":[{"name":"salt & vinegar chips","quantity":2.0,"tags":["snacks","chips"],"itemId":"83451","unitPrice":1.25},{"name":"club soda","quantity":1.0,"tags":["drinks","carbonated","2l"],"itemId":"9988563","unitPrice":2.55}],"amount":4.75,"posId":"","customerId":"8300","taxAmount":0.0,"date":"2017-08-08T12:43:10.709+02:00[Europe/Berlin]"}'
