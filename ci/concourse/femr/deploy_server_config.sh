#!/bin/bash

cd /opt/femr
aws s3 cp s3://femr-app/femr.zip .
chown ubuntu:ubuntu /opt/femr/femr.zip
unzip /opt/femr/femr.zip
mv femr-* femr
cd femr
aws s3 cp s3://femr-app/prod.conf .
mkdir public
mkdir public/img
aws s3 cp s3://femr-app/defaultProfile.png public/img
cd bin
chmod +x femr

/opt/femr/femr/bin/femr -Dconfig.file=/opt/femr/femr/prod.conf > /opt/femr/femr/log &

iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to 8081

