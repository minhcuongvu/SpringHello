#!/bin/bash
# old way
# kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernetes-dashboard get secret | grep admin-user | awk '{print $1}')

# new way
# get token for admin-user
kubectl -n kubernetes-dashboard create token admin-user
