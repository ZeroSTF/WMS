package tn.zeros.template.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.zeros.template.entities.Facture;
import tn.zeros.template.repositories.FactureRepository;
import tn.zeros.template.services.IServices.IFactureService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class FactureService implements IFactureService {
    private final FactureRepository factureRepository;


    public List<Facture> findAll() {
        return factureRepository.findAll();
    }

    @Override
    public Facture findById(Long id) {
        return factureRepository.findById(id).orElse(null);
    }

    @Override
    public Facture save(Facture facture) {
        return factureRepository.save(facture);
    }

    @Override
    public void deleteById(Long id) {
        factureRepository.deleteById(id);
    }
}
