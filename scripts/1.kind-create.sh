#!/bin/bash
kind delete cluster --name wslkindmultinodes

# set higher connections count
sudo sysctl -w net.netfilter.nf_conntrack_max=655360

kind create cluster \
  --image kindest/node:v1.30.2 \
  --name wslkindmultinodes \
  --config ../kind-3nodes.yaml

# Modify kubeconfig to skip TLS verification
# kubectl config set-cluster kind-wslkindmultinodes --insecure-skip-tls-verify=true

# kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-rc6/aio/deploy/recommended.yaml
# kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.2.0/aio/deploy/recommended.yaml
# kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml
kubectl apply -f ../dashboard-v2.7.0.yaml

kubectl apply -f - <<EOF
apiVersion: v1
kind: ServiceAccount
metadata:
  name: admin-user
  namespace: kubernetes-dashboard
EOF

kubectl apply -f - <<EOF
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: admin-user
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
- kind: ServiceAccount
  name: admin-user
  namespace: kubernetes-dashboard
EOF

kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernetes-dashboard get secret | grep admin-user | awk '{print $1}')

DASHBOARD_PORT=8001
# Check if there is any process listening on the specified port
if sudo lsof -i :$DASHBOARD_PORT | grep LISTEN > /dev/null; then
  # Kill the process listening on the specified port
  sudo kill -9 $(sudo lsof -i :$DASHBOARD_PORT | grep LISTEN | awk '{print $2}')
  echo "Process on port $DASHBOARD_PORT has been killed."
else
  echo "No process is listening on port $DASHBOARD_PORT."
fi

kubectl proxy &