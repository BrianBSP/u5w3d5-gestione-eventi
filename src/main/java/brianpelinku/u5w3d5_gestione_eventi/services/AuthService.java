package brianpelinku.u5w3d5_gestione_eventi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UtentiService utentiService;
}
