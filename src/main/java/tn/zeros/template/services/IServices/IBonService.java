package tn.zeros.template.services.IServices;

import tn.zeros.template.entities.Bon;

import java.util.List;

public interface IBonService {
    List<Bon> findAll();
    Bon findById(Long id);
    Bon save(Bon bon);
    void deleteById(Long id);
}
