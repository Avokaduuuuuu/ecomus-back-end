package com.avocado.ecomus.controller;

import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/colors")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping
    public ResponseEntity<?> fetchColors() {
        BaseResp resp = new BaseResp();
        resp.setData(colorService.fetchColors());
        resp.setMsg("Fetched colors");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
