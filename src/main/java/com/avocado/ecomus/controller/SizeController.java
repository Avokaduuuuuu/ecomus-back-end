package com.avocado.ecomus.controller;

import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping
    public ResponseEntity<?> fetchSizes(){
        BaseResp resp = new BaseResp();
        resp.setData(sizeService.fetchSizes());
        resp.setMsg("Fetched sizes");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
