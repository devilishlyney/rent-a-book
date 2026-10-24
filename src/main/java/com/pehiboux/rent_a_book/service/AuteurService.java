package com.pehiboux.rent_a_book.service;

import com.pehiboux.rent_a_book.model.Auteur;
import com.pehiboux.rent_a_book.model.Livre;
import com.pehiboux.rent_a_book.repository.AuteurRepository;
import com.pehiboux.rent_a_book.repository.LivreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuteurService {

    private final AuteurRepository auteurRepository;
    private final LivreRepository livreRepository;

    public AuteurService(AuteurRepository auteurRepository, LivreRepository livreRepository) {
        this.auteurRepository = auteurRepository;
        this.livreRepository = livreRepository;
    }

    public Page<Auteur> recupererTous(Pageable pageable) {
        return auteurRepository.findAll(pageable);
    }

    public Optional<Auteur> recupererParId(Long id) {
        return auteurRepository.findById(id);
    }

    public boolean existe(Long id) {
        return auteurRepository.existsById(id);
    }

    public Auteur creer(Auteur auteur) {
        auteur.setId(null);
        return auteurRepository.save(auteur);
    }

    public Auteur mettreAJour(Auteur auteurExistant, Auteur nouvellesValeurs) {
        auteurExistant.setNom(nouvellesValeurs.getNom());
        auteurExistant.setPrenom(nouvellesValeurs.getPrenom());
        auteurExistant.setNationalite(nouvellesValeurs.getNationalite());
        return auteurRepository.save(auteurExistant);
    }

    public boolean aDesLivres(Long id) {
        return livreRepository.existsByAuteurId(id);
    }

    public void supprimer(Long id) {
        auteurRepository.deleteById(id);
    }

    public Page<Livre> recupererLivres(Long id, Pageable pageable) {
        return livreRepository.findByAuteurId(id, pageable);
    }
}
