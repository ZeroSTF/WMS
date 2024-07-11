package tn.zeros.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.zeros.template.entities.Produits;

@Repository
public interface ProduitsRepository extends JpaRepository<Produits,Long> {
}
