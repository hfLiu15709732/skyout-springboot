package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean//确保spring容器中只有这一个util对象，oss上传只需要一个对象即可
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("阿里云对象存储工具对象创建 ");
        AliOssUtil aliOssUtil=new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                 aliOssProperties.getBucketName());
        return aliOssUtil;
    }
}
