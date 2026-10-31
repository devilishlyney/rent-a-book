package com.pehiboux.rent_a_book.mapper;

import com.pehiboux.rent_a_book.dto.EmpruntRequest;
import com.pehiboux.rent_a_book.dto.EmpruntResponse;
import com.pehiboux.rent_a_book.model.Emprunt;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

import java.time.LocalDate;

public final class EmpruntMapper {

    // Constructeur privé pour empêcher l'instanciation de la classe utilitaire.
    private EmpruntMapper() {
    }

    // Convertit une demande API en entité Emprunt.
    public static Emprunt versEntite(EmpruntRequest request) {
        Emprunt emprunt = new Emprunt();
        emprunt.setLivreId(request.getLivreId());
        emprunt.setEmprunteur(request.getEmprunteur());
        emprunt.setEmailEmprunteur(request.getEmailEmprunteur());
        return emprunt;
    }

    // Convertit une entité Emprunt en réponse JSON en calculant le statut "en retard".
    public static EmpruntResponse versResponse(Emprunt emprunt) {
        Boolean enRetard = Boolean.FALSE.equals(emprunt.getRendu())
                && emprunt.getDateRetourPrevue() != null
                && emprunt.getDateRetourPrevue().isBefore(LocalDate.now());

        return new EmpruntResponse(
                emprunt.getId(),
                emprunt.getLivreId(),
                emprunt.getEmprunteur(),
                emprunt.getDateEmprunt(),
                emprunt.getDateRetourPrevue(),
                emprunt.getDateRetourEffective(),
                emprunt.getRendu(),
                enRetard
        );
    }

    // Convertit une page d'entités Emprunt en page de réponses.
    public static PagedModel<EmpruntResponse> versPage(Page<Emprunt> page) {
        return new PagedModel<>(page.map(EmpruntMapper::versResponse));
    }
}