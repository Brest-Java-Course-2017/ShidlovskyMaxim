# ShidlovskyMaxim

Auto showroom project.


Start REST: cd rest mvn jetty:run

Start WEB app: cd js-client chrome index.html

curl -v "localhost:8088/cars"

curl -v "localhost:8088/car/period?from=01-01-2010&to=01-01-2014"

curl -v "localhost:8088/car/1"

curl -v "localhost:8088/car/model/X5"

curl -H "Content-Type: application/json" -X POST -d '{"model":"someModel","releaseDate":"01-01-2011","amount":10, "producerId":1}' -v "localhost:8088/car"

curl -H "Content-Type: application/json" -X PUT -d '{"carId":1,"model":"updatedModel","releaseDate":"01-01-2011", "amount":15, "producerId":1}' -v "localhost:8088/car"

curl -X DELETE localhost:8088/car/5

curl -v "localhost:8088/cars/amount"

curl -v "localhost:8088/producer/1/cars"

curl -H "Content-Type: application/json" -X POST -d '{"name":"producerName","country":"producerCountry"}' -v "localhost:8088/producer"

curl -H "Content-Type: application/json" -X PUT -d '{"producerId":1,"name":"updatedName","country":"updatedCountry"}' -v "localhost:8088/producer"

curl -X DELETE localhost:8088/producer/1

curl -v "localhost:8088/producers"

curl -v "localhost:8088/producers/amount"

curl -v "localhost:8088/producer/car/1"

curl -v "localhost:8088/producer/name/Mercedes"

curl -v "localhost:8088/producer/1/cars/amount"