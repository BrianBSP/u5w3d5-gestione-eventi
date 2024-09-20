package brianpelinku.u5w3d5_gestione_eventi.controllers;

import brianpelinku.u5w3d5_gestione_eventi.exceptions.BadRequestException;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewUtenteDTO;
import brianpelinku.u5w3d5_gestione_eventi.payloads.NewUtenteRespDTO;
import brianpelinku.u5w3d5_gestione_eventi.payloads.UtenteLoginDTO;
import brianpelinku.u5w3d5_gestione_eventi.payloads.UtenteLoginRespDTO;
import brianpelinku.u5w3d5_gestione_eventi.services.AuthService;
import brianpelinku.u5w3d5_gestione_eventi.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private AuthService authService;


    @PostMapping("login")
    public UtenteLoginRespDTO login(@RequestBody UtenteLoginDTO body) {
        return new UtenteLoginRespDTO(this.authService.checkCredenzialiAndGeneraToken(body));
    }

    // creo un nuovo profilo per un nuovo utente
    // ognuno si può registrare
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUtenteRespDTO createUtente(@RequestBody @Validated NewUtenteDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation
                    .getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Segnalazione Errori nel Payload. " + messages);
        } else {
            return new NewUtenteRespDTO(this.utentiService.saveUtente(body).utenteId());
        }
    }

}
