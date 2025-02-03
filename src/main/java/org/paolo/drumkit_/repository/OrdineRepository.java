package org.paolo.drumkit_.repository;

import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.StatoOrdine;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
//    Optional<Ordine> findByUtente_IdAndStatoOrdine(long idUtente, StatoOrdine statoOrdine);

    @Query("SELECT o FROM Ordine o WHERE o.utente.id = :idUtente AND o.statoOrdine = :statoOrdine")
    List<Ordine> findFirstByUtente_IdAndStatoOrdine(@Param("idUtente") long idUtente,
                                                    @Param("statoOrdine") StatoOrdine statoOrdine,
                                                    Pageable pageable);

//    @Query("SELECT o FROM Ordine o " +
//            "WHERE o.utente.id = :idUtente " +
//            "ORDER BY o.dataConferma DESC")       // Ordina per data di conferma decrescente
//    Optional<Ordine> findUltimoOrdineEseguito(@Param("idUtente") Long idUtente, Pageable pageable);
}
