package com.avocado.ecomus.controller;

import com.avocado.ecomus.exception.ColorNotFoundException;
import com.avocado.ecomus.exception.ProductNotFoundException;
import com.avocado.ecomus.exception.SizeNotFoundException;
import com.avocado.ecomus.payload.req.AddVariantRequest;
import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/variants")
public class VariantController {

    @Autowired
    private VariantService variantService;

    @PostMapping("/add")
    public ResponseEntity<?> addVariant(AddVariantRequest request){
        BaseResp resp = new BaseResp();

        try {
            variantService.addVariant(request);
            resp.setMsg("Add Variant Success");
        }catch (ProductNotFoundException | ColorNotFoundException | SizeNotFoundException e) {
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
