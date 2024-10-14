package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.entity.EmailDetail;
import com.avocado.ecomus.entity.OrderEntity;
import com.avocado.ecomus.entity.UserEntity;
import com.avocado.ecomus.service.EmailService;
import com.avocado.ecomus.service.TemplateService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
public class TemplateServiceImp implements TemplateService {
    @Autowired
    private EmailService emailService;


    @Override
    public void tokenTemplate(EmailDetail emailDetail, UserEntity userEntity, String token) throws MessagingException {
        Context context = new Context();
        context.setVariable("firstName", userEntity.getFirstName());
        context.setVariable("lastName", userEntity.getLastName());
        context.setVariable("token", token);

        emailService.sendVerificationEmail(emailDetail, "SendTokenTemplate.html", context);
    }

    @Override
    public void orderConfirmTemplate(EmailDetail emailDetail, OrderEntity orderEntity) throws MessagingException {
        Context context = new Context();
        context.setVariable("customerName", orderEntity.getUser().getFirstName() + " " + orderEntity.getUser().getLastName());
        context.setVariable("orderId", orderEntity.getId());
        context.setVariable("orderDate", orderEntity.getCreateDate());
        context.setVariable("totalAmount", orderEntity.getTotal());
        context.setVariable("recipientName", orderEntity.getUser().getFirstName() + " " + orderEntity.getUser().getLastName());
        context.setVariable("shippingAddress", orderEntity.getUser().getAddress());

        emailService.sendVerificationEmail(emailDetail, "OrderConfirmationTemplate.html", context);
    }

    @Override
    public void orderCreateTemplate(EmailDetail emailDetail, UserEntity user, OrderEntity orderEntity) throws MessagingException {
        Context context = new Context();
        context.setVariable("customerName", user.getFirstName() + " " + user.getLastName());
        context.setVariable("orderId", orderEntity.getId());
        context.setVariable("orderDate", orderEntity.getCreateDate());
        context.setVariable("totalAmount", orderEntity.getTotal());

        emailService.sendVerificationEmail(emailDetail, "OrderCreationTemplate.html", context);
    }

    @Override
    public void orderDeliveryTemplate(EmailDetail emailDetail, UserEntity user, OrderEntity order) throws MessagingException {
        Context context = new Context();
        context.setVariable("customerName", user.getFirstName() + " " + user.getLastName());
        context.setVariable("orderId", order.getId());
        context.setVariable("estimatedDeliveryTime", "5 Days");

        emailService.sendVerificationEmail(emailDetail, "OrderDeliveryTemplate.html", context);
    }
}
