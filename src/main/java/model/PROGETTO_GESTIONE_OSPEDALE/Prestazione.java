package PROGETTO_GESTIONE_OSPEDALE;
import java.time.LocalDateTime;

public class Prestazione {

    private LocalDateTime inizio_prestazione;
    private LocalDateTime fine_prestazione;
    String verbale_prestazione;

    public Prestazione(LocalDateTime inizio, LocalDateTime fine, String verbale){
        inizio_prestazione = inizio;
        fine_prestazione = fine;
        verbale_prestazione = verbale;
    }

}
