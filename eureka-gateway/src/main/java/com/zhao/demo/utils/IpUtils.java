package com.zhao.demo.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

public class IpUtils {
    public static String getIpAddr(ServerHttpRequest request){
        HttpHeaders headers = request.getHeaders();
        List<String> ips = headers.get("X-Forwarded-For");
        String ip = "";
        if (ips != null && ips.size() > 0)
            ip = ips.get(0);
        return ip;
    }
}
