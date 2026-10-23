package com.pehiboux.rent_a_book.service;

import com.pehiboux.rent_a_book.model.Livre;
import com.pehiboux.rent_a_book.repository.LivreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service contenant la logique métier pour les livres.
 * Le service est le chef d'orchestre : il coordonne les appels
 * au repository et applique les règles métier.
 */
@Service
public class LivreService {
    private final LivreRepository livreRepository;
    /**
     * Injection de dépendance par constructeur.
     * Spring injecte automatiquement l'AuteurRepository.
     */
    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }
    /**
     * Retourne tous les livres.
     */
    public List<Livre> recupererTous() {
        return livreRepository.findAll();
    }
    /**
     * Retourne un livre par son ID.
     * @return Optional vide si le livre n'existe pas.
     */
    public Optional<Livre> recupererParId(Long id) {
        return livreRepository.findById(id);
    }
    /**
     * Crée un nouveau livre.
     * @param livre Le livre à créer (sans ID).
     * @return Le livre créé (avec ID généré).
     */
    public Livre creer(Livre livre) {
        // On s'assure que l'ID est null pour forcer une création (pas une mise jour)
        livre.setId(null);
        return livreRepository.save(livre);
    }
    /**
     * Met à jour un livre existant.
     * @param id L'ID de le livre à modifier.
     * @param livreModifie Les nouvelles valeurs.
     * @return Optional contenant le livre mis à jour, ou vide si l'ID n'existe
    pas.
     */
    public Optional<Livre> mettreAJour(Long id, Livre livreModifie) {
        return livreRepository.findById(id)
                .map(livreExistant -> {
                    livreExistant.setTitre(livreModifie.getTitre());
                    livreExistant.setIsbn(livreModifie.getIsbn());
                    livreExistant.setPrix(livreModifie.getPrix());
                    livreExistant.setAnneePublication(livreModifie.getAnneePublication());
                    livreExistant.setStock(livreModifie.getStock());
                    livreExistant.setAuteurId(livreModifie.getAuteurId());
                    return livreRepository.save(livreExistant);
                });
    }
    /**
     * Supprime un livre par son ID.
     * @return true si le livre existait et a été supprimé, false sinon.
     */
    public boolean supprimer(Long id) {
        if (livreRepository.existsById(id)) {
            livreRepository.deleteById(id);
            return true;
        }
        return false;
    }
    /**
     * Liste les livres en disponible.
     * @return Page contenant la liste des livres disponible
     */
    public List<Livre> recupererLivresDisponible(){
        return this.livreRepository.findByStockGreaterThan(0);
    }
    /**
     * Liste les livres dont le titre correspond.
     * @return List contenant la liste des livres dont le titre correspond
     */
    public List<Livre> recupererLivresParTitre(String titre){
        return this.livreRepository.findByTitreContainingIgnoreCase(titre);
    }
}

