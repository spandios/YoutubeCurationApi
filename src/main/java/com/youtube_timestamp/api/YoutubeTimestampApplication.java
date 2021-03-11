package com.youtube_timestamp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties
@EnableAspectJAutoProxy
public class YoutubeTimestampApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeTimestampApplication.class, args);
    }

}
