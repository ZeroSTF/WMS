package tn.zeros.template.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.repositories.BonRepository;
import tn.zeros.template.repositories.UserRepository;
import tn.zeros.template.services.IServices.IBonService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user by this email exists"));
    }

    @RequiredArgsConstructor
    @Service
    @Slf4j
    public static class BonService implements IBonService {
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
}
