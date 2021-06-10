package com.fh.shop.api.cart.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SkuTask implements Serializable {

    private String skuName;

    private BigDecimal price;

    private Integer stock;

    private String brandName;

    private String cateName;

}
