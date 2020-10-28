# Concourse CI
Concourse CI pipeline for fEMR

Hosted at https://concourse.teamfemr.org

More info about Concourse CI: [https://concourse.ci](https://concourse.ci)


## Configuration:
Set up credentials
```
cp credentials.yml.example credentials.yml
vim credentials.yml
```

Get the [fly CLI](https://concourse.ci/downloads.html)


## Execution:
Login:
```
fly --target femr login --team-name main --concourse-url https://concourse.teamfemr.org
```

Set new or update pipeline:
```
fly set-pipeline -t femr -c pipeline.yml -p femr-test --non-interactive -l credentials.yml
```

## TODO:
* Stop committing AWS credentials to GitHub
