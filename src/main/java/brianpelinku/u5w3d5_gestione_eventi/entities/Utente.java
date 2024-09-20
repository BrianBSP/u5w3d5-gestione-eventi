package brianpelinku.u5w3d5_gestione_eventi.entities;

import brianpelinku.u5w3d5_gestione_eventi.enums.RuoloUtente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    private String nome;
    private String cognome;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(name = "ruolo_utente", nullable = false)
    @Enumerated(EnumType.STRING)
    private RuoloUtente ruoloUtente;

    @JsonIgnore
    @OneToMany(mappedBy = "organizzatoreId")
    private List<Evento> eventoList;

    @JsonIgnore
    @OneToMany(mappedBy = "utenteId")
    private List<Prenotazioni> prenotazioniList;
}
