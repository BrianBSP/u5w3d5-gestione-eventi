package brianpelinku.u5w3d5_gestione_eventi.services;

import brianpelinku.u5w3d5_gestione_eventi.entities.Evento;
import brianpelinku.u5w3d5_gestione_eventi.entities.Prenotazioni;
import brianpelinku.u5w3d5_gestione_eventi.entities.Utente;
import brianpelinku.u5w3d5_gestione_eventi.exceptions.BadRequestException;
import brianpelinku.u5w3d5_gestione_eventi.exceptions.NotFoundException;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewPrenotazioneDTO;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewPrenotazioneRespDTO;
import brianpelinku.u5w3d5_gestione_eventi.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Prenotazioni> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazioni findById(int prenotazioneId) {
        return this.prenotazioneRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public void findByIdAndDelete(int prenotazioneId) {
        Prenotazioni trovato = this.findById(prenotazioneId);
        this.prenotazioneRepository.delete(trovato);
    }


}
