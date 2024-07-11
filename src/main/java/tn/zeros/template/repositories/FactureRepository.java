package tn.zeros.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.zeros.template.entities.Facture;

@Repository
public interface FactureRepository extends JpaRepository<Facture,Long> {
}
