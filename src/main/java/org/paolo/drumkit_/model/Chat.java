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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id_utente_uno","id_utente_due"})})
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_utente_uno",nullable = false)
    private Utente utenteUno;

    @ManyToOne
    @JoinColumn(name = "id_utente_due")
    private Utente utenteDue;

//    @ManyToOne
//    @JoinColumn(nullable = false , updatable = false , name = "id_prodotto")
//    private Prodotto prodotto;

    @OneToMany(mappedBy = "chat")
    private List<Messaggio> messaggi;




}
