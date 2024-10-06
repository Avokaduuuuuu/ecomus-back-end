package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.dto.OrderDto;
import com.avocado.ecomus.dto.StatusDto;
import com.avocado.ecomus.entity.OrderEntity;
import com.avocado.ecomus.entity.OrderVariant;
import com.avocado.ecomus.enums.StatusEnum;
import com.avocado.ecomus.exception.*;
import com.avocado.ecomus.mapper.OrderMapper;
import com.avocado.ecomus.payload.req.OrderRequest;
import com.avocado.ecomus.payload.req.VariantRequest;
import com.avocado.ecomus.repository.*;
import com.avocado.ecomus.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
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

    @Override
    public List<OrderDto> getOrderByUserId(int id, int currentUser) {
        if (id != currentUser) throw new RestrictException("User can not access these data");
        return orderRepository
                .findByUser_Id(id)
                .stream()
                .map(OrderMapper::mapToOrderDto)
                .toList();
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(OrderMapper::mapToOrderDto)
                .toList();
    }

    @Override
    public void acceptOrder(int orderId) {
        OrderEntity orderEntity = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderEntity.setStatus(
                statusRepository
                        .findByName(StatusEnum.PACKAGING.name())
                        .orElseThrow(() -> new StatusNotFoundException("Status not found"))
        );

        orderRepository.save(orderEntity);
    }
}
