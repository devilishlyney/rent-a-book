package com.pehiboux.rent_a_book.controller;

import com.pehiboux.rent_a_book.dto.AuteurRequest;
import com.pehiboux.rent_a_book.dto.AuteurResponse;
import com.pehiboux.rent_a_book.dto.LivreResponse;
import com.pehiboux.rent_a_book.mapper.AuteurMapper;
import com.pehiboux.rent_a_book.mapper.LivreMapper;
import com.pehiboux.rent_a_book.model.Auteur;
import com.pehiboux.rent_a_book.model.Livre;
import com.pehiboux.rent_a_book.service.AuteurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auteurs")
@Tag(name = "Auteurs", description = "Gestion des auteurs")
public class AuteurController {

    private final AuteurService auteurService;

    public AuteurController(AuteurService auteurService) {
        this.auteurService = auteurService;
    }

    // Endpoint 1 — GET /api/v1/auteurs : une liste paginée
    @Operation(summary = "Lister tous les auteurs")
    @ApiResponse(responseCode = "200", description = "Page d'auteurs récupérée avec succès")
    @GetMapping
    public ResponseEntity<PagedModel<AuteurResponse>> listerTous(
            @PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        Page<Auteur> page = auteurService.recupererTous(pageable);
        return ResponseEntity.ok(AuteurMapper.versPage(page));
    }

    // Endpoint 2 — GET /api/v1/auteurs/{id} : obtenir un auteur par id
    @Operation(summary = "Obtenir un auteur par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auteur trouvé"),
            @ApiResponse(responseCode = "404", description = "Auteur inexistant")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuteurResponse> obtenirParId(@PathVariable Long id) {
        Optional<Auteur> auteur = auteurService.recupererParId(id);
        if (auteur.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AuteurMapper.versResponse(auteur.get()));
    }

    // Endpoint 3 — POST /api/v1/auteurs : créer un auteur
    @Operation(summary = "Créer un nouvel auteur")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Auteur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    public ResponseEntity<AuteurResponse> creer(@Valid @RequestBody AuteurRequest request) {
        Auteur auteur = AuteurMapper.versEntite(request);
        Auteur auteurCree = auteurService.creer(auteur);
        return ResponseEntity.status(HttpStatus.CREATED).body(AuteurMapper.versResponse(auteurCree));
    }

    // Endpoint 4 — PUT /api/v1/auteurs/{id} : modifier un auteur
    @Operation(summary = "Mettre à jour un auteur")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auteur mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Auteur inexistant")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AuteurResponse> mettreAJour(
            @PathVariable Long id,
            @Valid @RequestBody AuteurRequest request) {
        Optional<Auteur> auteurExistant = auteurService.recupererParId(id);
        if (auteurExistant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Auteur nouvellesValeurs = AuteurMapper.versEntite(request);
        Auteur auteurMaj = auteurService.mettreAJour(auteurExistant.get(), nouvellesValeurs);
        return ResponseEntity.ok(AuteurMapper.versResponse(auteurMaj));
    }

    // Endpoint 5 — DELETE /api/v1/auteurs/{id} : supprimer un auteur
    @Operation(summary = "Supprimer un auteur (impossible s'il a encore des livres)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Auteur supprimé"),
            @ApiResponse(responseCode = "404", description = "Auteur inexistant"),
            @ApiResponse(responseCode = "409", description = "L'auteur a encore des livres")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (!auteurService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        if (auteurService.aDesLivres(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        auteurService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint 6 — GET /api/v1/auteurs/{id}/livres : lister les livres d'un auteur
    @Operation(summary = "Lister les livres d'un auteur")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livres de l'auteur récupérés avec succès"),
            @ApiResponse(responseCode = "404", description = "Auteur inexistant")
    })
    @GetMapping("/{id}/livres")
    public ResponseEntity<PagedModel<LivreResponse>> listerLivres(
            @PathVariable Long id,
            @PageableDefault(size = 10, sort = "titre") Pageable pageable) {
        if (!auteurService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        Page<Livre> page = auteurService.recupererLivres(id, pageable);
        return ResponseEntity.ok(LivreMapper.versPage(page));
    }
}
