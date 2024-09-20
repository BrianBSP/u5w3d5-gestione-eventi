package brianpelinku.u5w3d5_gestione_eventi.repositories;

import brianpelinku.u5w3d5_gestione_eventi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Optional<Utente> findByEmail(String email);

    boolean existsByEmail(String email);
}
