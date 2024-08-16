#!/bin/bash
# creating a separate docker container apart from the cluster
# mounting to /app for debugging
docker rm -f springhello || true && \
docker run --rm -d -p 8080:8080 -p 5005:5005 -v ~/repos/SpringHello/:/app --name springhello local.registry.com:32555/springhello:latest

# ./mvnw spring-boot:run