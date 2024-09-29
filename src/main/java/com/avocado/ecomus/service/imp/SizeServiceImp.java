package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.dto.DisplaySizeDto;
import com.avocado.ecomus.repository.SizeRepository;
import com.avocado.ecomus.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImp implements SizeService {
    @Autowired
    private SizeRepository sizeRepository;


    @Override
    public List<DisplaySizeDto> fetchSizes() {
        return sizeRepository
                .findAll()
                .stream()
                .map(
                        sizeEntity ->
                                DisplaySizeDto
                                        .builder()
                                        .id(sizeEntity.getId())
                                        .size(sizeEntity.getName())
                                        .build()
                )
                .toList();
    }
}
