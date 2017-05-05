#
# Dockerfile for testing fEMR
#
# fEMR: https://github.com/femr/femr
# Dockerfile: https://github.com/femr/femr
#

# Get the base image
FROM ubuntu:16.04

# Add repository for SBT
RUN echo "deb http://dl.bintray.com/sbt/debian /" | \
    tee -a /etc/apt/sources.list.d/sbt.list

RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823

# Let us use the add-apt-repository command
RUN apt-get update && apt-get install -y software-properties-common

# Install Oracle Java 8, SBT, and pip
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer \
  sbt \
  python-pip \
  jq \
  oracle-java8-set-default && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer

RUN pip install --upgrade --user awscli

ENV PATH="/root/.local/bin:${PATH}"

