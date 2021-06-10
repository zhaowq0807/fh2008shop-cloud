package com.fh.shop.api.member.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class Member implements Serializable {
    @ApiModelProperty(value = "会员的Id",example = "0",hidden = true)
    private Long id;
    @ApiModelProperty("会员的名字")
    private String memberName;
    @ApiModelProperty("会员的密码")
    private String password;
    @ApiModelProperty("会员的昵称")
    private String nikeName;
    @ApiModelProperty("会员的手机号")
    private String phone;
    @ApiModelProperty("会员的邮箱")
    private String mail;
    @ApiModelProperty("会员激活的状态")
    private Integer status;
    @ApiModelProperty(value = "会员的积分")
    private Long score;
}
