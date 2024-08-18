kubectl get secret --namespace monitoring my-grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo

echo "http://prometheus.kafka.svc.cluster.local:9090"
echo "http://loki-gateway.loki.svc.cluster.local/"
echo "X-Scope-OrgId: foo"