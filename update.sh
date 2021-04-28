#!/bin/sh
# Stop femr app by killing java all java processes
killall -9 java

# stop mysql
sudo launchctl unload -F /Library/LaunchDaemons/com.oracle.oss.mysql.mysqld.plist

# Builds, (re)creates, starts, and attaches to containers
docker-compose up --force-recreate --build -d

# remove unused images without prompting for confirmation
#docker remove prune -f

# start mysql
sudo launchctl load -F /Library/LaunchDaemons/com.oracle.oss.mysql.mysqld.plist