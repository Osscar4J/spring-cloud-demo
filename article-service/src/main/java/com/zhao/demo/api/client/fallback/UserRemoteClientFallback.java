package com.zhao.demo.api.client.fallback;

import com.zhao.demo.api.client.UserRemoteClient;
import org.springframework.stereotype.Component;

@Component
public class UserRemoteClientFallback implements UserRemoteClient {

    @Override
    public String hello() {
        return "fail by fallback";
    }
}
