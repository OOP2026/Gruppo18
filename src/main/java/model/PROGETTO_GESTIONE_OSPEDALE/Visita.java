package model.PROGETTO_GESTIONE_OSPEDALE;

import java.time.LocalDateTime;

public class Visita extends Prestazione {

    public Visita(String tipo, LocalDateTime inizio, LocalDateTime fine, String verbale) {
        // Passa i 4 parametri al costruttore della classe padre (Prestazione)
        super(tipo, inizio, fine, verbale);
    }
}