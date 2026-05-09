<!-- TOC -->
* [slack-operator](#slack-operator)
  * [Running in docker](#running-in-docker)
  * [Run tests like in CI/CD:](#run-tests-like-in-cicd)
  * [Usage:](#usage)
* [Misc](#misc)
<!-- TOC -->

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

## Usage:

Launch app.

Pass environment variables:
```shell
SPRING_PROFILES_ACTIVE=json-logs,standard-message

APP_SLACK_CHANNEL=change
APP_SLACK_TOKEN=change

APP_SORTED_MARKDOWN_SECTION_1="Test message: `It's a test`"
APP_SORTED_MARKDOWN_SECTION_2="Second test message: `It's a test`"
```

# Misc

1