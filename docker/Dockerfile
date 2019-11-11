FROM openjdk:8

ARG COMMON_BRANCH
ARG VLAN_BRANCH

# Install.
RUN \
  sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
  apt-get update -y && \
  apt-get upgrade -y && \
  apt-get install -y build-essential && \
  apt-get install openjfx -y && \
  apt-get install -y software-properties-common && \
  apt-get install -y byobu curl git htop man unzip vim wget maven && \
  apt-get install -y net-tools iputils-ping && \
  rm -rf /var/lib/apt/lists/*

# Set environment variables.
ENV HOME /root

# Define working directory.
WORKDIR /root

# Installing Common
RUN \
  git clone https://github.com/fogbow/common.git && \
  (cd common && git checkout $COMMON_BRANCH && mvn install -Dmaven.test.skip=true)

# Installing service
RUN \
  git clone https://github.com/fogbow/vlan-id-service.git && \
  (cd vlan-id-service && git checkout $VLAN_BRANCH && mvn install -Dmaven.test.skip=true)

# Define working directory.
WORKDIR /root/vlan-id-service

RUN \
  mvn dependency:sources