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

    @Column(nullable = false , columnDefinition = "boolean default false")
    private boolean isDisattivato;

    //di default il prodotto è in attesa di approvazione
    @Column(nullable = false, columnDefinition = "varchar(255) default 'DA_APPROVARE'")
    @Enumerated(EnumType.STRING) // O EnumType.ORDINAL se vuoi usare numeri
    private StatoProdotto stato;

    private String motivoRifiuto; // Campo per memorizzare il motivo di rifiuto

    //il mio prodotto puo essere in piu righe ordine
    @OneToMany(mappedBy = "prodotto")
    private List<RigaOrdine> rigaOrdine;


    @OneToMany(mappedBy = "prodotto")
    private List<Chat> chats;

    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name = "id_utente")
    private Utente proprietario;

}
