package com.zhao.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zhao.demo.utils.IpUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class IpFilter extends ZuulFilter {

    private List<String> blackIpList = Arrays.asList("127.0.0.1", "192.168.31.155");

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
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String ip = IpUtils.getIpAddr(ctx.getRequest());
        if (!StringUtils.isEmpty(ip) && blackIpList.contains(ip)){
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody("{\"code\":401}");
            ctx.getResponse().setContentType("application/json; charset=utf-8");
            return null;
        }
        return null;
    }
}
