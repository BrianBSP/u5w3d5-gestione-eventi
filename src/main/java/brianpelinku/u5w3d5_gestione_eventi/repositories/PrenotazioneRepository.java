package brianpelinku.u5w3d5_gestione_eventi.repositories;

import brianpelinku.u5w3d5_gestione_eventi.entities.Evento;
import brianpelinku.u5w3d5_gestione_eventi.entities.Prenotazioni;
import brianpelinku.u5w3d5_gestione_eventi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazioni, Integer> {

    boolean existsByUtenteIdAndEventoId(Utente utenteId, Evento eventoId);
}
