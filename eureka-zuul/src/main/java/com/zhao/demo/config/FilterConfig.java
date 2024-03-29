package com.zhao.demo.config;

import com.zhao.demo.filter.ErrorFilter;
import com.zhao.demo.filter.IpFilter;
import com.zhao.demo.filter.LogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public IpFilter ipFilter(){
        return  new IpFilter();
    }

    @Bean
    public ErrorFilter errorFilter(){
        return  new ErrorFilter();
    }

    @Bean
    public LogFilter logFilter(){
        return  new LogFilter();
    }
}
