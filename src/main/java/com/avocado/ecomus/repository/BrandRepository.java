package com.avocado.ecomus.repository;

import com.avocado.ecomus.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
}
