package com.avocado.ecomus.repository;

import com.avocado.ecomus.entity.PaymentInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInformationRepository extends JpaRepository<PaymentInformationEntity, Integer> {
}
