#!/bin/bash
pod_name=$(kubectl get pods --no-headers -o custom-columns=":metadata.name" | grep '^promtail-daemonset-' | head -n 1)

if [ -z "$pod_name" ]; then
  echo "No pod found with the name starting with 'promtail-daemonset-'"
  exit 1
fi

kubectl port-forward $pod_name 9080:9080