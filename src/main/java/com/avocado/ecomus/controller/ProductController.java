package com.avocado.ecomus.controller;

import com.avocado.ecomus.exception.BrandNotFoundException;
import com.avocado.ecomus.exception.CategoryNotFoundException;
import com.avocado.ecomus.exception.ProductNotFoundException;
import com.avocado.ecomus.payload.req.AddProductRequest;
import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?> add(@RequestBody @Valid AddProductRequest request){
        BaseResp resp = new BaseResp();
        try {
            productService.addProduct(request);
            resp.setMsg("Add product success");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }catch (BrandNotFoundException | CategoryNotFoundException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        BaseResp resp = new BaseResp();
        resp.setData(productService.getAllProducts());
        resp.setMsg("All products success");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        BaseResp resp = new BaseResp();
        try {
            resp.setData(productService.getProductById(id));
        }catch (ProductNotFoundException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/change/{id}")
    public ResponseEntity<?> disable(@PathVariable int id){
        BaseResp resp = new BaseResp();
        try {
            productService.changeProductStatus(id);
            resp.setMsg("Product's availability successfully changed");
        }catch (ProductNotFoundException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
