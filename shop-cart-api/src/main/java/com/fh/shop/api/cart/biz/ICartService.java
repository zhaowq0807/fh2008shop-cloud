package com.fh.shop.api.cart.biz;

import com.fh.shop.common.ServerResponse;

public interface ICartService {

    ServerResponse addCartItem(Long memberId, Long skuId, Long count);

    ServerResponse findCart(Long memberId);

    ServerResponse findCartCount(Long memberId);

    ServerResponse deleteCartItem(Long memberId, Long skuId);

    ServerResponse deleteBatch(Long memberId, String ids);

    ServerResponse sendRecipient(Long memberId);
}
