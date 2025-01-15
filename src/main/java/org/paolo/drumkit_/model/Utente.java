package org.paolo.drumkit_.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String nome;
    @NotNull
    private String cognome;

    @NotNull
    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ruolo ruolo;

    private boolean isDisattivato;

    @Column(nullable = false)
    private LocalDate dataNascita;

    @OneToMany(mappedBy = "utente")
    private List<Indirizzo> indirizzo;

    @OneToMany(mappedBy = "utente")
    private List<Ordine> ordini;

    @OneToMany(mappedBy = "proprietario")
    private List<Prodotto> prodotti; // Rappresenta i prodotti posseduti dall'utente


    public Utente(String nome, String cognome, String email, String password, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
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