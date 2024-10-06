package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.dto.StatusDto;
import com.avocado.ecomus.repository.StatusRepository;
import com.avocado.ecomus.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImp implements StatusService {
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<StatusDto> getAllStatus() {
        return statusRepository
                .findAll()
                .stream()
                .map(status -> StatusDto
                        .builder()
                        .id(status.getId())
                        .status(status.getName())
                        .build()
                )
                .toList();
    }
}
