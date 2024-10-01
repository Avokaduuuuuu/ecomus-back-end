package com.avocado.ecomus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "order_variant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sku")
    private VariantEntity variantEntity;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total")
    private double total;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private OrderEntity orderEntity;
}
