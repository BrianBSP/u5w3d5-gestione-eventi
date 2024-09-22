package brianpelinku.u5w3d5_gestione_eventi.controllers;

import brianpelinku.u5w3d5_gestione_eventi.entities.Utente;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewUtenteRespDTO;
import brianpelinku.u5w3d5_gestione_eventi.services.EventiService;
import brianpelinku.u5w3d5_gestione_eventi.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utenti")
public class UtentiController {

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private EventiService eventiService;

    // GET All
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')") // solo gli admin possono leggere l'elenco degli utenti
    public Page<Utente> getAllUtenti(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.utentiService.findAll(page, size, sortBy);
    }

    // GET byId
    @GetMapping("/{utenteId}")
    public NewUtenteRespDTO findUtenteById(@PathVariable int utenteId) {
        return new NewUtenteRespDTO(this.utentiService.findById(utenteId).getId());
    }

    // DELETE
    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')") // solo gli admin possono elimini gli utenti
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int utenteId) {
        utentiService.findByIdAndDelete(utenteId);
    }

    // dopo aver effettuato il login posso vedere il mio profilo
    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente utenteAutenticato) {
        return utenteAutenticato;
    }

    // dopo aver effettuato il login posso eliminare il mio profilo
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente utenteAutenticato) {
        this.utentiService.findByIdAndDelete(utenteAutenticato.getId());
    }

    /*@PostMapping("/me/eventi")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEventoRespDTO saveEvento(@AuthenticationPrincipal @RequestBody @Validated Utente utenteAutenticato, NewEventoDTO evento, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation
                    .getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Segnalazione Errori nel Payload. " + messages);
        } else {


            return new NewEventoRespDTO(this.eventiService.saveEvento(evento).eventoId());
        }
    }*/

}
