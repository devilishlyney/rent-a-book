package com.pehiboux.rent_a_book.controller;

import com.pehiboux.rent_a_book.dto.EmpruntRequest;
import com.pehiboux.rent_a_book.dto.EmpruntResponse;
import com.pehiboux.rent_a_book.mapper.EmpruntMapper;
import com.pehiboux.rent_a_book.model.Emprunt;
import com.pehiboux.rent_a_book.service.EmpruntService;
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
@RequestMapping("/api/v1/emprunts")
@Tag(name = "Emprunts", description = "Emprunts et retours des livres")
public class EmpruntController {

    private final EmpruntService empruntService;

    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    // Endpoint 15 — GET /api/v1/emprunts : lister tous les emprunts
    @Operation(summary = "Lister tous les emprunts")
    @ApiResponse(responseCode = "200", description = "Page d'emprunts récupérée avec succès")
    @GetMapping
    public ResponseEntity<PagedModel<EmpruntResponse>> listerTous(
            @PageableDefault(size = 10, sort = "dateEmprunt") Pageable pageable) {
        Page<Emprunt> page = empruntService.recupererTous(pageable);
        return ResponseEntity.ok(EmpruntMapper.versPage(page));
    }

    // Endpoint 16 — GET /api/v1/emprunts/{id} : obtenir un emprunt par id
    @Operation(summary = "Obtenir un emprunt par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Emprunt trouvé"),
            @ApiResponse(responseCode = "404", description = "Emprunt inexistant")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmpruntResponse> obtenirParId(@PathVariable Long id) {
        Optional<Emprunt> opt = empruntService.recupererParId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EmpruntMapper.versResponse(opt.get()));
    }

    // Endpoint 17 — POST /api/v1/emprunts : créer un emprunt
    @Operation(summary = "Créer un emprunt")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Emprunt créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou livre inexistant"),
            @ApiResponse(responseCode = "409", description = "Livre indisponible")
    })
    @PostMapping
    public ResponseEntity<EmpruntResponse> creer(@Valid @RequestBody EmpruntRequest request) {
        if (!empruntService.livreExiste(request.getLivreId())) {
            return ResponseEntity.badRequest().build();
        }
        if (!empruntService.livreDisponible(request.getLivreId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Emprunt emprunt = EmpruntMapper.versEntite(request);
        Emprunt empruntCree = empruntService.creer(emprunt);
        return ResponseEntity.status(HttpStatus.CREATED).body(EmpruntMapper.versResponse(empruntCree));
    }

    // Endpoint 18 — PATCH /api/v1/emprunts/{id}/retour : retourner un livre emprunté
    @Operation(summary = "Retourner un livre emprunté")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Emprunt retourné avec succès"),
            @ApiResponse(responseCode = "404", description = "Emprunt inexistant"),
            @ApiResponse(responseCode = "409", description = "Emprunt déjà rendu")
    })
    @PatchMapping("/{id}/retour")
    public ResponseEntity<EmpruntResponse> retourner(@PathVariable Long id) {
        Optional<Emprunt> opt = empruntService.recupererParId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Emprunt emprunt = opt.get();
        if (Boolean.TRUE.equals(emprunt.getRendu())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Emprunt maj = empruntService.retourner(emprunt);
        return ResponseEntity.ok(EmpruntMapper.versResponse(maj));
    }

    // Endpoint 19 — GET /api/v1/emprunts/en-cours : lister les emprunts non rendus
    @Operation(summary = "Lister les emprunts en cours (non rendus)")
    @ApiResponse(responseCode = "200", description = "Page des emprunts en cours")
    @GetMapping("/en-cours")
    public ResponseEntity<PagedModel<EmpruntResponse>> listerEnCours(
            @PageableDefault(size = 10, sort = "dateEmprunt") Pageable pageable) {
        Page<Emprunt> page = empruntService.recupererEnCours(pageable);
        return ResponseEntity.ok(EmpruntMapper.versPage(page));
    }

    // Endpoint 20 — GET /api/v1/emprunts/en-retard : lister les emprunts en retard
    @Operation(summary = "Lister les emprunts en retard")
    @ApiResponse(responseCode = "200", description = "Page des emprunts en retard")
    @GetMapping("/en-retard")
    public ResponseEntity<PagedModel<EmpruntResponse>> listerEnRetard(
            @PageableDefault(size = 10, sort = "dateEmprunt") Pageable pageable) {
        Page<Emprunt> page = empruntService.recupererEnRetard(pageable);
        return ResponseEntity.ok(EmpruntMapper.versPage(page));
    }
}
