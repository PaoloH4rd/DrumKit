package org.paolo.drumkit_.repository;

import org.paolo.drumkit_.model.RigaOrdine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RigaOrdineRepository extends JpaRepository<RigaOrdine, Long> {
//    List<RigaOrdine> findAllByOrdine(Long idOrdine);
}
