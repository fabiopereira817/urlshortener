#!/bin/bash

echo "Running redis"
docker run -d --name=my-redis -p 6379:6379 redis

echo "Running url shortener service"
sbt -jvm-debug 5005 run

echo "Stopping redis"
docker stop my-redis
docker rm my-redis
