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
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false , updatable = false , name = "id_utente")
    private Utente utente;

    @Column(nullable = false)
    private LocalDate dataConferma;

    @Column(nullable = false)
    private StatoOrdine statoOrdine;

    private boolean isDisattivato;

    @OneToMany(mappedBy = "ordine")
    private List<RigaOrdine> rigaOrdine;
}
