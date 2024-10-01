package com.avocado.ecomus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private AddressEntity address;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "user")
    private List<ConfirmationTokenEntity> confirmationTokens;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;
}
