iis:
	kubectl apply -f src/infra/pg-stateful.yml
	kubectl apply -f src/infra/pg-secrets.yml
	kubectl apply -f src/infra/pg-service.yml
	kubectl apply -f src/infra/iis-secrets.yml
	kubectl apply -f src/infra/iis-deployment.yml
	kubectl apply -f src/infra/iis-service.yml

