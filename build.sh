#!/bin/bash

set -e
set -x

sbt -batch universal:stage
docker build . -t enrichment:latest
kubectl apply -f deploy/
