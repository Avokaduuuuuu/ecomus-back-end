package com.avocado.ecomus.service;

import com.avocado.ecomus.payload.req.OrderRequest;

public interface OrderService {
    void addOrder(OrderRequest request);
}
