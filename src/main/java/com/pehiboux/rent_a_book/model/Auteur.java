package com.pehiboux.rent_a_book.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "auteurs")
public class Auteur {   /**
    * Identifiant unique, généré automatiquement par la base de données.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
    * Nom de l'auteur. Ne peut pas être vide.
    */
    @NotBlank(message = "Le nom de l'auteur est obligatoire")
    @Column(nullable = false)
    private String nom;
    /**
    * Prénom de l'auteur. Ne peut pas être vide.
    */
    @NotBlank(message = "Le prenom de l'auteur est obligatoire")
    @Column(nullable = false)
    private String prenom;
    /**
    * Nationalité de l'auteur.
    */
    private String nationalite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }
}
