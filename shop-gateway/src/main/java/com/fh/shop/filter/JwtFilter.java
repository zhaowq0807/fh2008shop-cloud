package com.fh.shop.filter;

import com.alibaba.fastjson.JSON;
import com.fh.shop.common.Constants;
import com.fh.shop.common.KeyUtil;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.Md5Util;
import com.fh.shop.util.RedisUtil;
import com.fh.shop.vo.MemberVo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.List;

@Slf4j
public class JwtFilter  extends ZuulFilter {


    @Value("${fh.shop.checkUrl}")
    private List<String> checkUrls;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {
        log.info("========={}",checkUrls);
        //获取当前访问的url
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //http://localhost:9000/api/cate
        StringBuffer requestURL = request.getRequestURL();
        boolean isCheck = false;
        for (String checkUrl : checkUrls) {
            if(requestURL.indexOf(checkUrl) > 0){
                isCheck = true;
                break;
            }
        }
        if(!isCheck){
            //相当于放行
            return null;
        }
        //验证
        //判断是否有头信息
        String header = request.getHeader("x-auth");
        if(StringUtils.isEmpty(header)){
            //不仅拦截了,还能给前台提示
            return buildResponse(ResponseEnum.TOKEN_IS_MISS);
//            throw new ShopException(ResponseEnum.TOKEN_IS_MISS);
        }
        //判断头信息是否完整
        String[] headerArr = header.split("\\.");
        if(headerArr.length != 2){
//            throw new ShopException(ResponseEnum.TOKEN_IS_NOT_FULL);
            return buildResponse(ResponseEnum.TOKEN_IS_NOT_FULL);
        }
        //验签
        String memberVoJsonBase64 = headerArr[0];
        String signBase64 = headerArr[1];
        //进行base64编码,怎么把字节数组变成字符串
        String memberVoJson = new String(Base64.getDecoder().decode(memberVoJsonBase64), "utf-8");
        String sign = new String(Base64.getDecoder().decode(signBase64), "utf-8");
        String newSign = Md5Util.sign(memberVoJson, Constants.SECTET);
        if(!newSign.equals(sign)){
            return buildResponse(ResponseEnum.TOKEN_IS_FAIL);
//            throw new ShopException(ResponseEnum.TOKEN_IS_FAIL);
        }
        //将json对象转换成java对象
        MemberVo memberVo = JSON.parseObject(memberVoJson,MemberVo.class);
        Long id = memberVo.getId();
        //判断是否过期
        if(!RedisUtil.exist(KeyUtil.buildMemberKey(id))){
            return buildResponse(ResponseEnum.TOKEN_IS_TIME_OUT);
//            throw new ShopException(ResponseEnum.TOKEN_IS_TIME_OUT);
        }
        //续命
        RedisUtil.expire(KeyUtil.buildMemberKey(id),Constants.TOKEY_EXPIRE);
        //将memberVo存入request中
//        request.setAttribute(Constants.CURR_MEMBER,memberVo);
        //将要传给后台微服务的信息放到请求头中
        currentContext.addZuulRequestHeader(Constants.CURR_MEMBER,memberVoJson);
         return null;
    }

    private Object buildResponse(ResponseEnum responseEnum) {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType("application/json:charset=utf-8");//解决中文乱码的问题
        currentContext.setSendZuulResponse(false);//拦截了,不会往下走
        ServerResponse serverResponse = ServerResponse.error(responseEnum);
        String res = JSON.toJSONString(serverResponse);
        currentContext.setResponseBody(res);
        return null;
    }
}
