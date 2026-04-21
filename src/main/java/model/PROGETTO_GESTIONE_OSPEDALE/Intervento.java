package PROGETTO_GESTIONE_OSPEDALE;
import java.time.LocalDateTime;

public class Intervento extends Prestazione{

    private String tipo_intervento;
    private boolean risultato;

    public Intervento(String tipo, LocalDateTime inizio, LocalDateTime fine, String verbale){
        super(inizio, fine, verbale);
        this.tipo_intervento = tipo_intervento;
        this.risultato = risultato;
    }

}
