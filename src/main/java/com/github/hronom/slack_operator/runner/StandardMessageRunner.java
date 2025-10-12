package com.github.hronom.slack_operator.runner;

import com.github.hronom.slack_operator.properties.ApplicationProperties;
import com.slack.api.Slack;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Inspired by:
 * * <a href="https://slack.dev/java-slack-sdk/guides/web-api-basics">Using the Web API</a>
 * * <a href="https://github.com/slackapi/java-slack-sdk">java-slack-sdk</a>
 */
@Service
@Profile("standard-message")
public class StandardMessageRunner implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ApplicationProperties applicationProperties;
    private final Slack slack;

    public StandardMessageRunner(ApplicationProperties applicationProperties, Slack slack) {
        this.applicationProperties = applicationProperties;
        this.slack = slack;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("Preparing message...");
        List<LayoutBlock> blocks = new LinkedList<>();
        blocks.add(
                new DividerBlock()
        );

        Map<String, String> env = System.getenv();
        for (int i = 0; i < 50; i++) {
            String envName = "APP_SORTED_MARKDOWN_SECTION_" + i;
            String envValue = env.get(envName);
            if (StringUtils.hasText(envValue)) {
                LOGGER.info("Processing {}. Value: '{}'...", envName, envValue);
                blocks.add(
                        new SectionBlock(
                                new MarkdownTextObject(
                                        envValue,
                                        null
                                ),
                                null,
                                null,
                                null,
                                null
                        )
                );
            }
        }
        for (String envName : env.keySet()) {
            if (envName.startsWith("APP_MARKDOWN_SECTION_")) {
                String envValue = env.get(envName);
                LOGGER.info("Processing {}. Value: '{}'...", envName, envValue);
                blocks.add(
                        new SectionBlock(
                                new MarkdownTextObject(
                                        envValue,
                                        null
                                ),
                                null,
                                null,
                                null,
                                null
                        )
                );
            }
        }

        LOGGER.info("Sending message to slack channel '{}'...", applicationProperties.slackChannel());
        ChatPostMessageResponse response = slack
                .methods(applicationProperties.slackToken())
                .chatPostMessage(req -> req
                        .channel(applicationProperties.slackChannel())
                        .blocks(blocks)
                );
        if (response.isOk()) {
            LOGGER.info("Message send.");
        } else {
            String errorCode = response.getError();
            LOGGER.info("Message not send, error {}", errorCode);
            throw new IllegalStateException("Message not send, error " + errorCode);
        }
    }
}
