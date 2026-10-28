package com.pehiboux.rent_a_book.controller;

import com.pehiboux.rent_a_book.dto.LivreRequest;
import com.pehiboux.rent_a_book.dto.LivreResponse;
import com.pehiboux.rent_a_book.mapper.LivreMapper;
import com.pehiboux.rent_a_book.model.Livre;
import com.pehiboux.rent_a_book.service.LivreService;
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
@RequestMapping("/api/v1/livres")
@Tag(name = "Livres", description = "Catalogue des livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    // Endpoint 7 — GET /api/v1/livres : lister tous les livres
    @Operation(summary = "Lister tous les livres")
    @ApiResponse(responseCode = "200", description = "Page de livres récupérée avec succès")
    @GetMapping
    public ResponseEntity<PagedModel<LivreResponse>> listerTous(
            @PageableDefault(size = 10, sort = "titre") Pageable pageable) {
        Page<Livre> page = livreService.recupererTous(pageable);
        return ResponseEntity.ok(LivreMapper.versPage(page));
    }

    // Endpoint 8 — GET /api/v1/livres/{id} : obtenir un livre par id
    @Operation(summary = "Obtenir un livre par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livre trouvé"),
            @ApiResponse(responseCode = "404", description = "Livre inexistant")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivreResponse> obtenirParId(@PathVariable Long id) {
        Optional<Livre> livre = livreService.recupererParId(id);
        if (livre.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(LivreMapper.versResponse(livre.get()));
    }

    // Endpoint 9 — POST /api/v1/livres : créer un livre
    @Operation(summary = "Créer un nouveau livre")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livre créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou auteur inexistant"),
            @ApiResponse(responseCode = "409", description = "ISBN déjà utilisé")
    })
    @PostMapping
    public ResponseEntity<LivreResponse> creer(@Valid @RequestBody LivreRequest request) {
        if (!livreService.auteurExiste(request.getAuteurId())) {
            return ResponseEntity.badRequest().build();
        }
        if (livreService.isbnExiste(request.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Livre livre = LivreMapper.versEntite(request);
        Livre livreCree = livreService.creer(livre);
        return ResponseEntity.status(HttpStatus.CREATED).body(LivreMapper.versResponse(livreCree));
    }

    // Endpoint 10 — PUT /api/v1/livres/{id} : mettre à jour un livre
    @Operation(summary = "Mettre à jour un livre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livre mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou auteur inexistant"),
            @ApiResponse(responseCode = "404", description = "Livre inexistant"),
            @ApiResponse(responseCode = "409", description = "ISBN déjà utilisé par un autre livre")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LivreResponse> mettreAJour(
            @PathVariable Long id,
            @Valid @RequestBody LivreRequest request) {
        Optional<Livre> livreExistant = livreService.recupererParId(id);
        if (livreExistant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!livreService.auteurExiste(request.getAuteurId())) {
            return ResponseEntity.badRequest().build();
        }
        if (livreService.isbnUtiliseParUnAutreLivre(request.getIsbn(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Livre nouvellesValeurs = LivreMapper.versEntite(request);
        Livre livreMaj = livreService.mettreAJour(livreExistant.get(), nouvellesValeurs);
        return ResponseEntity.ok(LivreMapper.versResponse(livreMaj));
    }

    // Endpoint 11 — DELETE /api/v1/livres/{id} : supprimer un livre
    @Operation(summary = "Supprimer un livre")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livre supprimé"),
            @ApiResponse(responseCode = "404", description = "Livre inexistant")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (!livreService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        livreService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint 12 — GET /api/v1/livres/recherche?titre=... : rechercher des livres par titre
    @Operation(summary = "Rechercher des livres par titre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Résultats de la recherche"),
            @ApiResponse(responseCode = "400", description = "Paramètre titre absent")
    })
    @GetMapping("/recherche")
    public ResponseEntity<PagedModel<LivreResponse>> rechercher(
            @RequestParam String titre,
            @PageableDefault(size = 10, sort = "titre") Pageable pageable) {
        Page<Livre> page = livreService.rechercherParTitre(titre, pageable);
        return ResponseEntity.ok(LivreMapper.versPage(page));
    }

    // Endpoint 13 — GET /api/v1/livres/disponibles : lister les livres disponibles
    @Operation(summary = "Lister les livres disponibles")
    @ApiResponse(responseCode = "200", description = "Page de livres disponibles")
    @GetMapping("/disponibles")
    public ResponseEntity<PagedModel<LivreResponse>> listerDisponibles(
            @PageableDefault(size = 10, sort = "titre") Pageable pageable) {
        Page<Livre> page = livreService.recupererDisponibles(pageable);
        return ResponseEntity.ok(LivreMapper.versPage(page));
    }

    // Endpoint 14 — PATCH /api/v1/livres/{id}/stock?quantite=... : ajouter du stock
    @Operation(summary = "Réapprovisionner le stock d'un livre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock mis à jour"),
            @ApiResponse(responseCode = "400", description = "Paramètre quantite absent, non numérique ou inférieur ou égal à 0"),
            @ApiResponse(responseCode = "404", description = "Livre inexistant")
    })
    @PatchMapping("/{id}/stock")
    public ResponseEntity<LivreResponse> ajouterStock(
            @PathVariable Long id,
            @RequestParam Integer quantite) {
        if (quantite <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Livre> livreExistant = livreService.recupererParId(id);
        if (livreExistant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Livre livreMaj = livreService.ajouterStock(livreExistant.get(), quantite);
        return ResponseEntity.ok(LivreMapper.versResponse(livreMaj));
    }
}
