package org.paolo.drumkit_.repository;

import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.StatoOrdine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {
    List<Ordine> findAllByUtente_IdAndIsDisattivatoIsFalse(long idUtente);
    // questo metodo lo può fare solo un admin
    List<Ordine> findAllByDataConfermaAndIsDisattivatoIsFalse(LocalDate dataConferma);
    List<Ordine> findAllByStatoOrdineAndIsDisattivatoIsFalse(StatoOrdine statoOrdine);
    List<Ordine> findAllByIsDisattivatoIsFalse();
    Optional<Ordine> findByIdAndIsDisattivatoIsFalse(long id_ordine);
    Optional<Ordine> findByUtente_IdAndDataConfermaIsNull(Long idUtente);
}
