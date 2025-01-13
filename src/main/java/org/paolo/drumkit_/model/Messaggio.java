package org.paolo.drumkit_.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Messaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean isCliente;

    @Column(nullable = false)
    private Timestamp data;
    @Column(nullable = false)
    private String testo;

    private boolean isDisattivato;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, name = "id_chat")
    private Chat chat;


}
