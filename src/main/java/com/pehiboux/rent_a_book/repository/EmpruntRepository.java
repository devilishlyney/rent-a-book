package com.pehiboux.rent_a_book.repository;
import com.pehiboux.rent_a_book.model.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

boolean existsByIdAndRenduTrue(Long id);

Page<Emprunt> findByRenduFalse(Pageable pageable);

List<Emprunt> findByRenduFalseAndDateRetourPrevueBefore(LocalDate date);
}