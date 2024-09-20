package brianpelinku.u5w3d5_gestione_eventi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewEventoDTO(
        @NotEmpty(message = "Campo obbligatorio. Inserire titolo")
        String titolo,

        @NotEmpty(message = "Campo obbligatorio. Inserire descrizione")
        String descrizione,

        @NotEmpty(message = "Campo obbligatorio. Inserire la data dell'evento")
        String dataEvento,

        @NotEmpty(message = "Campo obbligatorio. Inserire luogo")
        String luogo,

        @NotNull(message = "Campo obbligatorio. Inserire posti disponibili")
        int numeroPostiDisponibili,

        @NotNull(message = "Campo obbligatorio. Inserire id dell'organizzatore")
        int organizzatoreId

) {
}
