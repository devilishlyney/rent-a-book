package com.pehiboux.rent_a_book.repository;
import com.pehiboux.rent_a_book.model.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Long> {

List<Auteur> findByNomContainingIgnoreCase(String nom);

List<Auteur> findByPrenomContainingIgnoreCase(String prenom);

List<Auteur> findByNationaliteContainingIgnoreCase(String nationalite);
}