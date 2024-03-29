package com.zhao.demo.controller;

import com.zhao.demo.api.client.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ArticleController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private UserRemoteClient userRemoteClient;

    @GetMapping("/article/callHello")
    public String callHello(){
        return restTemplate.getForObject("http://eureka-client-user-service/user/hello", String.class);
    }

    @GetMapping("/choose")
    public Object chooseUrl(){
        ServiceInstance instance = loadBalancerClient.choose("EUREKA-CLIENT-USER-SERVICE");
        return instance;
    }

    @GetMapping("/callHello")
    public String callHello2(){
        String res = userRemoteClient.hello();
        return res;
    }

}
