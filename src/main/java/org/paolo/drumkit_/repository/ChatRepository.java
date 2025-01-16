package org.paolo.drumkit_.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.paolo.drumkit_.model.Chat;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("select c from Chat c where c.utenteUno.id= :id or c.utenteDue.id= :id")
    List<Chat> findAllByIdUtente(long id);

    @Query("select c from Chat c where c.utenteUno.email= :email or c.utenteDue.email= :email")
    List<Chat> findAllByEmail(String email);

    @Query("select c from Chat c where (c.utenteUno.email= :emailUno and c.utenteDue.email= :emailDue) or (c.utenteUno.email= :emailDue and c.utenteDue.email= :emailUno)")
    Optional<Chat> findAllByEmail(String emailUno, String emailDue);
}
