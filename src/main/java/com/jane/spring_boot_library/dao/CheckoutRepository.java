package com.jane.spring_boot_library.dao;

import com.jane.spring_boot_library.entity.Checkout;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);
    List<Checkout> findByUserEmail(String userEmail);

    @Transactional
    void deleteAllByBookId(Long bookId);
}
