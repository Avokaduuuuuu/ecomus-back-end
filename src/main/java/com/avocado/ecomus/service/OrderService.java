package com.avocado.ecomus.service;

import com.avocado.ecomus.dto.OrderDto;
import com.avocado.ecomus.payload.req.OrderRequest;
import jakarta.mail.MessagingException;


import java.util.List;

public interface OrderService {
    void addOrder(OrderRequest request) throws MessagingException;
    List<OrderDto> getOrderByUserId(int id, int currentUser);
    List<OrderDto> getAllOrders();
    void acceptOrder(int orderId) throws MessagingException;
    void sendForDelivery(int orderId) throws MessagingException;
    OrderDto getOrderById(int orderId);
}
