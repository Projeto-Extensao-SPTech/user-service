package com.dog_feliz.user_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.s3")
@Setter
@Getter
public class AwsS3Properties {
    private String bucketName;
    private String region;
    private String accessKey;
    private String secretKey;
}