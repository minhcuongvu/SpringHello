kubectl delete configmap ca-cert
kubectl create configmap ca-cert \
  --from-file=../certs/rootCA.crt \
  --namespace=kube-system 
  # --from-file=../certs/local.registry.com.crt \
  # --from-file=../certs/local.registry.com.key

# kubectl get configmap ca-cert -o yaml