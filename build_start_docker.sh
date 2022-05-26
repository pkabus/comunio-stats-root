#!/bin/bash

# absolute root directory (default value)
ROOT=/home/$USER/projects/comunio-stats-root

# absolute subproject paths
BACKEND_PATH=$ROOT/comunio-stats-backend
FRONTEND_PATH=$ROOT/comunio-stats-frontend

# relative Dockerfile names (default value)
DOCKERFILE=Dockerfile

# docker image tag (default value)
TAG=prod

# absolute compose file name of product components (default value)
COMPOSE_FILE_PROD=$ROOT/docker-compose/comunio-stats-compose-amd64.yml

# absolute compose file name of nginx and certbot components (default value)
COMPOSE_FILE_NGINX_CERT=$ROOT/docker-compose-nginx-certbot/docker-compose.yml

# get processor architecture
ARCH=`uname -m`
if [ "$ARCH" == "x86_64" ]; then \
    echo "x86_64 -> use adm64 docker images...";
elif [ "$ARCH" == "armv7l" ]; then \
    echo "armv7l -> use arm32v7 docker images...";
    DOCKERFILE=Dockerfile.piprod
    TAG=piprod
    COMPOSE_FILE_PROD=$ROOT/docker-compose/comunio-stats-compose-arm32v7.yml
else
    echo "unknown architecture -> cancel :(";
    exit 1
fi

REMOVE_DB_VOLUME=
# remove db volume?
if [ "$1" == "-v" ]; then \
    REMOVE_DB_VOLUME=-v
fi

# build production backend docker image
echo "Build backend image..."
docker build -f $BACKEND_PATH/$DOCKERFILE --rm -t pkabus/comunio-stats-backend:$TAG $BACKEND_PATH

# build production frontend docker iamge
echo "Build frontend image..."
docker build -f $FRONTEND_PATH/$DOCKERFILE --rm -t pkabus/comunio-stats-frontend:$TAG $FRONTEND_PATH

# stop and remove container as compose-network will be recreated
docker-compose -f $COMPOSE_FILE_PROD down $REMOVE_DB_VOLUME
docker-compose -f $COMPOSE_FILE_NGINX_CERT down

# recreate compose-network which is necessary for the communication between the prod and nginx-certbot envs
docker network rm compose-network
docker network create compose-network

# start prod env from compose
docker-compose -f $COMPOSE_FILE_PROD up -d

# start nginx and certbot env from compose
docker-compose -f $COMPOSE_FILE_NGINX_CERT up -d
