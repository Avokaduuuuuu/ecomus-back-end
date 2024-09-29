package com.avocado.ecomus.service;

import com.avocado.ecomus.dto.DisplayColorDto;

import java.util.List;

public interface ColorService {
    List<DisplayColorDto> fetchColors();
}
