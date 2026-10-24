package com.pehiboux.rent_a_book.repository;

import com.pehiboux.rent_a_book.model.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Long> {
    // aucune méthode à ajouter
}
