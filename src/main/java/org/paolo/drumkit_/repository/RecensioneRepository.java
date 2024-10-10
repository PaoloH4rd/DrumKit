package org.paolo.drumkit_.repository;

import org.paolo.drumkit_.model.Recensione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

    List<Recensione> findAllByUtente_IdAndIsDisattivatoIsFalse(long id_Utente);
    List<Recensione> findAllByProdotto_IdAndIsDisattivatoIsFalse(long id_Prodotto);
}
