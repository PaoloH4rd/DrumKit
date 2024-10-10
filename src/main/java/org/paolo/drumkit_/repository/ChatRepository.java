package org.paolo.drumkit_.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.paolo.drumkit_.model.Chat;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByCliente_IdAndIsChiusaIsFalse(long idCliente);
    List<Chat> findAllByIsChiusaIsFalse();
    Optional<Chat> findByIdAndIsChiusaIsFalse(long idChat);
}
