package tn.zeros.template.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.zeros.template.controllers.DTO.BonLivraisonDTO;
import tn.zeros.template.controllers.DTO.CreateBonRequest;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.entities.Transaction;
import tn.zeros.template.entities.User;
import tn.zeros.template.services.BonService;
import tn.zeros.template.services.TransactionService;
import tn.zeros.template.services.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Slf4j
public class BonController {
    final BonService bonService;
    final UserService userService;
    final TransactionService transactionService;

    @PostMapping("/Bon/livraison")
    public ResponseEntity<Bon> createBonLivraison(@RequestBody BonLivraisonDTO bonLivraisonDTO) {
        Bon createdBon = bonService.createBonLivraison(bonLivraisonDTO);
        return new ResponseEntity<>(createdBon, HttpStatus.CREATED);
    }


    @GetMapping("/bon-list")
    public ResponseEntity<List<Bon>> getBonList() {
        List<Bon> bons = bonService.findAll();
        return ResponseEntity.ok(bons);
    }


   /* @PostMapping("/bonsCom")
    public ResponseEntity<Bon> createBonCommande(@RequestParam Long senderId,
                                                 @RequestParam Long receiverId,
                                                 @RequestParam List<Long> transactionIds) {
        Bon bon = bonService.createBonCommande(senderId, receiverId, transactionIds);
        return ResponseEntity.created(URI.create("/api/bons/" + bon.getId())).body(bon);
    }*/

    @PostMapping("/bonsCom")
    public ResponseEntity<Bon> createBonCommande(@RequestBody CreateBonRequest request) {
        Bon bon = bonService.createBonCommande(request);
        return ResponseEntity.created(URI.create("/api/bons/" + bon.getId()))
                .body(bon);
    }
}

