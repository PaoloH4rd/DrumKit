package org.paolo.drumkit_.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//stiamo impostando che le due chiavi che gli diamo , saranno univoche
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"numero_carta","id_utente"})})
public class CartaDiCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false , updatable = false)
    private String numeroCarta;

    // Mettiamo come scadenza l'ultimo giorno del mese in cui scade
    @Column(nullable = false , updatable = false)
    private LocalDate scandenza;

    @Column(nullable = false,updatable = false)
    private String cvv;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    private boolean isDefault;

    private boolean isDisattivato;
    //ogni carta di credito è associata ad un utente
    // una volta associata non si puo cambiare
    //nella colonna utente di carta di credito che è una fk
    // andrò a mettere l'id dell'utente
    @ManyToOne
    @JoinColumn(nullable = false,updatable = false,name = "id_utente")
    private Utente utente;

}
