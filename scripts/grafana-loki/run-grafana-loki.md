NAME: loki
LAST DEPLOYED: Sun Aug 11 13:49:23 2024
NAMESPACE: default
STATUS: deployed
REVISION: 1
NOTES:
***********************************************************************
 Welcome to Grafana Loki
 Chart version: 6.10.0
 Chart Name: loki
 Loki version: 3.1.1
***********************************************************************

** Please be patient while the chart is being deployed **

Tip:

  Watch the deployment status using the command: kubectl get pods -w --namespace default

If pods are taking too long to schedule make sure pod affinity can be fulfilled in the current cluster.

***********************************************************************
Installed components:
***********************************************************************
* loki

Loki has been deployed as a single binary.
This means a single pod is handling reads and writes. You can scale that pod vertically by adding more CPU and memory resources.


***********************************************************************
Sending logs to Loki
***********************************************************************

Loki has been configured with a gateway (nginx) to support reads and writes from a single component.

You can send logs from inside the cluster using the cluster DNS:

http://loki-gateway.default.svc.cluster.local/loki/api/v1/push

You can test to send data from outside the cluster by port-forwarding the gateway to your local machine:

  kubectl port-forward --namespace default svc/loki-gateway 3100:80 &

And then using http://127.0.0.1:3100/loki/api/v1/push URL as shown below:

```
curl -H "Content-Type: application/json" -XPOST -s "http://127.0.0.1:3100/loki/api/v1/push"  \
--data-raw "{\"streams\": [{\"stream\": {\"job\": \"test\"}, \"values\": [[\"$(date +%s)000000000\", \"fizzbuzz\"]]}]}" \
-H X-Scope-OrgId:foo
```

Then verify that Loki did received the data using the following command:

```
curl "http://127.0.0.1:3100/loki/api/v1/query_range" --data-urlencode 'query={job="test"}' -H X-Scope-OrgId:foo | jq .data.result
```

***********************************************************************
Connecting Grafana to Loki
***********************************************************************

If Grafana operates within the cluster, you'll set up a new Loki datasource by utilizing the following URL:

http://loki-gateway.default.svc.cluster.local/

***********************************************************************
Multi-tenancy
***********************************************************************

Loki is configured with auth enabled (multi-tenancy) and expects tenant headers (`X-Scope-OrgID`) to be set for all API calls.

You must configure Grafana's Loki datasource using the `HTTP Headers` section with the `X-Scope-OrgID` to target a specific tenant.
For each tenant, you can create a different datasource.

The agent of your choice must also be configured to propagate this header.
For example, when using Promtail you can use the `tenant` stage. https://grafana.com/docs/loki/latest/send-data/promtail/stages/tenant/

When not provided with the `X-Scope-OrgID` while auth is enabled, Loki will reject reads and writes with a 404 status code `no org id`.

You can also use a reverse proxy, to automatically add the `X-Scope-OrgID` header as suggested by https://grafana.com/docs/loki/latest/operations/authentication/

For more information, read our documentation about multi-tenancy: https://grafana.com/docs/loki/latest/operations/multi-tenancy/

> When using curl you can pass `X-Scope-OrgId` header using `-H X-Scope-OrgId:foo` option, where foo can be replaced with the tenant of your choice.