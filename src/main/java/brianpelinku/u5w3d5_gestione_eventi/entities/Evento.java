package brianpelinku.u5w3d5_gestione_eventi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "eventi")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    private String titolo;
    private String descrizione;
    @Column(name = "data_evento")
    private LocalDate dataEvento;
    private String luogo;
    @Column(name = "numero_posti-disponibili")
    private int numeroPostiDisponibili;

    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    private Utente organizzatoreId;

    @JsonIgnore
    @OneToMany(mappedBy = "eventoId")
    private List<Prenotazioni> prenotazioniList;

}
