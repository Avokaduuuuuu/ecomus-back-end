package com.avocado.ecomus.mapper;

import com.avocado.ecomus.dto.PaymentMethodDto;
import com.avocado.ecomus.entity.PaymentMethodEntity;

public class PaymentMethodMapper {
    public static PaymentMethodDto mapToPaymentMethodDto(PaymentMethodEntity paymentMethod){
        return PaymentMethodDto
                .builder()
                .id(paymentMethod.getId())
                .name(paymentMethod.getName())
                .build();
    }
}
