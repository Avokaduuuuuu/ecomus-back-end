package com.avocado.ecomus.service;

import com.avocado.ecomus.entity.EmailDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail-sender", url = "http://localhost:8081")
public interface NotificationService {
    @PostMapping("/verification")
    ResponseEntity<?> sendVerificationTemplate(@RequestBody EmailDetail emailDetail);

    @PostMapping("/order-confirmation")
    ResponseEntity<?> sendOrderConfirmationTemplate(@RequestBody EmailDetail emailDetail);

    @PostMapping("/order-creation")
    ResponseEntity<?> sendOrderCreationTemplate(@RequestBody EmailDetail emailDetail);

    @PostMapping("/delivery")
    public ResponseEntity<?> sendDeliveryTemplate(@RequestBody EmailDetail emailDetail);
}
