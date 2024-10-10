package org.paolo.drumkit_.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String indirizzoR1;
    private String indirizzoR2;
    @Column(nullable = false)
    private String cap;
    @Column(nullable = false)
    private String comune;
    @Column(nullable = false)
    private String stato;
    @Column(nullable = false)
    private String numeroTel;
    @Column(nullable = false)
    private boolean isDefault;
    private boolean isDisattivato;

    @ManyToOne()
    @JoinColumn(nullable = false , updatable = false , name = "id_utente")
    private Utente utente;
}
