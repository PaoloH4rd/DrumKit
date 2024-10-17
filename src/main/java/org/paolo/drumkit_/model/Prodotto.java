package org.paolo.drumkit_.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//prodotto lo metti sempre tu in vendita
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
    private double prezzo;

    @Column(nullable = false)
    private int quantita;

    private boolean isDisattivato;


    //il mio prodotto puo essere in piu riche di ordine
    @OneToMany(mappedBy = "prodotto")
    private List<RigaOrdine> rigaOrdine;


    @OneToMany(mappedBy = "prodotto")
    private List<Chat> chats;
}
