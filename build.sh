#!/bin/sh -x

# mvn spring-boot:build-image

mvn -U -Pwith-docker clean install

docker stop airq-decoder || true
