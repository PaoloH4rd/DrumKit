package org.paolo.drumkit_.repository;

import org.paolo.drumkit_.model.Messaggio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {
}
