package tn.zeros.template.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.zeros.template.controllers.DTO.BonLivraisonDTO;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.services.BonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Slf4j
public class BonController {
    final BonService bonService;

    @PostMapping("/Bon/livraison")
    public ResponseEntity<Bon> createBonLivraison(@RequestBody BonLivraisonDTO bonLivraisonDTO) {
        Bon createdBon = bonService.createBonLivraison(bonLivraisonDTO);
        return new ResponseEntity<>(createdBon, HttpStatus.CREATED);
    }


}
