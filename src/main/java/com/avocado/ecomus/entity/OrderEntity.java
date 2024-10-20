package com.avocado.ecomus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "total")
    private double total;

    @Column(name = "note")
    private String note;

    @Column(name = "create_date")
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "id_payment")
    private PaymentMethodEntity paymentMethod;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @OneToMany(mappedBy = "orderEntity")
    private List<OrderVariant> orderVariants;

    @OneToOne
    @JoinColumn(name = "tracking_number")
    private ShipmentEntity shipment;
}
