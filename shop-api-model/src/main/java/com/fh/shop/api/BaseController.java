package com.fh.shop.api;

import com.alibaba.fastjson.JSON;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.common.Constants;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    public MemberVo buildMemberVo(HttpServletRequest request){
        String memberVoJson = request.getHeader(Constants.CURR_MEMBER);
        MemberVo memberVo = JSON.parseObject(memberVoJson, MemberVo.class);
        return memberVo;
    }
}
