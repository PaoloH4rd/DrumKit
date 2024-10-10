package org.paolo.drumkit_.repository;


import org.paolo.drumkit_.model.CartaDiCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartaDiCreditoRepository extends JpaRepository<CartaDiCredito, Long> {
//    List<CartaDiCredito>findAllByUtente_IdAndDisattivatoIsFalse(long idUtente);

}
