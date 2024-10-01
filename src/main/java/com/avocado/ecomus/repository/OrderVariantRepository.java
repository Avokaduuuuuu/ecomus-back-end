package com.avocado.ecomus.repository;

import com.avocado.ecomus.entity.OrderVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderVariantRepository extends JpaRepository<OrderVariant, Integer> {
}
