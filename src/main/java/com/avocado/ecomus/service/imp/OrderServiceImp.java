package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.dto.OrderDto;
import com.avocado.ecomus.entity.*;
import com.avocado.ecomus.enums.StatusEnum;
import com.avocado.ecomus.exception.*;
import com.avocado.ecomus.mapper.OrderMapper;
import com.avocado.ecomus.payload.req.CancelOrderRequest;
import com.avocado.ecomus.payload.req.OrderRequest;
import com.avocado.ecomus.payload.req.VariantRequest;
import com.avocado.ecomus.repository.*;
import com.avocado.ecomus.service.NotificationService;
import com.avocado.ecomus.service.OrderService;
import com.avocado.ecomus.tools.EmailBodyCreation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderVariantRepository orderVariantRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final VariantRepository variantRepository;
    private final ShipmentRepository shipmentRepository;
    private final NotificationService notificationService;

    @Override
    public void addOrder(OrderRequest request) throws MessagingException {
        OrderEntity orderEntity = new OrderEntity();
        UserEntity user = userRepository
                .findById(request.idUser())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        orderEntity.setUser(user);
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
        orderEntity.setShipment(
                shipmentRepository.save(
                        ShipmentEntity
                                .builder()
                                .firstName(request.firstName())
                                .lastName(request.lastName())
                                .email(request.email())
                                .phone(request.phone())
                                .city(request.city())
                                .district(request.district())
                                .ward(request.ward())
                                .street(request.street())
                                .zipcode(request.zipcode())
                                .shippingFee(request.shippingFee())
                                .status(
                                        statusRepository
                                                .findByName(StatusEnum.NOT_PICKUP.name())
                                                .orElseThrow(() -> new StatusNotFoundException("Status not found"))
                                )
                                .build()
                )
        );
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        for (VariantRequest variantRequest : request.variantRequests()) {
            VariantEntity variantEntity = variantRepository
                    .findById(variantRequest.sku())
                    .orElseThrow(() -> new VariantNotFoundException("Variant not found"));
            if (variantEntity.getQuantity() < variantRequest.quantity()) throw new OutOfStockException("Out of stock");
            variantEntity.setQuantity(variantEntity.getQuantity() - variantRequest.quantity());

            OrderVariant orderVariant = new OrderVariant();
            orderVariant.setTotal(variantRequest.total());
            orderVariant.setQuantity(variantRequest.quantity());
            orderVariant.setVariantEntity(variantEntity);
            orderVariant.setOrderEntity(savedOrder);
            orderVariantRepository.save(orderVariant);
        }



        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setTo(user.getEmail());
        emailDetail.setSubject("Order Creation");
        emailDetail.setBody(EmailBodyCreation.create(
                orderEntity.getUser().getFirstName() + " " + orderEntity.getUser().getLastName(),
                String.valueOf(savedOrder.getId()),
                orderEntity.getCreateDate().toString(),
                String.valueOf(orderEntity.getTotal())
        ));


        notificationService.sendOrderCreationTemplate(emailDetail);

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
    public void acceptOrder(int orderId) throws MessagingException {
        OrderEntity orderEntity = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderEntity.setStatus(
                statusRepository
                        .findByName(StatusEnum.PACKAGING.name())
                        .orElseThrow(() -> new StatusNotFoundException("Status not found"))
        );

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setTo(orderEntity.getUser().getEmail());
        emailDetail.setSubject("Order Creation");
        emailDetail.setBody(EmailBodyCreation.create(
                orderEntity.getUser().getFirstName() + " " + orderEntity.getUser().getLastName(),
                String.valueOf(orderId),
                orderEntity.getCreateDate().toString(),
                String.valueOf(orderEntity.getTotal()),
                orderEntity.getUser().getFirstName() + " " + orderEntity.getUser().getLastName(),
                orderEntity.getUser().getAddress().toString()
        ));

        notificationService.sendOrderConfirmationTemplate(emailDetail);

        orderRepository.save(orderEntity);
    }

    @Override
    public void sendForDelivery(int orderId) throws MessagingException {
        OrderEntity orderEntity = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderEntity.setStatus(
                statusRepository
                        .findByName(StatusEnum.SHIPPING.name())
                        .orElseThrow(() -> new StatusNotFoundException("Status not found"))
        );

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setTo(orderEntity.getUser().getEmail());
        emailDetail.setSubject("Order Delivery");
        emailDetail.setBody(EmailBodyCreation.create(
                orderEntity.getUser().getFirstName() + " " + orderEntity.getUser().getLastName(),
                String.valueOf(orderId)
        ));

        notificationService.sendDeliveryTemplate(emailDetail);

        orderRepository.save(orderEntity);
    }

    @Override
    public OrderDto getOrderById(int orderId) {
        return orderRepository
                .findById(orderId)
                .stream()
                .findFirst()
                .map(OrderMapper::mapToOrderDto)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public void cancelOrder(CancelOrderRequest request, int currentUser) {
        OrderEntity orderEntity = orderRepository
                .findById(request.id())
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (orderEntity.getUser().getId() != currentUser) throw new RestrictException("User can not access these data");

        if (
                !orderEntity.getStatus().getName().equals(StatusEnum.WAITING.name())
                && !orderEntity.getStatus().getName().equals(StatusEnum.PACKAGING.name())
                && !orderEntity.getStatus().getName().equals(StatusEnum.PENDING.name())
                && !orderEntity.getStatus().getName().equals(StatusEnum.PAID.name())
        ) {
            throw new OrderUnableCancelException("Order can not be cancelled");
        }
        orderEntity.setStatus(
                statusRepository
                        .findByName(StatusEnum.CANCELLED.name())
                        .orElseThrow(() -> new StatusNotFoundException("Status not found"))
        );

        orderRepository.save(orderEntity);
    }
}
