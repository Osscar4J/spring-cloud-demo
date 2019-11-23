package com.zhao.demo.config;

import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

@Configuration
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public BasicAuthenticationInterceptor basicAuthenticationInterceptor(){
        return new BasicAuthenticationInterceptor("user", "123123");
    }

    /**
     * 超时时间，第一个是连接超时时间：5000， 第二个是读取超时时间：10000
     * @return
     */
    @Bean
    public Request.Options options(){
        return new Request.Options(5000, 10000);
    }
}
