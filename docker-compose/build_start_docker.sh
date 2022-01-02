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

# absolute compose file name (default value)
COMPOSE_FILE=$ROOT/docker-compose/comunio-stats-compose-amd64.yml

# get processor architecture
ARCH=`uname -m`
if [ "$ARCH" == "x86_64" ]; then \
    echo "x86_64 -> use adm64 docker images...";
elif [ "$ARCH" == "armv7l" ]; then \
    echo "armv7l -> use arm32v7 docker images...";
    DOCKERFILE=Dockerfile.piprod
    TAG=piprod
    COMPOSE_FILE=$ROOT/docker-compose/comunio-stats-compose-arm32v7.yml
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
docker build -f $BACKEND_PATH/$DOCKERFILE -t pkabus/comunio-stats-backend:$TAG $BACKEND_PATH

# build production frontend docker iamge
echo "Build frontend image..."
docker build -f $FRONTEND_PATH/$DOCKERFILE -t pkabus/comunio-stats-frontend:$TAG $FRONTEND_PATH

docker-compose -f $COMPOSE_FILE down $REMOVE_DB_VOLUME

# start prod env from compose
docker-compose -f $COMPOSE_FILE up -d
