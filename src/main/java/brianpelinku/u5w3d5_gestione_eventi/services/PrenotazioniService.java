package brianpelinku.u5w3d5_gestione_eventi.services;

import brianpelinku.u5w3d5_gestione_eventi.entities.Evento;
import brianpelinku.u5w3d5_gestione_eventi.entities.Prenotazioni;
import brianpelinku.u5w3d5_gestione_eventi.entities.Utente;
import brianpelinku.u5w3d5_gestione_eventi.exceptions.BadRequestException;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewPrenotazioneDTO;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewPrenotazioneRespDTO;
import brianpelinku.u5w3d5_gestione_eventi.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioniService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private EventiService eventiService;

    public NewPrenotazioneRespDTO savePrenotazione(NewPrenotazioneDTO body) {

        Utente utente = utentiService.findById(body.utenteId());
        Evento evento = eventiService.findById(body.eventoId());

        if (this.prenotazioneRepository.existsByUtenteIdAndEventoId(utente, evento))
            throw new BadRequestException("L'utente " + utente.getId() + " ha già una prenotazione per l'evento " + evento.getId());

        Prenotazioni prenotazione = new Prenotazioni();
        prenotazione.setNomeEvento(evento.getTitolo());
        prenotazione.setUtenteId(utente);
        prenotazione.setEventoId(evento);


        // salvo il nuovo record
        return new NewPrenotazioneRespDTO(this.prenotazioneRepository.save(prenotazione).getId());
    }
}
