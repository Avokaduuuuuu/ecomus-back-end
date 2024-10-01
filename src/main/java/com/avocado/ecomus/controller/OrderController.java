package com.avocado.ecomus.controller;

import com.avocado.ecomus.exception.PaymentMethodNotFoundException;
import com.avocado.ecomus.exception.StatusNotFoundException;
import com.avocado.ecomus.exception.UserNotFoundException;
import com.avocado.ecomus.exception.VariantNotFoundException;
import com.avocado.ecomus.payload.req.OrderRequest;
import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request){
        BaseResp resp = new BaseResp();
        try {
            orderService.addOrder(request);
            resp.setMsg("Add order successfully");
        }catch (UserNotFoundException | StatusNotFoundException | PaymentMethodNotFoundException | VariantNotFoundException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
