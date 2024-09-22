package brianpelinku.u5w3d5_gestione_eventi.services;

import brianpelinku.u5w3d5_gestione_eventi.entities.Evento;
import brianpelinku.u5w3d5_gestione_eventi.entities.Utente;
import brianpelinku.u5w3d5_gestione_eventi.exceptions.BadRequestException;
import brianpelinku.u5w3d5_gestione_eventi.exceptions.NotFoundException;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewEventoDTO;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewEventoRespDTO;
import brianpelinku.u5w3d5_gestione_eventi.repositories.EventoRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EventiService {

    @Autowired
    private EventoRepositoy eventoRepositoy;

    @Autowired
    private UtentiService utentiService;

    public NewEventoRespDTO saveEvento(NewEventoDTO body, Utente utenteCorrente) {

        LocalDate dataEvento = null;
        try {
            dataEvento = LocalDate.parse(body.dataEvento());
        } catch (Exception e) {
            throw new BadRequestException("Errore. Formato data richiesto: YYYY-MM-DD");
        }

        if (this.eventoRepositoy.existsByLuogoAndDataEvento(body.luogo(), dataEvento))
            throw new BadRequestException("Evento già presente a " + body.luogo() + " il giorno " + body.dataEvento());

        Evento newEvento = new Evento();
        newEvento.setTitolo(body.titolo());
        newEvento.setDescrizione(body.descrizione());
        newEvento.setDataEvento(LocalDate.parse(body.dataEvento()));
        newEvento.setNumeroPostiDisponibili(body.numeroPostiDisponibili());
        newEvento.setOrganizzatoreId(utenteCorrente);


        // salvo il nuovo record
        return new NewEventoRespDTO(this.eventoRepositoy.save(newEvento).getId());
    }

    // cerco tutti gli evento
    public Page<Evento> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventoRepositoy.findAll(pageable);
    }

    // cerco evento byId
    public Evento findById(int eventoId) {
        return this.eventoRepositoy.findById(eventoId).orElseThrow(() -> new NotFoundException(eventoId));
    }

    // delete evento
    public void findByIdAndDelete(int eventoId) {
        Evento trovato = this.findById(eventoId);
        this.eventoRepositoy.delete(trovato);
    }
}
