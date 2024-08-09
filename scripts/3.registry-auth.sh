#!/bin/bash
echo $HOST_PORT
kubectl delete secret myregistrykey
kubectl create secret docker-registry myregistrykey \
  --docker-server=local.registry.com:32555 \
  --docker-username=registry \
  --docker-password=pwd