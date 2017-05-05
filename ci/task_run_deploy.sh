#!/bin/sh

set -ex

COMMIT=$(cd resource-femr-app && git rev-parse --short HEAD)

OLD_INSTANCE_ID=$(aws ec2 describe-instances --filters Name=tag:ID,Values=fEMR-demo | jq -r .Reservations[].Instances[].InstanceId)

aws ec2 create-tags --resources $OLD_INSTANCE_ID --tags Key=ID,Value=fEMR-demo-old

aws ec2 run-instances --image-id ami-ca6cf5dc --count 1 --instance-type t2.small --key-name concourse_id_rsa --security-group-ids sg-38b54e44 --subnet-id subnet-1b91f540 --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value='$COMMIT'},{Key=ID,Value=fEMR-demo}]' --user-data file://resource-femr-app/ci/deploy_server_config.sh > output.txt

NEW_INSTANCE_ID=$(cat output.txt | jq -r .Instances[].InstanceId)

aws ec2 wait instance-status-ok --instance-ids $NEW_INSTANCE_ID

aws ec2 associate-address --instance-id $NEW_INSTANCE_ID --allocation-id eipalloc-266cdf18

aws ec2 terminate-instances --instance-ids $OLD_INSTANCE_ID

