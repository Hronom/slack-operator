package com.github.hronom.slack_operator;

import com.github.hronom.slack_operator.properties.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class SlackOperatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackOperatorApplication.class, args);
    }
}
