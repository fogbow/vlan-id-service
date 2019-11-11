#!/bin/bash

if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <git vlan_branch> <docker_tag>"
  exit 1
fi

dependecies_properties_path=../src/main/resources/dependencies.properties

vlan_branch=$1
docker_tag=$2

sudo docker build --build-arg RAS_BRANCH=$vlan_branch \
 --build-arg COMMON_BRANCH=develop --no-cache -t fogbow/vlan-id-service:$docker_tag .

