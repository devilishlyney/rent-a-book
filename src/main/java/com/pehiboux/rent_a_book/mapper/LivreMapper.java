package com.pehiboux.rent_a_book.mapper;

import com.pehiboux.rent_a_book.dto.LivreRequest;
import com.pehiboux.rent_a_book.dto.LivreResponse;
import com.pehiboux.rent_a_book.model.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public final class LivreMapper {

    // Constructeur privé pour empêcher l'instanciation de la classe utilitaire.
    private LivreMapper() {
    }

    // Convertit une demande API en entité Livre.
    public static Livre versEntite(LivreRequest request) {
        Livre livre = new Livre();
        livre.setTitre(request.getTitre());
        livre.setIsbn(request.getIsbn());
        livre.setPrix(request.getPrix());
        livre.setAnneePublication(request.getAnneePublication());
        livre.setStock(request.getStock());
        livre.setAuteurId(request.getAuteurId());
        return livre;
    }

    // Convertit une entité Livre en réponse JSON.
    public static LivreResponse versResponse(Livre livre) {
        return new LivreResponse(
                livre.getId(),
                livre.getTitre(),
                livre.getIsbn(),
                livre.getPrix(),
                livre.getAnneePublication(),
                livre.getStock(),
                livre.getAuteurId()
        );
    }

    // Convertit une page d'entités Livre en page de réponses.
    public static PagedModel<LivreResponse> versPage(Page<Livre> page) {
        return new PagedModel<>(page.map(LivreMapper::versResponse));
    }
}