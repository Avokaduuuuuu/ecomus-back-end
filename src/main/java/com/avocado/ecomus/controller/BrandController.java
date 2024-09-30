package com.avocado.ecomus.controller;

import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> fetchAll(){
        BaseResp resp = new BaseResp();
        resp.setData(brandService.getAllBrands());
        resp.setMsg("Fetch brands successful");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
