package com.avocado.ecomus.controller;

import com.avocado.ecomus.exception.CategoryAlreadyExistsException;
import com.avocado.ecomus.exception.InvalidInputException;
import com.avocado.ecomus.payload.req.AddCategoryRequest;
import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid AddCategoryRequest request){
        BaseResp resp = new BaseResp();

        try {
            categoryService.addCategory(request);
            resp.setMsg("Add Category Successfully");
        }catch (CategoryAlreadyExistsException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
