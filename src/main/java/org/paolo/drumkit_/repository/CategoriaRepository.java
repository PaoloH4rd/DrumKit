package org.paolo.drumkit_.repository;

import org.paolo.drumkit_.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findAllByIsDisattivatoIsFalse();
}