package brianpelinku.u5w3d5_gestione_eventi.controllers;

import brianpelinku.u5w3d5_gestione_eventi.entities.Prenotazioni;
import brianpelinku.u5w3d5_gestione_eventi.exceptions.BadRequestException;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewPrenotazioneDTO;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewPrenotazioneRespDTO;
import brianpelinku.u5w3d5_gestione_eventi.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewPrenotazioneRespDTO savePrenotazione(@RequestBody @Validated NewPrenotazioneDTO prenotazione, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Segnalazione Errori nel Payload. " + messages);
        } else {
            return new NewPrenotazioneRespDTO(this.prenotazioniService.savePrenotazione(prenotazione).prenotazioneId());
        }
    }

    @GetMapping
    public Page<Prenotazioni> getAllPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size,
                                                 @RequestParam(defaultValue = "id") String sortBy) {
        return this.prenotazioniService.findAll(page, size, sortBy);
    }

    @GetMapping("/{prenotazioneId}")
    public NewPrenotazioneRespDTO findPrenotazioneById(@PathVariable int prenotazioneId) {
        return new NewPrenotazioneRespDTO(this.prenotazioniService.findById(prenotazioneId).getId());
    }

    @DeleteMapping("/{prenotazioniId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int prenotazioniId) {
        prenotazioniService.findByIdAndDelete(prenotazioniId);
    }
}
