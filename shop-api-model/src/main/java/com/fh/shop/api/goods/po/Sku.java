package com.fh.shop.api.goods.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class Sku {
    @ApiModelProperty(value = "sku的Id",example = "0",hidden = true)
    private Long id;
    @ApiModelProperty("sku的名字")
    private String skuName;
    @ApiModelProperty(value = "spu的Id",example = "0")
    private Long spuId;
    @ApiModelProperty(value = "sku的价格",example = "0")
    private BigDecimal price;
    @ApiModelProperty(value = "sku的库存",example = "0")
    private Integer stock;
    @ApiModelProperty(value = "sku的价格")
    private String specInfo;
    @ApiModelProperty(value = "sku的价格")
    private String image;
    @ApiModelProperty(value = "图片的Id",example = "0")
    private Long colorId;
    @ApiModelProperty(value = "推荐")
    private Integer isRecommend;//推荐
    @ApiModelProperty(value = "热销")
    private Integer sellWell;//热销
    @ApiModelProperty(value = "上下架")
    private Integer onSale;//上下架
    @ApiModelProperty(value = "销量")
    private Long sale;//销量
}
