package com.jane.spring_boot_library.dao;

import com.jane.spring_boot_library.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);
    Review findByUserEmailAndBookId(String userEmail, Long bookId);

    @Transactional
    void deleteAllByBookId(Long bookId);
}
