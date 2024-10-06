package com.avocado.ecomus.controller;

import com.avocado.ecomus.exception.*;
import com.avocado.ecomus.payload.req.OrderRequest;
import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserOrders(@PathVariable int id, @AuthenticationPrincipal Integer userId){
        BaseResp resp = new BaseResp();
        try {
            resp.setData(orderService.getOrderByUserId(id, userId));
            resp.setMsg("Fetch Order By User Id: " + id);
        }catch (RestrictException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(){
        BaseResp resp = new BaseResp();
        resp.setData(orderService.getAllOrders());
        resp.setMsg("Fetch All Orders");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptOrder(@PathVariable int id){
        BaseResp resp = new BaseResp();
        try {
            orderService.acceptOrder(id);
            resp.setMsg("Accept Order successfully");
        }catch (OrderNotFoundException | StatusNotFoundException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
