package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.exception.FileAlreadyExistException;
import com.avocado.ecomus.exception.FileBrokenException;
import com.avocado.ecomus.exception.FileNotFoundException;
import com.avocado.ecomus.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileServiceImp implements FileService {

    private final Path root = Paths.get("D:\\Education\\Java_Project\\ecomus_prj\\back-end\\imgs");

    @Override
    public void save(MultipartFile file) {
        try{
            Files.copy(file.getInputStream(), root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        }catch (FileAlreadyExistsException e){
            throw new FileAlreadyExistException("File already exists");
        } catch (IOException e) {
            throw new FileBrokenException("File is broken");
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new FileNotFoundException("File not found: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
