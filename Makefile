USERNAME			:= ifqthenp
COMMIT				:= $(shell git rev-parse HEAD)

DESCRIBE			:= $(shell git describe --match "v*" --always --tags)
DESCRIBE_PARTS		:= $(subst -, ,$(DESCRIBE))
VERSION_TAG			:= $(word 1,$(DESCRIBE_PARTS))

.PHONY: all
all: build push

.PHONY: build
build: build_jar build_docker_app build_docker_prometheus build_docker_blackbox_exporter

.PHONY: push
push: push_app push_prometheus push_blackbox

.PHONY: app
app: build_jar build_docker_app push_app

.PHONY: prometheus
prometheus: build_docker_prometheus push_prometheus

.PHONY: blackbox
blackbox: build_docker_blackbox_exporter push_blackbox

# >>> NERDY JOKES APP >>> ##############################################################################################

APP_DOCKERFILE	:= docker/nerdy-jokes-app/Dockerfile
APP_IMAGE_NAME	:= ${USERNAME}/nerdy-jokes-app
APP_IMG			:= ${APP_IMAGE_NAME}:${COMMIT}
APP_TAG			:= ${APP_IMAGE_NAME}:${VERSION_TAG}
APP_LATEST		:= ${APP_IMAGE_NAME}:latest

build_jar:
	@./gradlew clean build bootJar

build_docker_app:
	@DOCKER_BUILDKIT=1 docker image build -f ${APP_DOCKERFILE} -t ${APP_IMG} .
	@docker tag ${APP_IMG} ${APP_LATEST}
	@docker tag ${APP_IMG} ${APP_TAG}

push_app:
	@docker push ${APP_IMAGE_NAME}

# >>> PROMETHEUS >>> ###################################################################################################

PROM_DOCKERFILE	:= docker/prometheus/Dockerfile
PROM_IMAGE_NAME	:= ${USERNAME}/nerdy-jokes-prometheus
PROM_IMG		:= ${PROM_IMAGE_NAME}:${TAG}
PROM_LATEST		:= ${PROM_IMAGE_NAME}:latest

build_docker_prometheus:
	@DOCKER_BUILDKIT=1 docker image build -f ${PROM_DOCKERFILE} -t ${PROM_IMG} .
	@docker tag ${PROM_IMG} ${PROM_LATEST}

push_prometheus:
	@docker push ${PROM_IMAGE_NAME}

# >>> BLACKBOX EXPORTER >>> ############################################################################################

BLACK_DOCKERFILE	:= docker/blackbox_exporter/Dockerfile
BLACK_IMAGE_NAME	:= ${USERNAME}/nerdy-jokes-blackbox-exporter
BLACK_IMG			:= ${BLACK_IMAGE_NAME}:${TAG}
BLACK_LATEST		:= ${BLACK_IMAGE_NAME}:latest

build_docker_blackbox_exporter:
	@DOCKER_BUILDKIT=1 docker image build -f ${BLACK_DOCKERFILE} -t ${BLACK_IMG} .
	@docker tag ${BLACK_IMG} ${BLACK_LATEST}

push_blackbox:
	@docker push ${BLACK_IMAGE_NAME}

# >>> DOCKER COMPOSE >>> ###############################################################################################

docker_run:
	@LD_LIBRARY_PATH=/usr/local/lib docker-compose -f docker/docker-compose.yml up

docker_clean:
	-docker container rm $$(docker container ls -qa)
	-docker volume rm $$(docker volume ls -q)
	-docker network rm $$(docker network ls -q)
	-docker image prune -a
