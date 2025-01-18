package org.paolo.drumkit_.repository;


import org.paolo.drumkit_.model.Prodotto;
import org.paolo.drumkit_.model.StatoProdotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
    List<Prodotto> findAllByIsDisattivatoIsFalse();
    Optional<Prodotto> findByIdAndIsDisattivatoIsFalse(long id_Prodotto);
    List<Prodotto> findAllByStato(StatoProdotto stato);
    List<Prodotto> findAllByStatoAndProprietarioIdNot(StatoProdotto stato, Long idUtente);
    List<Prodotto> findAllByProprietarioIdAndStato(Long idUtente, StatoProdotto stato);
}