package brianpelinku.u5w3d5_gestione_eventi.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "prenotazioni")
public class Prenotazioni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;
    @Column(name = "nome_evento")
    private String nomeEvento;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utenteId;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento eventoId;
}
