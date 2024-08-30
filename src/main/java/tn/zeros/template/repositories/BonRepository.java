package tn.zeros.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.entities.User;
import tn.zeros.template.entities.enums.BonType;

import java.util.List;


@Repository
public interface BonRepository extends JpaRepository<Bon,Long> {
    Long countByType(BonType type);
    List<Bon> findByTypeAndSender(BonType type, User sender);

}
