package brianpelinku.u5w3d5_gestione_eventi.controllers;

import brianpelinku.u5w3d5_gestione_eventi.entities.Evento;
import brianpelinku.u5w3d5_gestione_eventi.exceptions.BadRequestException;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewEventoDTO;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewEventoRespDTO;
import brianpelinku.u5w3d5_gestione_eventi.services.EventiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventi")
public class EventiController {
    @Autowired
    private EventiService eventiService;

    // POST --> creo un nuovo record --- +body
    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEventoRespDTO saveEvento(@RequestBody @Validated NewEventoDTO viaggio, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation
                    .getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Segnalazione Errori nel Payload. " + messages);
        } else {
            return new NewEventoRespDTO(this.eventiService.saveViaggio(viaggio).eventoId());
        }
    }

    // GET All
    @GetMapping
    public Page<Evento> getAllEventi(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.eventiService.findAll(page, size, sortBy);
    }

    // GET byId
    @GetMapping("/{eventiId}")
    public NewEventoRespDTO findEventoById(@PathVariable int eventiId) {
        return new NewEventoRespDTO(this.eventiService.findById(eventiId).getId());
    }

    // DELETE
    @DeleteMapping("/{eventiId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int viaggiId) {
        eventiService.findByIdAndDelete(viaggiId);
    }
}
