package com.fh.shop.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class CrossFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
    HttpServletResponse response = currentContext.getResponse();
    //处理跨域
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");

    //处理自定义请求头
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-auth,content-type,x-token");
    //处理特殊的请求方式 delete,get,put,post
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"DELETE,PUT,POST,GET");
        return null;
}
}
