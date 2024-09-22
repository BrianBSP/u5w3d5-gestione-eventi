package brianpelinku.u5w3d5_gestione_eventi.services;

import brianpelinku.u5w3d5_gestione_eventi.entities.Utente;
import brianpelinku.u5w3d5_gestione_eventi.enums.RuoloUtente;
import brianpelinku.u5w3d5_gestione_eventi.exceptions.BadRequestException;
import brianpelinku.u5w3d5_gestione_eventi.exceptions.NotFoundException;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewUtenteDTO;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewUtenteRespDTO;
import brianpelinku.u5w3d5_gestione_eventi.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtentiService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato."));
    }

    public NewUtenteRespDTO saveUtente(NewUtenteDTO body) {

        this.utenteRepository.findByEmail(body.email()).ifPresent(author -> {
            throw new BadRequestException("L'email " + body.email() + " è già in uso.");
        });

        RuoloUtente ruoloUtente;

        try {
            ruoloUtente = RuoloUtente.valueOf(body.ruolo().toUpperCase());
            if (ruoloUtente == RuoloUtente.ADMIN)
                throw new BadRequestException("Errore. Nessuno può inserirsi come ADMIN");
        } catch (Exception e) {
            throw new BadRequestException("Errore. Il ruolo inserito non esiste.");
        }

        Utente newUtente = new Utente(body.nome(), body.cognome(), body.email(), bcrypt.encode(body.password()), ruoloUtente);

        // salvo il nuovo record
        return new NewUtenteRespDTO(this.utenteRepository.save(newUtente).getId());
    }

    // cerco tutti gli utenti
    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteRepository.findAll(pageable);
    }

    // cerco utenti byId
    public Utente findById(int utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    // delete utente
    public void findByIdAndDelete(int utenteId) {
        Utente trovato = this.findById(utenteId);
        this.utenteRepository.delete(trovato);
    }


}
