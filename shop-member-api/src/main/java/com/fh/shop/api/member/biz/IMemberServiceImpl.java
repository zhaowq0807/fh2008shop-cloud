package com.fh.shop.api.member.biz;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.common.Constants;
import com.fh.shop.common.KeyUtil;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.Md5Util;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service("memberService")
@Transactional(rollbackFor = Exception.class)
public class IMemberServiceImpl implements IMemberService {

    @Resource
    private IMemberMapper memberMapper;

    @Override
    public ServerResponse login(String memberName, String password) {
        //验证非空
        if(StringUtils.isEmpty(memberName) || StringUtils.isEmpty(password)){
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_IS_NULL);
        }
        //判断用户名是否为空
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("memberName",memberName);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        Long id1 = member.getId();
        if(member == null){
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_MEMBER_NAME_NOT_EXIST);
        }

        //判断密码是否正确
        if(!Md5Util.md5(password).equals(member.getPassword())){
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_MEMBER_PASSWORD_IS_ERROR);
        }
        Member member1 = memberMapper.selectById(id1);
        Integer status = member1.getStatus();
        if(status==0){
            String mail = member.getMail();
            Map<String,String> map = new HashMap<>();
            map.put("mail",mail);
            map.put("id",member.getId()+"");
            return ServerResponse.error(ResponseEnum.TOKEN_IS_TIME_STATUS_ERROR,map);
        }
        //=========生成签名
        //创建Vo
        MemberVo memberVo = new MemberVo();
        Long id = member.getId();
        memberVo.setId(member.getId());
        memberVo.setMemberName(member.getMemberName());
        memberVo.setNickName(member.getNikeName());
        //将用户信息转为JSON
        String memberVoJson = JSON.toJSONString(memberVo);
        //生成签名
        String sign = Md5Util.sign(memberVoJson , Constants.SECTET);
        //=========生成签名
        //将用户信息和签名通过.分割组成一个字符串传给前台 x.y
        //将用户信息进行base64编码
        String memberVoJsonBase64 = Base64.getEncoder().encodeToString(memberVoJson.getBytes());
        String signBase64 =  Base64.getEncoder().encodeToString(sign.getBytes());
        //将信息存入redis中
        RedisUtil.setEx(KeyUtil.buildMemberKey(id),"",Constants.TOKEY_EXPIRE);
        //将用户信息和签名响应给前台
        return ServerResponse.success(memberVoJsonBase64+"."+signBase64);
    }


}
