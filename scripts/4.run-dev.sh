#!/bin/bash
kubectl delete deployment springhello-deployment
kubectl apply -f ../deployment.dev.yaml
kubectl apply -f ../service.dev.yaml
kubectl rollout restart deployment springhello-deployment

