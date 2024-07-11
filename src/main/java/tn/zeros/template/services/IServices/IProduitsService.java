package tn.zeros.template.services.IServices;

import tn.zeros.template.entities.Produits;

import java.util.List;

public interface IProduitsService {
    List<Produits> findAll();
    Produits findById(Long id);
    Produits save(Produits produit);
    void deleteById(Long id);
}
