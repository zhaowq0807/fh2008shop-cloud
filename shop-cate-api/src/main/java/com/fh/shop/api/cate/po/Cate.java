package com.fh.shop.api.cate.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Cate {
    @ApiModelProperty(value = "分类的Id",example = "0",hidden = true)
    private Long id;
    @ApiModelProperty("分类的名字")
    private String cateName;
    @ApiModelProperty(value = "父Id",example = "0")
    private Long  fatherId;
    @ApiModelProperty(value = "类型Id",example = "0")
    private Long typeId;
    @ApiModelProperty("类型名字")
    private String typeName;
}
