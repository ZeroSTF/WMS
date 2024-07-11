package tn.zeros.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.zeros.template.entities.Bon;


@Repository
public interface BonRepository extends JpaRepository<Bon,Long> {

}
