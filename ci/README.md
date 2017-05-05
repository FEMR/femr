# Concourse CI
Concourse CI pipeline for demo.teamfemr.org.

Hosted at concourse.teamfemr.org (sometimes :8080, depends on the day)

More info about Concourse CI: [https://concourse.ci](https://concourse.ci)


## Configuration:
Set up credentials
```
cp credentials.yml.example credentials.yml
vim credentials.yml
```


## Execution:
Login:
```
fly -t femr login
```

Set new or update pipeline:
```
fly set-pipeline -t femr --config pipeline.yml -p femr-test --non-interactive -l credentials.yml
```

## TODO:
* Set up public access to view status of pipeline
* Set up SSL
* Stop committing AWS credentials to GitHub
