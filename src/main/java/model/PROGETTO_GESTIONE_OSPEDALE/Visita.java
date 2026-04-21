package PROGETTO_GESTIONE_OSPEDALE;
import java.time.LocalDateTime;

public class Visita extends Prestazione {

    public Visita(String tipo, LocalDateTime inizio, LocalDateTime fine, String verbale){
        super(inizio, fine, verbale);
    }
}
