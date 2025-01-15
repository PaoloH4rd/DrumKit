package org.paolo.drumkit_.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Messaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(length = 512)
    private String testo;

    @CreationTimestamp
    @Column(nullable = false,insertable = false,updatable = false,columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private LocalDateTime dataOra;

    private boolean primoUtente;

    private boolean isDisattivato;

    @ManyToOne
    @JoinColumn(name = "id_chat")
    private Chat chat;


}
