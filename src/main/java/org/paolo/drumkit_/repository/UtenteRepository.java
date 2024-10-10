package org.paolo.drumkit_.repository;

import org.paolo.drumkit_.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByEmailAndPasswordAndIsDisattivatoIsFalse(String email , String password);
    List<Utente> findAllByIsDisattivatoIsFalse();

    Optional<Utente> findByEmailAndIsDisattivatoIsFalse(String email);
}
