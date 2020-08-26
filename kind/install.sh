#!/usr/bin/env bash

kind create cluster --config=kind-config.yml

kubectl apply -f ./ingress/nginx-ingress.yml
kubectl wait --namespace ingress-nginx \
    --for=condition=ready pod \
    --selector=app.kubernetes.io/component=controller \
    --timeout=90s

kubectl apply -f ./ingress/nginx-servicemonitor.yml

kubectl apply -f ./namespace/ns.yml

kubectl apply -f ./prometheus/prometheus-operator-bundle.yml
kubectl apply -f ./prometheus/prometheus-instance.yml
kubectl apply -f ./prometheus/prometheus-servicemonitor.yml
kubectl apply -f ./prometheus/prometheus-ingress.yml

kubectl apply -f ./app/nerdy-jokes-app.yml
kubectl apply -f ./app/nerdy-jokes-app-servicemonitor.yml

