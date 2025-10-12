package com.github.hronom.slack_operator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public class ApplicationProperties {
    private String slackToken;
    private String slackChannel;

    public String getSlackToken() {
        return slackToken;
    }

    public void setSlackToken(String slackToken) {
        this.slackToken = slackToken;
    }

    public String getSlackChannel() {
        return slackChannel;
    }

    public void setSlackChannel(String slackChannel) {
        this.slackChannel = slackChannel;
    }
}
