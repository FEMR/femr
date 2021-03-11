#!/bin/bash
# Creates a backup of the existing MySQL database and pushes it to a centralized Amazon RDS database.
#Backing up local database.
mysqldump \
    --databases femr \
    --master-data=2  \
    --single-transaction \
    --order-by-primary \
    -r backup.sql
#Compress backup file to conserve network resources.
gzip backup.sql
#Transfer backup file to Amazon EC2 instance.
scp -r -i femrtest.pem backup.sql.gz ec2-user@ec2-3-15-230-33.us-east-2.compute.amazonaws.com://home/ec2-user/test
#AWS command that unzips backup file and utilizes sed to remove definer information to prevent errors.
aws ssm send-command --document-name "AWS-RunShellScript" --document-version "1" --targets '[{"Key":"InstanceIds","Values":["i-06c56ec70246c0870"]}]' --parameters '{"commands":["gzip -d backup.sql.gz","sed '"'"'s/\\sDEFINER=`[^`]*`@`[^`]*`//g'"'"' -i backup.sql"],"workingDirectory":["/home/ec2-user/test"],"executionTimeout":["3600"]}' --comment "Unzips the backup file." --timeout-seconds 600 --max-concurrency "50" --max-errors "0" --cloud-watch-output-config '{"CloudWatchOutputEnabled":true}' --region us-east-2
#Generates a authentication token and pushes backup file from EC2 instance to Amazon RDS database.
aws ssm send-command --document-name "AWS-RunShellScript" --document-version "1" --targets '[{"Key":"InstanceIds","Values":["i-06c56ec70246c0870"]}]' --parameters '{"commands":["RDSHOST=\"central.ccc42svxvmzp.us-east-2.rds.amazonaws.com\"","TOKEN=\"$(aws rds generate-db-auth-token --hostname $RDSHOST --port 3306 --region us-east-2 --username yash)\"","","mysql --host=$RDSHOST --port=3306 --ssl-ca=rds-ca-2019-root.pem --user=yash --password=$TOKEN < test/backup.sql"],"workingDirectory":["/home/ec2-user/"],"executionTimeout":["3600"]}' --comment "Pushes backup to master database. " --timeout-seconds 600 --max-concurrency "50" --max-errors "0" --cloud-watch-output-config '{"CloudWatchOutputEnabled":true}' --region us-east-2
#Cleans up remaining files on EC2 instance.
aws ssm send-command --document-name "AWS-RunShellScript" --document-version "1" --targets '[{"Key":"InstanceIds","Values":["i-06c56ec70246c0870"]}]' --parameters '{"workingDirectory":["/home/ec2-user/test"],"executionTimeout":["3600"],"commands":["mv backup.sql old_backup.sql"]}' --comment "Cleans up." --timeout-seconds 600 --max-concurrency "50" --max-errors "0" --cloud-watch-output-config '{"CloudWatchOutputEnabled":true}' --region us-east-2
#Removes leftover archive files. TODO: Move them to a different folder to store backup history.
rm backup.sql.gz