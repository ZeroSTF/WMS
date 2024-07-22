package tn.zeros.template.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.zeros.template.entities.Facture;
import tn.zeros.template.services.FactureService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facture")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Slf4j
public class FactureController {
    private final FactureService factureService;


    @GetMapping("/factures")
    public List<Facture> getAllFactures() {
        return factureService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        Facture facture = factureService.findById(id);
        if (facture == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facture);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> createFacture(@RequestBody Facture facture) {
        Facture factureAdded = factureService.save(facture);

        if (factureAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(factureAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Facture> updateFacture(@PathVariable Long id, @RequestBody Facture facture) {
        facture.setId(id);
        Facture updatedFacture = factureService.save(facture);
        if (updatedFacture == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFacture);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
