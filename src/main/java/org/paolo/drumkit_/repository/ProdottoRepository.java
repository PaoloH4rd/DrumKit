package org.paolo.drumkit_.repository;


import org.paolo.drumkit_.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
//    List<Prodotto> findAllByCategoriaAndIsDisattivatoIsFalse(long idCategoria);
    List<Prodotto> findAllByIsDisattivatoIsFalse();
    Optional<Prodotto> findByIdAndIsDisattivatoIsFalse(long id_Prodotto);
    // getTopVotazioniCategoria
}
