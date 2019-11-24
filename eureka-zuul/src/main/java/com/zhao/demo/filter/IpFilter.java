package com.zhao.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zhao.demo.utils.IpUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class IpFilter extends ZuulFilter {

    private List<String> blackIpList = Arrays.asList("127.0.0.1");

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        Object success = context.get("isSuccess");
        return success == null ? true : Boolean.parseBoolean(success.toString());
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String ip = IpUtils.getIpAddr(context.getRequest());
        if (!StringUtils.isEmpty(ip) && blackIpList.contains(ip)){
            context.setSendZuulResponse(false);
            context.setResponseBody("{\"code\":401}");
            context.getResponse().setContentType("application/json; charset=utf-8");
            context.set("isSuccess", false);
            return null;
        }
        return null;
    }
}
