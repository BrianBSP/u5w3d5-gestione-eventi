package brianpelinku.u5w3d5_gestione_eventi.payloads;

import jakarta.validation.constraints.NotNull;

public record NewPrenotazioneDTO(
        String eventoPrenotato,
        @NotNull(message = "Campo obbligatorio. Indicare id utente.")
        int utenteId,
        @NotNull(message = "Campo obbligatorio. Indicare id evento.")
        int eventoId
) {
}
