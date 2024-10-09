package com.avocado.ecomus.controller;

import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> fetchAllCategories() {
        BaseResp resp = new BaseResp();

        resp.setData(categoryService.fetchAllCategories());
        resp.setMsg("Fetch Categories Successfully");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
