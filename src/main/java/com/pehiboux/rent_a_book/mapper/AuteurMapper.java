package com.pehiboux.rent_a_book.mapper;

import com.pehiboux.rent_a_book.dto.AuteurRequest;
import com.pehiboux.rent_a_book.dto.AuteurResponse;
import com.pehiboux.rent_a_book.model.Auteur;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public final class AuteurMapper {

    // Constructeur privé pour empêcher l'instanciation de la classe utilitaire.
    private AuteurMapper() {
    }

    // Convertit une demande API en entité Auteur.
    public static Auteur versEntite(AuteurRequest request) {
        Auteur auteur = new Auteur();
        auteur.setNom(request.getNom());
        auteur.setPrenom(request.getPrenom());
        auteur.setNationalite(request.getNationalite());
        return auteur;
    }

    // Convertit une entité Auteur en réponse JSON.
    public static AuteurResponse versResponse(Auteur auteur) {
        return new AuteurResponse(
                auteur.getId(),
                auteur.getNom(),
                auteur.getPrenom(),
                auteur.getNationalite()
        );
    }

    // Convertit une page d'entités Auteur en page de réponses.
    public static PagedModel<AuteurResponse> versPage(Page<Auteur> page) {
        return new PagedModel<>(page.map(AuteurMapper::versResponse));
    }
}