package org.paolo.drumkit_.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Utente implements UserDetails {

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

    @Column(nullable = false)
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

    public Utente(String nome, String cognome, String email, String password, String dataNascita, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.dataNascita = LocalDate.parse(dataNascita);
        this.ruolo = ruolo;
    }

    // vuole ruolo -> sarebbe un get ruolo con spring security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+ruolo.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}