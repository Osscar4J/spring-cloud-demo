package com.zhao.demo.api.client;

import com.zhao.demo.api.client.fallback.UserRemoteClientFallback;
import com.zhao.demo.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        value = "eureka-client-user-service",
        configuration = FeignConfiguration.class,
        fallback = UserRemoteClientFallback.class
)
public interface UserRemoteClient {

    @GetMapping("/user/hello")
    String hello();
}
