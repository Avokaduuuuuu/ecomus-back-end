package com.avocado.ecomus.service;

import com.avocado.ecomus.dto.OrderDto;
import com.avocado.ecomus.payload.req.OrderRequest;


import java.util.List;

public interface OrderService {
    void addOrder(OrderRequest request);
    List<OrderDto> getOrderByUserId(int id, int currentUser);
    List<OrderDto> getAllOrders();
    void acceptOrder(int orderId);
}
