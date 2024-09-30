package com.avocado.ecomus.repository;

import com.avocado.ecomus.entity.ConfirmationTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, Integer> {
    Optional<ConfirmationTokenEntity> findByToken(String confirmationToken);

    @Query("UPDATE ConfirmationTokenEntity ct " +
            "SET ct.confirmedAt = ?2 " +
            "WHERE ct.token = ?1")
    @Transactional
    @Modifying
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
