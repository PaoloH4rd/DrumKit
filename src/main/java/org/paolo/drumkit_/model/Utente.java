package org.paolo.drumkit_.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Utente  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private LocalDate dataNascita;

    @Column(nullable = false)
    private Ruolo ruolo;

    private boolean isDisattivato;

    @OneToMany(mappedBy = "utente")
    private List<CartaDiCredito> carte;

    @OneToMany(mappedBy = "utente")
    private List<Indirizzo> indirizzo;

    @OneToMany(mappedBy = "utente")
    private List<Recensione> recensioni;

    @OneToMany(mappedBy = "utente")
    private List<Ordine> ordini;

    @OneToMany(mappedBy = "cliente")
    private List<Chat> chats;


    /////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    // ///////////
    // vuole ruolo_ -> sarebbe un get ruolo con spring seurity
}