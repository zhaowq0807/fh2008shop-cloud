package com.fh.shop.api.cart.controller;

import com.fh.shop.api.BaseController;
import com.fh.shop.api.cart.biz.ICartService;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/carts")
@Api(tags = "购物车接口")
public class CartController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    @Resource(name = "cartService")
    private ICartService cartService;

    @PostMapping("/addCartItem")
    @ApiOperation("添加商品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skuId",value = "商品的Id",dataType = "java.lang.Long",required = true),
            @ApiImplicitParam(name = "count",value = "商品的数量",dataType = "java.lang.Long",required = true),
            @ApiImplicitParam(name = "x-auth",value = "头信息",dataType = "java.lang.String",required = true,paramType = "header"),
    })
    public ServerResponse addCartItem(Long skuId, Long count){
        MemberVo memberVo = buildMemberVo(request);
        Long memberId = memberVo.getId();
        return cartService.addCartItem(memberId,skuId,count);
    }

    @GetMapping("/findCart")
    @ApiOperation("查找购物车商品")
    @ApiImplicitParam(name = "x-auth",value = "头信息",dataType = "java.lang.String",required = true,paramType = "header")
    public ServerResponse findCart(){
        MemberVo memberVo = buildMemberVo(request);
        Long memberId = memberVo.getId();
        return cartService.findCart(memberId);
    }
    @GetMapping("/findCartCount")
    @ApiOperation("查找购物车商品数量")
    @ApiImplicitParam(name = "x-auth",value = "头信息",dataType = "java.lang.String",required = true,paramType = "header")
    public ServerResponse findCartCount(){
        MemberVo memberVo = buildMemberVo(request);
        Long memberId = memberVo.getId();
        return cartService.findCartCount(memberId);
    }
    @DeleteMapping("/deleteCartItem")
    @ApiOperation("删除购物车中的商品")
    @ApiImplicitParam(name = "x-auth",value = "头信息",dataType = "java.lang.String",required = true,paramType = "header")
    public ServerResponse deleteCartItem(Long skuId){
        MemberVo memberVo = buildMemberVo(request);
        Long memberId = memberVo.getId();
        return cartService.deleteCartItem(memberId,skuId);
    }
    @DeleteMapping("/deleteBatch")
    @ApiOperation("批量删除购物车中的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "值",dataType = "java.lang.String",required = true),
            @ApiImplicitParam(name = "x-auth",value = "头信息",dataType = "java.lang.String",required = true,paramType = "header")
    })
    public ServerResponse deleteBatch(String ids){
        MemberVo memberVo = buildMemberVo(request);
        Long memberId = memberVo.getId();
        return cartService.deleteBatch(memberId,ids);
    }

    @GetMapping("/sendRecipient")
    public ServerResponse sendRecipient(){
        MemberVo memberVo = buildMemberVo(request);
        Long memberId = memberVo.getId();
        return cartService.sendRecipient(memberId);
    }
}
