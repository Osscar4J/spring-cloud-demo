package com.zhao.demo.config;

import com.zhao.demo.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class FilterConfiguration {

    private Logger logger = LoggerFactory.getLogger(FilterConfiguration.class);

    @Bean
    @Order(-1)
    public GlobalFilter a(){
        return (exchange, chain) -> {
            logger.info("first pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("third post filter");
            }));
        };
    }

    @Bean
    @Order(0)
    public GlobalFilter b(){
        return (exchange, chain) -> {
            logger.info("second pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("second post filter");
            }));
        };
    }

    @Bean
    @Order(1)
    public GlobalFilter c(){
        return (exchange, chain) -> {
            logger.info("third pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("first post filter");
            }));
        };
    }

    /**
     * IP限流
     * @return
     */
    @Bean
    public KeyResolver ipKeyResolver(){
        return exchange -> Mono.just(IpUtils.getIpAddr(exchange.getRequest()));
    }

//    /**
//     * 用户限流
//     * @return
//     */
//    @Bean
//    public KeyResolver userKeyResolver(){
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
//    }
}
