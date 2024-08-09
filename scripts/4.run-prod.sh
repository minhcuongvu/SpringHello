#!/bin/bash
kubectl delete deployment springhello-deployment
kubectl apply -f ../deployment.prod.yaml
kubectl apply -f ../service.prod.yaml
kubectl rollout restart deployment springhello-deployment

