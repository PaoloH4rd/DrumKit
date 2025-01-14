package org.paolo.drumkit_.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RigaOrdine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int quantita;

    @Column(nullable = false)
    private double prezzoTot;

    private boolean isDisattivato;


    //ogniriga d'ordine prima di avere un ordine associato devo fare il checkout.
    //avra un ordine null fino a quando non viene fatto il checkout
    @ManyToOne
    @JoinColumn(/*nullable = false , updatable = false ,*/ name = "id_ordine")
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name = "id_prodotto")
    private Prodotto prodotto;

}
