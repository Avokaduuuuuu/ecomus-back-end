package com.avocado.ecomus.controller;

import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<?> getStatus() {
        BaseResp resp = new BaseResp();
        resp.setData(statusService.getAllStatus());
        resp.setMsg("Fetch All Status");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
