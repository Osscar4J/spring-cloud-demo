package com.zhao.demo.filter;

import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Enumeration;

public class LogFilter extends BaseFilter {

    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public Object run() {
        final RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        logger.info("REQUEST:: > " + request.getScheme() + " " + request.getRemoteAddr() + ":" + request.getRemotePort());
        StringBuilder params = new StringBuilder();
        Enumeration<String> names = request.getParameterNames();
        if (request.getMethod().equalsIgnoreCase("GET")){
            while (names.hasMoreElements()){
                String name = names.nextElement();
                params.append(name);
                params.append("=");
                params.append(request.getParameter(name));
                params.append("&");
            }
        }
        if (params.length() > 0){
            params.delete(params.length() - 1, params.length());
        }
        logger.info("REQUEST:: > " + request.getMethod() + " " + request.getRequestURI() + params + " " + request.getProtocol());
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()){
            String name = headers.nextElement();
            String value = request.getHeader(name);
            logger.info("REQUEST HEADER:: > " + name + ":" + value);
        }
        // 获取请求体参数
        if (!context.isChunkedRequestBody()){
            ServletInputStream inp = null;
            try {
                inp = context.getRequest().getInputStream();
                String body = null;
                if (inp != null){
                    body = IOUtils.toString(inp, "UTF-8");
                    logger.info("REQUEST BODY:: > " + body);
                }
            } catch (Exception e){
                logger.error(e.getMessage());
            }
        }

        // 获取响应内容方式一
//        try {
//            Object zuulResponse = context.get("zuulResponse");
//            if (zuulResponse != null){
//                RibbonHttpResponse resp = (RibbonHttpResponse) zuulResponse;
//                String body = IOUtils.toString(resp.getBody(), "UTF-8");
//                resp.close();
//                context.setResponseBody(body);
//                logger.info("RESPONSE:: > " + body);
//            }
//        } catch (Exception e){
//            logger.error(e.getMessage());
//        }

        // 获取响应内容方式二
        InputStream stream = context.getResponseDataStream();
        try {
            if (stream != null){
                String body = IOUtils.toString(stream, "UTF-8");
                context.setResponseBody(body);
                logger.info("RESPONSE:: > " + body);
            }
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
