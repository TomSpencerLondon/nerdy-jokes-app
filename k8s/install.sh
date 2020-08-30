#!/usr/bin/env bash

kind create cluster --config=kind-config.yaml

kubectl apply -f ./ingress-nginx/controller.yaml
kubectl apply -f ./ingress-nginx/metrics-svc.yaml
kubectl wait --namespace ingress-nginx \
    --for=condition=ready pod \
    --selector=app.kubernetes.io/component=controller \
    --timeout=90s

kubectl apply -f ./prometheus/prometheus-operator-bundle.yaml
kubectl apply -f ./prometheus/prometheus-instance.yaml
kubectl apply -f ./prometheus/prometheus-servicemonitor.yaml
kubectl apply -f ./prometheus/prometheus-ingress.yaml

kubectl apply -f ./ingress-nginx/servicemonitor.yaml

kubectl apply -f ./nerdy-jokes-app/deployment.yaml
kubectl apply -f ./nerdy-jokes-app/servicemonitor.yaml
kubectl apply -f ./nerdy-jokes-app/ingress.yaml

kubectl apply -f ./grafana/crds -n monitoring
kubectl apply -f ./grafana/roles -n monitoring
kubectl apply -f ./grafana/operator.yaml -n monitoring
kubectl apply -f ./grafana/instance.yaml -n monitoring
kubectl apply -f ./grafana/prometheus-datasource.yaml -n monitoring
kubectl apply -f ./grafana/servicemonitor.yaml -n monitoring
