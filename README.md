

curl -i -X POST -H "Content-Type: application/json" http://localhost:9000/order -d '{"orderId":"83123","items":[{"name":"salt & vinegar chips","quantity":2.0,"tags":["snacks","chips"],"itemId":"83451","unitPrice":1.25},{"name":"club soda","quantity":1.0,"tags":["drinks","carbonated","2l"],"itemId":"9988563","unitPrice":2.55}],"amount":4.75,"posId":"145a","customerId":"8300","taxAmount":0.0,"date":"2017-08-08T12:43:10.709+02:00[Europe/Berlin]"}'


### missing POS ID
curl -i -X POST -H "Content-Type: application/json" http://localhost:9000/order -d '{"orderId":"83123","items":[{"name":"salt & vinegar chips","quantity":2.0,"tags":["snacks","chips"],"itemId":"83451","unitPrice":1.25},{"name":"club soda","quantity":1.0,"tags":["drinks","carbonated","2l"],"itemId":"9988563","unitPrice":2.55}],"amount":4.75,"posId":"","customerId":"8300","taxAmount":0.0,"date":"2017-08-08T12:43:10.709+02:00[Europe/Berlin]"}'

### missing order & POS ID
curl -i -X POST -H "Content-Type: application/json" http://localhost:9000/order -d '{"orderId":"","items":[{"name":"salt & vinegar chips","quantity":2.0,"tags":["snacks","chips"],"itemId":"83451","unitPrice":1.25},{"name":"club soda","quantity":1.0,"tags":["drinks","carbonated","2l"],"itemId":"9988563","unitPrice":2.55}],"amount":4.75,"posId":"","customerId":"8300","taxAmount":0.0,"date":"2017-08-08T12:43:10.709+02:00[Europe/Berlin]"}'
