package com.fh.shop.api.cart.controller;

import com.fh.shop.api.cart.biz.ISkuService;
import com.fh.shop.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

@RestController
@RequestMapping("/api")
@Api(tags = "sku的接口")
public class SkuController implements Serializable {

    @Resource(name = "skuService")
    private ISkuService skuService;

    @GetMapping("/skus")
    @ApiOperation("sku的查询")
    public ServerResponse list(){
        return skuService.list();
    }

    @GetMapping("/skus/findSku")
    public ServerResponse findSku(@RequestParam("id") Long id){
        return skuService.findSku(id);
    }
}
