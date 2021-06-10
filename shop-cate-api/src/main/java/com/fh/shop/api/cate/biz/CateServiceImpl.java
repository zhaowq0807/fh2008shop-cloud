package com.fh.shop.api.cate.biz;

import com.alibaba.fastjson.JSON;
import com.fh.shop.api.cate.mapper.ICateMapper;
import com.fh.shop.api.cate.po.Cate;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("cateService")
public class CateServiceImpl implements ICateService {

    @Resource
    private ICateMapper cateMapper;


    @Override
    public ServerResponse findList() {
        //RedisUtil工具类的方法看看缓存中的cateList是否拥有
        String cateListInfo = RedisUtil.get("cateList");
        //判断缓存中的cateListInfo是否为空，如果不为空则直接返回缓存中的数据
        if(StringUtils.isNotEmpty(cateListInfo)){
            //将JSON字符串转换成JavaList对象
            List<Cate> cateList = JSON.parseArray(cateListInfo, Cate.class);
            return ServerResponse.success(cateList);
        }
        List<Cate> cateList = cateMapper.selectList(null);
        //把查出来的数据转换成json字符串
        String toJSONString = JSON.toJSONString(cateList);
        //通过redis封装类把数据传到缓存中
        RedisUtil.set("cateList",toJSONString);
        return ServerResponse.success(cateList);
    }
}
