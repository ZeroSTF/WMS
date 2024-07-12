package tn.zeros.template.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.zeros.template.entities.Produits;
import tn.zeros.template.services.ProduitsService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@Slf4j
public class ProduitsContoller {
    private final ProduitsService produitsService;

    @GetMapping
    public List<Produits> getAllProduits() {
        return produitsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produits> getProduitById(@PathVariable Long id) {
        Produits produit = produitsService.findById(id);
        if (produit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produit);
    }

    @PostMapping
    public ResponseEntity<Void> createProduit(@RequestBody Produits produit) {
        Produits produitAdded = produitsService.save(produit);

        if (produitAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produitAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produits> updateProduit(@PathVariable Long id, @RequestBody Produits produit) {
        produit.setId(id);
        Produits updatedProduit = produitsService.save(produit);
        if (updatedProduit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
