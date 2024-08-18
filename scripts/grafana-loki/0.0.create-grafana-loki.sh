#!/bin/bash
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update
# helm uninstall loki
# helm install --values loki-distributed.yaml loki grafana/loki
# helm upgrade loki grafana/loki --values loki-distributed.yaml

# install=====
kubectl create namespace loki
helm install --values loki-scalable.yaml loki grafana/loki --namespace loki
# update=======
# helm upgrade loki grafana/loki --values loki-scalable.yaml

# kubectl port-forward --namespace loki svc/loki-gateway 3100:80 &

#send test=====
# curl -H "Content-Type: application/json" -XPOST -s "http://127.0.0.1:3100/loki/api/v1/push"  \
# --data-raw "{\"streams\": [{\"stream\": {\"job\": \"test\"}, \"values\": [[\"$(date +%s)000000000\", \"fizzbuzz\"]]}]}" \
# -H X-Scope-OrgId:foo
#retrieve test=====
# curl "http://127.0.0.1:3100/loki/api/v1/query_range" --data-urlencode 'query={job="test"}' -H X-Scope-OrgId:foo | jq .data.result


# grafana
# make sure datasource connection has the header contains 'X-Scope-OrgId' and value 'foo'
kubectl create namespace monitoring
# helm delele my-grafana
helm install my-grafana grafana/grafana --namespace monitoring