package com.fh.shop.api.cate.controller;

import com.fh.shop.api.cate.biz.ICateService;
import com.fh.shop.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

@RestController
@RequestMapping("/api")
@Api(tags = "分类的接口")
@Slf4j
public class CateController implements Serializable {

    @Resource(name = "cateService")
    private ICateService cateService;

    @Value("${server.port}")
    private String port;

    @GetMapping("/cates")
    @ApiOperation("分类的查询")
    public ServerResponse findList(){
        log.info("端口信息:{}" , port);
        return cateService.findList();
    }

}
