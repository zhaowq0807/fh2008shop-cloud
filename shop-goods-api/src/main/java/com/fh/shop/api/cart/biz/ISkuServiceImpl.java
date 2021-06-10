package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSON;
import com.fh.shop.api.cart.mapper.ISkuMapper;
import com.fh.shop.api.goods.po.Sku;
import com.fh.shop.api.cart.vo.SkuVo;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service("skuService")
public class ISkuServiceImpl implements ISkuService {

    @Resource
    private ISkuMapper skuMapper;

    @Override
    public ServerResponse list() {
        String skuListInfo = RedisUtil.get("skuList");
        if(StringUtils.isNotEmpty(skuListInfo)){
            List<SkuVo> skuVoList = JSON.parseArray(skuListInfo, SkuVo.class);
            return ServerResponse.success(skuVoList);
        }
        List<Sku> skuList= skuMapper.findSkuList();
        List<SkuVo> skuVoList = skuList.stream().map(x -> {
            SkuVo skuVo = new SkuVo();
            skuVo.setId(x.getId());
            skuVo.setImage(x.getImage());
            skuVo.setPrice(x.getPrice().toString());
            skuVo.setSkuName(x.getSkuName());
            return skuVo;
        }).collect(Collectors.toList());
        String toJSONString = JSON.toJSONString(skuVoList);
        RedisUtil.set("skuList",toJSONString);
        return ServerResponse.success(skuVoList);
    }

    @Override
    public ServerResponse findSku(Long id) {
        Sku sku = skuMapper.selectById(id);
        return ServerResponse.success(sku);
    }
}
