# URL Shortener

This service will create a tiny url for a given url.

The service will generate a numeric unique id and will convert to Bas62, this will be the short url

Settings are defined under `conf/application.conf` file.

### Dependencies

Sbt  1.3.4
Scala 2.13.1

Backend storage options: in memory and Redis. These are enabled/disabled in `conf/application.conf` file.

### Build, test and run

this is a sbt/scala/play framework project.

To compile run in console: `sbt clean compile`

To run tests run in console: `sbt test`

To run the service execute in console: `sbt run`
Also, to run the service there is a handy shell script (`scripts/debug.sh`) which will run an instance of Redis in Docker and will run this service. When finished pressing `ctrl+x` will terminate the session and will kill the docker instance.

### Interacting with the service

Two endpoints are provided:

#### POST /
Json payload is expected with the format:
```json
{
  "url": [the origin url],
  "ttl": [time this url will be stored. Optional, if not provided default defined in config will be used]
}
```

Response *200(OK)* with Json payload
```json
{
  "sourceUrl": [the origin url],  
  "shortUrl": [the short url],
  "ttl": [the ttl applied]
}
```

#### GET /:shortUrl
Response *301(Moved permanently)* with the original url in the `Location` header.
