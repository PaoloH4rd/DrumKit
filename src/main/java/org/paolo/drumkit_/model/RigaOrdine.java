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

    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name = "id_ordine")
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name = "id_prodotto")
    private Prodotto prodotto;

}
