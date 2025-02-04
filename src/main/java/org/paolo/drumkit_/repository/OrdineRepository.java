package org.paolo.drumkit_.repository;

import org.paolo.drumkit_.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {
    List<Ordine> findAllByIsDisattivatoIsFalse();
    Optional<Ordine> findByIdAndIsDisattivatoIsFalse(long id_ordine);
    Optional<Ordine> findByUtente_IdAndDataConfermaIsNull(Long idUtente);


}
