package com.fh.shop.api.member.controller;

import com.alibaba.fastjson.JSON;
import com.fh.shop.api.BaseController;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.common.Constants;
import com.fh.shop.common.KeyUtil;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@Api(tags = "会员接口")
public class MemberController extends BaseController {

    @Resource(name="memberService")
    private IMemberService memberService;


    @Autowired
    private HttpServletRequest request;


    @ApiOperation("登录")
    @PostMapping("/member/login")
    public ServerResponse login(String memberName , String password){
        return memberService.login(memberName,password);
    }

    @ApiOperation("从request里面取用户信息")
    @GetMapping("/member/findMember")
    public ServerResponse findMember(){
//        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        String memberVoJson = request.getHeader(Constants.CURR_MEMBER);
        MemberVo memberVo = JSON.parseObject(memberVoJson, MemberVo.class);
        return ServerResponse.success(memberVo);
    }

    @ApiOperation("注销登录")
    @GetMapping("/member/logout")
    public ServerResponse logout(){
//        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        MemberVo memberVo = buildMemberVo(request);
        RedisUtil.del(KeyUtil.buildMemberKey(memberVo.getId()));
        return ServerResponse.success();
    }




}
