package com.pehiboux.rent_a_book.model;

import jakarta.persistence.*;

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
    @Column(nullable = false)
    private String titre;

    /**
     * Isbn. Ne peut pas être vide et unique (RM2).
     */
    @Column(nullable = false, unique = true)
    private String isbn;

    /**
     * Prix. Ne peut pas être vide.
     */
    @Column(nullable = false)
    private Double prix;

    /**
     * Année de publication.
     */
    private Integer anneePublication;

    /**
     * Stock. Ne peut pas être vide.
     */
    @Column(nullable = false)
    private Integer stock;

    /**
     * Id de l'auteur. Ne peut pas être vide.
     */
    @Column(nullable = false)
    private Long auteurId;

    public Livre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(Long auteurId) {
        this.auteurId = auteurId;
    }
}
