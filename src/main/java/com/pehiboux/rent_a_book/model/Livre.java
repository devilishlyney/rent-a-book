package com.pehiboux.rent_a_book.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "livres")
public class Livre {
    /**
     * Identifiant unique, généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Titre. Ne peut pas être vide.
     */
    @NotBlank(message = "Le titre est obligatoire")
    @Column(nullable = false)
    private String titre;
    /**
     * Isbn. Ne peut pas être vide.
     */
    @NotBlank(message = "L'isbn est obligatoire")
    @Column(nullable = false, unique = true)
    private String isbn;
    /**
     * Prix. Ne peut pas être vide.
     */
    @NotBlank(message = "Le prix est obligatoire")
    @Column(nullable = false)
    private Double prix;
    /**
     * Année de publication.
     */
    private Integer anneePublication;
    /**
     * Stock. Ne peut pas être vide.
     */
    @NotBlank(message = "Le stock est obligatoire")
    @Column(nullable = false)
    private Integer stock;
    /**
     * Id de l'auteur. Ne peut pas être vide.
     */
    @NotBlank(message = "L'id de l'auteur est obligatoire")
    @Column(nullable = false)
    private Long auteurId;
}
