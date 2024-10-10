package org.paolo.drumkit_.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.paolo.drumkit_.model.Chat;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

//    List<Chat> findAllByCliente_IdAndIsChiusaIsfalse(long idCliente);
//    List<Chat> findAllByAdmin_IdAndIsChiusaIsfalse(long idAdmin);
////    List<Chat> findAllByCliente_IdAndAdmin_IdAndIsChiusaIsfalse(long idCliente, long idAdmin);
//    List<Chat> findAllByChiusaIsFalse();
//    Optional<Chat> findByIdAndChiusaIsFalse();
}
