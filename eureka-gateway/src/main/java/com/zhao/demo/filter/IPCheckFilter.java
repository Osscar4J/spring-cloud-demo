package com.zhao.demo.filter;

import org.bouncycastle.asn1.ocsp.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class IPCheckFilter implements GlobalFilter, Ordered {

    private Logger logger = LoggerFactory.getLogger(IPCheckFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if (getIp(headers).equalsIgnoreCase("127.0.0.2")){
            ServerHttpResponse response = exchange.getResponse();
            byte[] datas = "{\"code\":401}".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(datas);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        logger.info("IPCheck filter");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private String getIp(HttpHeaders httpHeaders) {
        return "127.0.0.1";
    }
}
