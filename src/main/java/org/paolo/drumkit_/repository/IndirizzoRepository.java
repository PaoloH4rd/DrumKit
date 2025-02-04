package org.paolo.drumkit_.repository;


import org.paolo.drumkit_.model.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {
    Optional<Indirizzo> findByIdAndIsDisattivatoIsFalse(long id);
}
