package tn.zeros.template.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.repositories.BonRepository;
import tn.zeros.template.services.IServices.IBonService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BonService implements IBonService {

    private final BonRepository bonRepository;

    @Override
    public List<Bon> findAll() {
        return bonRepository.findAll();
    }

    @Override
    public Bon findById(Long id) {
        return bonRepository.findById(id).orElse(null);
    }

    @Override
    public Bon save(Bon bon) {
        return bonRepository.save(bon);
    }

    @Override
    public void deleteById(Long id) {
        bonRepository.deleteById(id);
    }
}
