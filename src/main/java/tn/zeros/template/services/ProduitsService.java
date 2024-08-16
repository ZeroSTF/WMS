package tn.zeros.template.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.zeros.template.entities.Produits;
import tn.zeros.template.repositories.ProduitsRepository;
import tn.zeros.template.services.IServices.IProduitsService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProduitsService implements IProduitsService {
    private final ProduitsRepository produitRepository;

    @Override
    public List<Produits> findAll() {
        return produitRepository.findAll();
    }

    @Override
    public Produits findById(Long id) {
        return produitRepository.findById(id).orElse(null);
    }

    @Override
    public Produits save(Produits produit) {
        return produitRepository.save(produit);
    }

    @Override
    public void deleteById(Long id) {
        produitRepository.deleteById(id);
    }

}
