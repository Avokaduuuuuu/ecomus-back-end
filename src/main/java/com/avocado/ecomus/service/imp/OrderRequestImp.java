package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.entity.OrderEntity;
import com.avocado.ecomus.entity.OrderVariant;
import com.avocado.ecomus.enums.StatusEnum;
import com.avocado.ecomus.exception.PaymentMethodNotFoundException;
import com.avocado.ecomus.exception.StatusNotFoundException;
import com.avocado.ecomus.exception.UserNotFoundException;
import com.avocado.ecomus.exception.VariantNotFoundException;
import com.avocado.ecomus.payload.req.OrderRequest;
import com.avocado.ecomus.payload.req.VariantRequest;
import com.avocado.ecomus.repository.*;
import com.avocado.ecomus.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderRequestImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderVariantRepository orderVariantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private VariantRepository variantRepository;

    @Override
    public void addOrder(OrderRequest request) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(
                userRepository
                        .findById(request.idUser())
                        .orElseThrow(() -> new UserNotFoundException("User not found"))
        );
        orderEntity.setNote(request.note());
        orderEntity.setStatus(
                statusRepository
                        .findByName(StatusEnum.WAITING.name())
                        .orElseThrow(() -> new StatusNotFoundException("Status not found"))
        );
        orderEntity.setPaymentMethod(
                paymentMethodRepository
                        .findById(request.idPaymentMethod())
                        .orElseThrow(() -> new PaymentMethodNotFoundException("Payment method not found"))
        );
        orderEntity.setTotal(request.total());
        orderEntity.setCreateDate(LocalDate.now());
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        for (VariantRequest variantRequest : request.variantRequests()) {
            OrderVariant orderVariant = new OrderVariant();
            orderVariant.setTotal(variantRequest.total());
            orderVariant.setQuantity(variantRequest.quantity());
            orderVariant.setVariantEntity(
                  variantRepository
                          .findById(variantRequest.sku())
                          .orElseThrow(() -> new VariantNotFoundException("Variant not found"))
            );
            orderVariant.setOrderEntity(savedOrder);
            orderVariantRepository.save(orderVariant);
        }
    }
}
