package tn.zeros.template.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.zeros.template.controllers.DTO.BonLivraisonDTO;
import tn.zeros.template.controllers.DTO.CreateBonRequest;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.entities.Facture;
import tn.zeros.template.entities.Transaction;
import tn.zeros.template.entities.User;
import tn.zeros.template.services.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private final QRCodeService qrCodeService;
    final FactureService factureService;

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




    @GetMapping("/getAllBonsByUser")
    public ResponseEntity<?> getAllBonsByUser() {
        User currentUser;
        try {
            /*current user*/
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            currentUser = userService.loadUserByEmail(currentEmail);
            ////////////////////////////////////////////////////////////////////
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(bonService.findByUser(currentUser));
    }

    @PostMapping("/bonsCom")
    public ResponseEntity<Bon> createBonCommande(@RequestBody CreateBonRequest request) {
        Bon bon = bonService.createBonCommande(request);
        return ResponseEntity.created(URI.create("/api/bons/" + bon.getId()))
                .body(bon);
    }

/*************** Code QR  *********************************************/

    @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> getBonQRCode(@PathVariable Long id) {
        log.info("Generating QR code for bon id: {}", id);
        try {
            Bon bon = bonService.findById(id);
            if (bon == null) {
                log.warn("Bon not found with id: {}", id);
                return ResponseEntity.notFound().build();
            }
            log.info("Bon found: {}", bon);

            byte[] qrCodeImage = qrCodeService.generateQRCodeImage(bon);
            log.info("QR code generated successfully");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error generating QR code for bon id: " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/scan/{id}")
    public ResponseEntity<Bon> scanBon(@PathVariable Long id) {
        try {
            Bon bon = bonService.findById(id);
            if (bon == null) {
                return ResponseEntity.notFound().build();
            }

            // Mettre à jour le statut du bon
            bon.setStatus(true);
            bon = bonService.save(bon);

            //facturation if scan
            if (bon.isTofactur()==true ) { // Check both conditions
                Facture facture = new Facture();
                facture.setDate(LocalDate.now()); // Set facture date
           //     facture.setMontantTotal(bon.getMontantTotal()); // Set total amount from bon
                // Set paiementMode (optional, based on your logic)
           //     facture.setPaiementMode(PaiementMode.espece); // Default to undefined

                List<Bon> bons = new ArrayList<>();
                bons.add(bon);
                facture.setBon(bons);

                factureService.save(facture);
            /*    // Save facture and associate with bon
               Facture savedFacture = factureService.save(facture);
               bon.setFacture(savedFacture); // Associate facture with bon


                bonService.save(bon);

             */
            }

            return ResponseEntity.ok(bon);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


