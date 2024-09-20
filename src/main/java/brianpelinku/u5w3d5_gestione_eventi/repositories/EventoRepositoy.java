package brianpelinku.u5w3d5_gestione_eventi.repositories;

import brianpelinku.u5w3d5_gestione_eventi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepositoy extends JpaRepository<Evento, Integer> {

}
