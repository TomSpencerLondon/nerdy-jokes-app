#!/usr/bin/env bash

kind create cluster --config=kind-config.yml

kubectl apply -f ./ingress-nginx/controller.yml
kubectl apply -f ./ingress-nginx/metrics-svc.yml
kubectl wait --namespace ingress-nginx \
    --for=condition=ready pod \
    --selector=app.kubernetes.io/component=controller \
    --timeout=90s

kubectl apply -f ./prometheus/prometheus-operator-bundle.yml
kubectl apply -f ./prometheus/prometheus-instance.yml
kubectl apply -f ./prometheus/prometheus-servicemonitor.yml
kubectl apply -f ./prometheus/prometheus-ingress.yml

kubectl apply -f ./ingress-nginx/servicemonitor.yml

kubectl apply -f ./nerdy-jokes-app/deployment.yml
kubectl apply -f ./nerdy-jokes-app/servicemonitor.yml
kubectl apply -f ./nerdy-jokes-app/ingress.yml

kubectl apply -f ./grafana/crds -n monitoring
kubectl apply -f ./grafana/roles -n monitoring
kubectl apply -f ./grafana/operator.yml -n monitoring
kubectl apply -f ./grafana/instance.yml -n monitoring
kubectl apply -f ./grafana/prometheus-datasource.yml -n monitoring
kubectl apply -f ./grafana/servicemonitor.yml -n monitoring
