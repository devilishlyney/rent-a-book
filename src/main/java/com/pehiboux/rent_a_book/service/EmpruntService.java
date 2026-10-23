package com.pehiboux.rent_a_book.service;

import com.pehiboux.rent_a_book.model.Emprunt;
import com.pehiboux.rent_a_book.repository.EmpruntRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 * Service contenant la logique métier pour les emprunts.
 * Le service est le chef d'orchestre : il coordonne les appels
 * au repository et applique les règles métier.
 */
@Service
public class EmpruntService {
    private final EmpruntRepository empruntRepository;
    /**
     * Injection de dépendance par constructeur.
     * Spring injecte automatiquement l'AuteurRepository.
     */
    public EmpruntService(EmpruntRepository empruntRepository) {
        this.empruntRepository = empruntRepository;
    }
    /**
     * Retourne tous les emprunts.
     */
    public List<Emprunt> recupererTous() {
        return empruntRepository.findAll();
    }
    /**
     * Retourne un emprunt par son ID.
     * @return Optional vide si l'emprunt n'existe pas.
     */
    public Optional<Emprunt> recupererParId(Long id) {
        return empruntRepository.findById(id);
    }
    /**
     * Crée un nouveau emprunt.
     * @param emprunt L'emprunt à créer (sans ID).
     * @return L'emprunt créé (avec ID généré).
     */
    public Emprunt creer(Emprunt emprunt) {
        // On s'assure que l'ID est null pour forcer une création (pas une mise jour)
        emprunt.setId(null);
        return empruntRepository.save(emprunt);
    }
    /**
     * Met à jour un emprunt existant.
     * @param id L'ID de l'emprunt à modifier.
     * @param empruntModifie Les nouvelles valeurs.
     * @return Optional contenant l'emprunt mis à jour, ou vide si l'ID n'existe
    pas.
     */
    public Optional<Emprunt> mettreAJour(Long id, Emprunt empruntModifie) {
        return empruntRepository.findById(id)
                .map(empruntExistant -> {
                    empruntExistant.setLivreId(empruntModifie.getLivreId());
                    empruntExistant.setEmprunteur(empruntModifie.getEmprunteur());
                    empruntExistant.setEmailEmprunteur(empruntModifie.getEmailEmprunteur());
                    empruntExistant.setDateEmprunt(empruntModifie.getDateEmprunt());
                    empruntExistant.setDateRetourPrevue(empruntModifie.getDateRetourPrevue());
                    empruntExistant.setDateRetourEffective(empruntModifie.getDateRetourEffective());
                    empruntExistant.setRendu(empruntModifie.isRendu());
                    return empruntRepository.save(empruntExistant);
                });
    }
    /**
     * Supprime un emprunt par son ID.
     * @return true si l'emprunt existait et a été supprimé, false sinon.
     */
    public boolean supprimer(Long id) {
        if (empruntRepository.existsById(id)) {
            empruntRepository.deleteById(id);
            return true;
        }
        return false;
    }
    /**
     * Liste les emprunts en cours.
     * @return Page contenant la liste des emprunts en cours
     */
    public Page<Emprunt> recupererEmpruntsEnCours(Pageable pageable){
        return this.empruntRepository.findByRenduFalse(pageable);
    }
    /**
     * Liste les emprunts en retard.
     * @return List contenant la liste des emprunts en retard
     */
    public List<Emprunt> recupererEmpruntsEnRetard(LocalDate date){
        return this.empruntRepository.findByRenduFalseAndDateRetourPrevueBefore(date);
    }
}

