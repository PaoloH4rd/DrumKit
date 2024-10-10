package org.paolo.drumkit_.repository;

import org.paolo.drumkit_.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<Foto, Long> {
    List<Foto> findAllByIsDisattivatoIsFalse();
    Foto  findByIdAndIsDisattivatoIsFalse(Long id);
}
