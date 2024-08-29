package tn.zeros.template.services.IServices;

import tn.zeros.template.controllers.DTO.CreateBonRequest;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.entities.Transaction;
import tn.zeros.template.entities.User;

import java.util.List;

public interface IBonService {
    List<Bon> findAll();
    Bon findById(Long id);
    Bon save(Bon bon);
    void deleteById(Long id);
    List<Transaction> findByUser(User user);

    Bon createBonCommande(CreateBonRequest request);

}
