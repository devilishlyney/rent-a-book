package com.pehiboux.rent_a_book.repository;
import com.pehiboux.rent_a_book.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {

List<Livre> findByTitreContainingIgnoreCase(String titre);

List<Livre> findByStockGreaterThan(Integer stock);
}