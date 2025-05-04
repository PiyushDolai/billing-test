package com.dolaicorp.billing.app.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dolaicorp.billing.app.model.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    @Query("SELECT b FROM Billing b WHERE b.billingDate >= :startDate AND b.billingDate <= :endDate ORDER BY b.billingDate DESC")
    Page<Billing> findByBillingDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    Page<Billing> findAllByOrderByBillingDateDesc(Pageable pageable);
}