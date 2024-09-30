package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.dto.BrandDto;
import com.avocado.ecomus.repository.BrandRepository;
import com.avocado.ecomus.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImp implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<BrandDto> getAllBrands() {
        return brandRepository
                .findAll()
                .stream()
                .map(brand -> BrandDto
                        .builder()
                        .id(brand.getId())
                        .name(brand.getName())
                        .build())
                .toList();
    }
}
