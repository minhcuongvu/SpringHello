#!/bin/bash
kubectl create deployment nginx --image=nginx --port=80
kubectl create service nodeport nginx --tcp=80:80 --node-port=30000