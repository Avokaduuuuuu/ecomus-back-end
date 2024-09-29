package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.dto.DisplayColorDto;
import com.avocado.ecomus.repository.ColorRepository;
import com.avocado.ecomus.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImp implements ColorService {
    @Autowired
    private ColorRepository colorRepository;


    @Override
    public List<DisplayColorDto> fetchColors() {
        return colorRepository
                .findAll()
                .stream()
                .map(
                        colorEntity ->
                                DisplayColorDto
                                        .builder()
                                        .id(colorEntity.getId())
                                        .color(colorEntity.getName())
                                        .build()
                )
                .toList();
    }
}
