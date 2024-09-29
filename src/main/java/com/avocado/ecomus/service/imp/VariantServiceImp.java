package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.entity.VariantEntity;
import com.avocado.ecomus.exception.ColorNotFoundException;
import com.avocado.ecomus.exception.ProductNotFoundException;
import com.avocado.ecomus.exception.SizeNotFoundException;
import com.avocado.ecomus.payload.req.AddVariantRequest;
import com.avocado.ecomus.repository.ColorRepository;
import com.avocado.ecomus.repository.ProductRepository;
import com.avocado.ecomus.repository.SizeRepository;
import com.avocado.ecomus.repository.VariantRepository;
import com.avocado.ecomus.service.FileService;
import com.avocado.ecomus.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VariantServiceImp implements VariantService {
    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private FileService fileService;

    @Override
    public void addVariant(AddVariantRequest request) {
        VariantEntity variantEntity = new VariantEntity();

        variantEntity.setImage(request.image().getOriginalFilename());
        variantEntity.setQuantity(request.quantity());

        variantEntity.setProduct(
                productRepository
                        .findById(request.idProduct())
                        .orElseThrow(() -> new ProductNotFoundException("Product not found"))
        );

        variantEntity.setSize(
                sizeRepository
                        .findById(request.idSize())
                        .orElseThrow(() -> new SizeNotFoundException("Size not found"))
        );

        variantEntity.setColor(
                colorRepository
                        .findById(request.idColor())
                        .orElseThrow(() -> new ColorNotFoundException("Color not found"))
        );

        fileService.save(request.image());
        variantRepository.save(variantEntity);
    }
}
