package org.paolo.drumkit_.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean isChiusa;

    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name = "id_cliente")
    private Utente cliente;

    //TODO why admin on chat -> chat is with vendor
    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name ="id_admin")
    private Utente admin;

    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name = "id_prodotto")
    private Prodotto prodotto;

    @OneToMany(mappedBy = "chat")
    private List<Messaggio> messaggi;



}
