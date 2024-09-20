package brianpelinku.u5w3d5_gestione_eventi.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(int id) {
        super("L'elemento con id " + id + " non è stato trovato.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
