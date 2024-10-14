package com.avocado.ecomus.service;

import com.avocado.ecomus.entity.EmailDetail;
import com.avocado.ecomus.entity.OrderEntity;
import com.avocado.ecomus.entity.UserEntity;
import jakarta.mail.MessagingException;

public interface TemplateService {
    void tokenTemplate(EmailDetail emailDetail, UserEntity userEntity, String token) throws MessagingException;
    void orderConfirmTemplate(EmailDetail emailDetail, OrderEntity orderEntity) throws MessagingException;
    void orderCreateTemplate(EmailDetail emailDetail, UserEntity user, OrderEntity orderEntity) throws MessagingException;
    void orderDeliveryTemplate(EmailDetail emailDetail, UserEntity user, OrderEntity order) throws MessagingException;
}
