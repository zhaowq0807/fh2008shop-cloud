package com.fh.shop.api.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.member.po.Member;
import org.apache.ibatis.annotations.Param;

public interface IMemberMapper extends BaseMapper<Member> {

    void updateScore(@Param("id") Long id, @Param("totalPrice") double totalPrice);
}
