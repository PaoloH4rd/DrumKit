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

    @OneToMany(mappedBy = "prodotto")
    private List<Foto> foto;

    // il prodotto puo essere in piu categorie ?
    //se il prodotto è tamburo e piatti non c'è problema
    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name = "id_categoria")
    private Categoria categoria;

    //il mio prodotto puo essere in piu riche di ordine
    @OneToMany(mappedBy = "prodotto")
    private List<RigaOrdine> rigaOrdine;

    @OneToMany(mappedBy = "prodotto")
    private List<Recensione> recensioni;

    @OneToMany(mappedBy = "prodotto")
    private List<Chat> chats;
}
