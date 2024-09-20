package brianpelinku.u5w3d5_gestione_eventi.controllers;

import brianpelinku.u5w3d5_gestione_eventi.entities.Utente;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewUtenteRespDTO;
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

    // GET All
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')") // solo gli admin possono leggere l'elenco dei dipendenti
    public Page<Utente> getAllDipendenti(@RequestParam(defaultValue = "0") int page,
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
    @PreAuthorize("hasAuthority('ADMIN')") // solo gli admin possono elimini i dipendenti
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

}
