package com.zhao.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public abstract class BaseFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        Object success = context.get("isSuccess");
        return success == null ? true : Boolean.parseBoolean(success.toString());
    }
}
