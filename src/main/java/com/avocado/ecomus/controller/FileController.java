package com.avocado.ecomus.controller;

import com.avocado.ecomus.exception.FileAlreadyExistException;
import com.avocado.ecomus.exception.FileBrokenException;
import com.avocado.ecomus.exception.FileNotFoundException;
import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        BaseResp resp = new BaseResp();
        try {
            fileService.save(file);
            resp.setMsg("File saved successfully");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (FileAlreadyExistException | FileBrokenException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> download(@PathVariable String filename) {
        try {
            Resource resource = fileService.load(filename);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            BaseResp resp = new BaseResp();
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
