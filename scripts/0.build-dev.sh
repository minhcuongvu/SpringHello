#!/bin/bash
# build and push to a private registry
docker build -f ../dockerfile.development -t local.registry.com:32555/springhello:latest ../
docker push local.registry.com:32555/springhello:latest