package com.avocado.ecomus.controller;

import com.avocado.ecomus.exception.UserNotFoundException;
import com.avocado.ecomus.payload.req.AddPaymentInformationRequest;
import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.PaymentInformationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment_information")
public class PaymentInformationController {

    private PaymentInformationService paymentInformationService;



    public PaymentInformationController(PaymentInformationService paymentInformationService) {
        this.paymentInformationService = paymentInformationService;
    }


    @PostMapping("/add/{id}")
    @Transactional
    public ResponseEntity<?> addPaymentInformation(AddPaymentInformationRequest request, @PathVariable int id) {
        BaseResp resp = new BaseResp();
        try {
            paymentInformationService.addPaymentInformation(request, id);
            resp.setMsg("Add payment information successful");
        }catch (UserNotFoundException e) {
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
