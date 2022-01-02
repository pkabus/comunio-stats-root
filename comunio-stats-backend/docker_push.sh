#!/bin/bash

MVN_VERSION=$(mvn -q \
    -Dexec.executable=echo \
    -Dexec.args='${project.version}' \
    --non-recursive \
    exec:exec)

MVN_NAME=$(mvn -q \
    -Dexec.executable=echo \
    -Dexec.args='${project.artifactId}' \
    --non-recursive \
    exec:exec)

DOCKERHUB_USR=pkabus

docker login -u $DOCKERHUB_USR

docker tag $MVN_NAME:$MVN_VERSION $DOCKERHUB_USR/$MVN_NAME:$MVN_VERSION
docker push $DOCKERHUB_USR/$MVN_NAME:$MVN_VERSION

