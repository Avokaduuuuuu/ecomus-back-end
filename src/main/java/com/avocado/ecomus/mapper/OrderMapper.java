package com.avocado.ecomus.mapper;

import com.avocado.ecomus.dto.OrderDto;
import com.avocado.ecomus.dto.StatusDto;
import com.avocado.ecomus.entity.OrderEntity;

public class OrderMapper {
    public static OrderDto mapToOrderDto(OrderEntity order) {
        return OrderDto
                .builder()
                .id(order.getId())
                .orderDate(order.getCreateDate())
                .status(
                        StatusDto
                                .builder()
                                .id(order.getStatus().getId())
                                .status(order.getStatus().getName())
                                .build()
                )
                .total(order.getTotal())
                .totalItems(order.getOrderVariants().size())
                .paymentMethod(PaymentMethodMapper.mapToPaymentMethodDto(order.getPaymentMethod()))
                .build();
    }
}
