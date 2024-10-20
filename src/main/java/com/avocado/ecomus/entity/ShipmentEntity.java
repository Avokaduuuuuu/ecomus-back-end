package com.avocado.ecomus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "shipment")
public class ShipmentEntity {
    @Id
    @Column(name = "tracking_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trackingNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "street")
    private String street;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "shipping_fee")
    private double shippingFee;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "id_branch_address")
    private BranchAddressEntity branchAddress;

    @OneToOne(mappedBy = "shipment")
    private OrderEntity order;
}
