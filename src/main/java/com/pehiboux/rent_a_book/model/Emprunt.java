package com.pehiboux.rent_a_book.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "emprunts")
public class Emprunt {
    /**
     * Identifiant unique, généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Id du livre. Ne peut pas être vide.
     */
    @NotBlank(message = "L'id du livre est obligatoire")
    @Column(nullable = false)
    private Long livreId;
    /**
     * Emprunteur. Ne peut pas être vide.
     */
    @NotBlank(message = "L'emprunteur est obligatoire")
    @Column(nullable = false)
    private String emprunteur;
    /**
     * L'email de l'emprunteur. Ne peut pas être vide.
     */
    @NotBlank(message = "L'email de l'emprunteur est obligatoire")
    @Column(nullable = false)
    private String emailEmprunteur;
    /**
     * Date de l'emprunt. Ne peut pas être vide.
     */
    @NotBlank(message = "La date de l'emprunt est obligatoire")
    @Column(nullable = false)
    private LocalDate dateEmprunt;
    /**
     * Date de retour prévue. Ne peut pas être vide.
     */
    @NotBlank(message = "La date de retour prévue est obligatoire")
    @Column(nullable = false)
    private LocalDate dateRetourPrevue;
    /**
     * Date de retour prévue. Null tant que le livre n'est pas rendu
     */
    private LocalDate dateRetourEffective;
    /**
     * Rendu. Ne peut pas être vide.
     */
    @NotBlank(message = "Le rendu est obligatoire")
    @Column(nullable = false)
    private boolean rendu;
}
