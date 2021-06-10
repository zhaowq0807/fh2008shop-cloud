package com.fh.shop.api.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MemberVo implements Serializable {
    @ApiModelProperty(value = "会员的Id",example = "0",hidden = true)
    private Long id;
    @ApiModelProperty("会员的名字")
    private String memberName;
    @ApiModelProperty("会员的昵称")
    private String nickName;
}
