package com.avocado.ecomus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private int id;
    private LocalDate orderDate;
    private StatusDto status;
    private double total;
    private int totalItems;
}
