package com.github.hronom.slack_operator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public record ApplicationProperties(
        String slackToken,
        String slackChannel
) {
}
