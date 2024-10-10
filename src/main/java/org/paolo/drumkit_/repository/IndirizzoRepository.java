package org.paolo.drumkit_.repository;


import org.paolo.drumkit_.model.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {
    List<Indirizzo> findAllByUtente_IdAndIsDisattivatoIsFalse(long id_utente);
    List<Indirizzo> findAllByIsDefaultIsFalse();
    Optional<Indirizzo> findByIdAndIsDisattivatoIsFalse(long id);
}
