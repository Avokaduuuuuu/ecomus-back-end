package com.avocado.ecomus.repository;

import com.avocado.ecomus.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
    Optional<StatusEntity> findByName(String status);
}
