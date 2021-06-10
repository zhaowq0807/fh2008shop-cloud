package com.fh.shop.api.cart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SkuVo implements Serializable {
    @ApiModelProperty(value = "sku的Id",example = "0",hidden = true)
    private Long id;
    @ApiModelProperty(value = "sku的价格")
    private String price;
    @ApiModelProperty("sku的名字")
    private String skuName;
    @ApiModelProperty(value = "图片")
    private String image;
}
