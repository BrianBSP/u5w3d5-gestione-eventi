package brianpelinku.u5w3d5_gestione_eventi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUtenteDTO(

        @NotEmpty(message = "Il nome è obbligatorio")
        @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 3 30 caratteri")
        String nome,
        @NotEmpty(message = "Il cognome è obbligatorio")
        @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 3 30 caratteri")
        String cognome,
        @Email(message = "L'email inserita non è valida.")
        @NotEmpty(message = "Email Obbligatoria.")
        String email,
        @NotEmpty(message = "Inserisci una password.")
        String password,

        @NotEmpty(message = "Campo obbligatorio. Inserisci un ruolo")
        String ruolo
) {
}
