package brianpelinku.u5w3d5_gestione_eventi.exceptions;

import java.time.LocalDateTime;

public record ErrorsPayload(
        String message,
        LocalDateTime timestamp
) {
}
