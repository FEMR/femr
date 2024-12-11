# Integration Tests

Integration tests for fEMR. This project will bring up the entire fEMR stack with docker then run tests against it.

Assumes that the femr docker container's name is `femr-femr`, otherwise you will need to re-build it with the new name.

## Run with Docker

Ensure that the femr container is built with name `femr-femr`, before running the tests.

In the `integration-test` directory, run the tests with:

```bash
docker compose up --build
```

## Build Image

In the root of the fEMR repository:

```bash
docker compose build
```
