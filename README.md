# slack-operator
A set of Slack operations

## Running in docker

```shell
docker compose up -d --build
```

## Run tests like in CI/CD:
```shell
docker compose -f compose.test.ci.yaml up --menu=false --quiet-pull --build --exit-code-from slack-operator-test
```