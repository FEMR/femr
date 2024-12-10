# Integration Tests

## Env Vars

```bash
docker run -it --rm -v $(pwd):/app -w /app femr-femr pandoc README.md -o README.md
```

## Running Tests

```bash
docker-compose -f docker-compose.test.yml up --build
```

## Running Tests with Coverage

```bash
docker-compose -f docker-compose.test.yml -f docker-compose.coverage.yml up --build
```

