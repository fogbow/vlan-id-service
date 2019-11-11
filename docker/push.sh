#!/bin/bash

if [ "$#" -ne 1 ]; then
  echo "Usage: $0 <image_tag>"
  exit 1
fi

image_tag=$1

sudo docker push fogbow/resource-allocation-service:$image_tag