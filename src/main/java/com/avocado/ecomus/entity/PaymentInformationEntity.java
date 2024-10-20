package com.avocado.ecomus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "payment_information")
public class PaymentInformationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "holder_name")
    private String holderName;

    @Column(name = "bank")
    private String bank;

    @Column(name = "expired")
    private LocalDate expired;

    @Column(name = "ccv")
    private String ccv;

    @Column(name = "card_number")
    private String cardNumber;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;
}
