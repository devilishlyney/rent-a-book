package com.pehiboux.rent_a_book.service;

import com.pehiboux.rent_a_book.model.Auteur;
import com.pehiboux.rent_a_book.repository.AuteurRepository;
import com.pehiboux.rent_a_book.repository.LivreRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
/**
 * Service contenant la logique métier pour les auteurs.
 * Le service est le chef d'orchestre : il coordonne les appels
 * au repository et applique les règles métier.
 */
@Service
public class AuteurService {
    private final AuteurRepository auteurRepository;
    private final LivreRepository livreRepository;
    /**
     * Injection de dépendance par constructeur.
     * Spring injecte automatiquement l'AuteurRepository.
     */
    public AuteurService(AuteurRepository auteurRepository, LivreRepository livreRepository) {
        this.auteurRepository = auteurRepository;
        this.livreRepository = livreRepository;
    }
    /**
     * Retourne tous les auteurs.
     */
    public List<Auteur> recupererTous() {
        return auteurRepository.findAll();
    }
    /**
     * Retourne un auteur par son ID.
     * @return Optional vide si l'auteur n'existe pas.
     */
    public Optional<Auteur> recupererParId(Long id) {
        return auteurRepository.findById(id);
    }
    /**
     * Crée un nouveau auteur.
     * @param auteur L'auteur à créer (sans ID).
     * @return L'auteur créé (avec ID généré).
     */
    public Auteur creer(Auteur auteur) {
        // On s'assure que l'ID est null pour forcer une création (pas une mise jour)
        auteur.setId(null);
        return auteurRepository.save(auteur);
    }
    /**
     * Met à jour un auteur existant.
     * @param id L'ID de l'auteur à modifier.
     * @param auteurModifie Les nouvelles valeurs.
     * @return Optional contenant l'auteur mis à jour, ou vide si l'ID n'existe
    pas.
     */
    public Optional<Auteur> mettreAJour(Long id, Auteur auteurModifie) {
        return auteurRepository.findById(id)
                .map(auteurExistant -> {
                    auteurExistant.setNom(auteurModifie.getNom());
                    auteurExistant.setPrenom(auteurModifie.getPrenom());
                    auteurExistant.setNationalite(auteurModifie.getNationalite());
                    return auteurRepository.save(auteurExistant);
                });
    }
    /**
     * Supprime un auteur par son ID.
     * @return true si l'auteur existait et a été supprimé, false sinon.
     */
    public boolean supprimer(Long id) {
        if (auteurRepository.existsById(id)) {
            auteurRepository.deleteById(id);
            return true;
        }
        return false;
    }
    /**
     * Recherche des livres avec id de l'auteur (recherche partielle, insensible à la casse).
     */
    public List<Auteur> recupererLivres(Long id) {
        return livreRepository.findByAuteurId(id);
    }
}

