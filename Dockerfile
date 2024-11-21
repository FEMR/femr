FROM sbtscala/scala-sbt:eclipse-temurin-jammy-8u352-b08_1.9.0_2.12.17 AS builder

#build varaibles
#ENV SBT_VERSION 1.1.5
ENV PROJECT_HOME /usr/src

#RUN mkdir -p $PROJECT_HOME/activator $PROJECT_HOME/app

RUN apt-get update && apt-get install -y \
    unzip \
    && rm -rf /var/lib/apt/lists/*

#WORKDIR $PROJECT_WORKPLACE/activator
#RUN wget https://downloads.typesafe.com/typesafe-activator/1.3.6/typesafe-activator-1.3.6.zip && \
#    unzip typesafe-activator-1.3.6.zip && chmod a+x activator-dist-1.3.6/activator
#ENV PATH $PATH:/activator-1.3.6

# Install Play Framework
#RUN curl -O https://downloads.typesafe.com/typesafe-activator/1.3.6/typesafe-activator-1.3.6.zip

#RUN unzip typesafe-activator-1.3.6.zip -d / && rm typesafe-activator-1.3.6.zip && ls && sudo chmod a+x /activator-dist-1.3.6/activator

# Install curl
#RUN \
 # apt-get update && \
 # apt-get -y install curl

# Install sbt
#RUN \
  #mkdir /working/ && \
  #cd /working/ && \
  #curl -L -o sbt-$SBT_VERSION.deb https://repo.scala-sbt.org/scalasbt/debian/sbt-$SBT_VERSION.deb && \
  #dpkg -i sbt-$SBT_VERSION.deb && \
  #rm sbt-$SBT_VERSION.deb && \
  #apt-get update && \
  #apt-get install sbt && \
  #cd && \
  #rm -r /working/ && \
  #sbt sbtVersion

COPY Build.sbt .
COPY project ./project

RUN --mount=type=cache,target=/root/.ivy2 \
        --mount=type=cache,target=/root/.sbt \
        sbt update

# Setup path variables and copy fEMR into container
#ENV PATH $PROJECT_HOME/activator/activator-dist-1.3.10/bin:$PATH
#ENV PATH $PROJECT_WORKPLACE/build/target/universal/stage/bin:$PATH
COPY . $PROJECT_HOME/app
COPY ../speedtest /usr/src/speedtest
WORKDIR $PROJECT_HOME/app

RUN \
    rm $PROJECT_HOME/app/conf/application.conf && \
    mv $PROJECT_HOME/app/conf/application.docker.conf $PROJECT_HOME/app/conf/application.conf

#RUN sbt clean compile
RUN --mount=type=cache,target=/root/.ivy2 \
    --mount=type=cache,target=/root/.sbt \
    sbt dist

WORKDIR $PROJECT_HOME/app/target/universal
RUN unzip femr-*.zip && rm femr-*.zip

FROM openjdk:jre-alpine

RUN apk add --no-cache bash python3 py3-pip gcc python3-dev musl-dev linux-headers
RUN pip3 install psutil

#database variables
ENV DB_URL "jdbc:mysql://localhost:3306/femr_db?characterEncoding=UTF-8&useSSL=false"
ENV DB_USER "username"
ENV DB_PASS "password"

COPY --from=builder /usr/src/app/target/universal/femr-* /opt/bin/femr

#open port 9000 for connections
EXPOSE 9000

# run fEMR using env variables
#ENTRYPOINT url=$DB_URL usr=$DB_USER pass=$DB_PASS sbt ~run
ENTRYPOINT ["/bin/bash", "-c", "/opt/bin/femr/bin/femr"]