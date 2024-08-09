#!/bin/bash

# Step 1: Get the list of pods and grab the full name of the first pod starting with "springhello-deployment-"
pod_name=$(kubectl get pods --no-headers -o custom-columns=":metadata.name" | grep '^springhello-deployment-' | head -n 1)

if [ -z "$pod_name" ]; then
  echo "No pod found with the name starting with 'springhello-deployment-'"
  exit 1
fi

echo "Found pod: $pod_name"

# Step 2: Get logs from the init-certs container of the found pod
kubectl logs "$pod_name" -c init-certs
