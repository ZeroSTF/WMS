package tn.zeros.template.services.IServices;

import tn.zeros.template.entities.Facture;

import java.util.List;

public interface IFactureService {
    List<Facture> findAll();
    Facture findById(Long id);
    Facture save(Facture facture);
    void deleteById(Long id);
}
