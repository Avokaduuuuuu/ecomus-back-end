package com.avocado.ecomus.service;

import com.avocado.ecomus.dto.DisplaySizeDto;

import java.util.List;

public interface SizeService {
    List<DisplaySizeDto> fetchSizes();
}
