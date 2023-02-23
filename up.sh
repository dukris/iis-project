# shellcheck disable=SC2164
cd src/infra
kubectl apply -f pg-secrets.yml
kubectl apply -f pg-stateful.yml
kubectl apply -f pg-service.yml
kubectl apply -f iis-secrets.yml
kubectl apply -f iis-deployment.yml
kubectl apply -f iis-service.yml