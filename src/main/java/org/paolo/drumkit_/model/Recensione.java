package org.paolo.drumkit_.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String titolo;
    @Column(nullable = false)
    private String testo;
    @Column(nullable = false)
    private int voto;

    private boolean isDisattivato;

    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name = "id_utente")
    private Utente utente;

    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name = "id_prodotto")
    private Prodotto prodotto;
}
