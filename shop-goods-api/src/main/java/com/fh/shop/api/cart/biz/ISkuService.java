package com.fh.shop.api.cart.biz;

import com.fh.shop.common.ServerResponse;


public interface ISkuService {
    ServerResponse list();

    ServerResponse findSku(Long id);
}
