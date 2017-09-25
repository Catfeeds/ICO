package com.tongwii.ico.configurer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-25
 */
@Configuration
@PropertySource("classpath:config/regular_experssion.properties")
@ConfigurationProperties(prefix = "input")
@Setter
@Getter
public class TestConfig {
    public String email;
}
