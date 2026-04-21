package PROGETTO_GESTIONE_OSPEDALE;

import java.time.LocalDateTime;
import java.util.Locale;

public class Ricovero {

    private String tipo_ricovero;
    private LocalDateTime data_inizio;
    private LocalDateTime data_fine_prevista;
    private LocalDateTime data_fine_effettiva;

    public Ricovero(String tipo, LocalDateTime inizio, LocalDateTime fine_prevista, LocalDateTime fine_effettiva){
        tipo_ricovero = tipo;
        data_inizio = inizio;
        data_fine_prevista = fine_prevista;
        data_fine_effettiva = fine_effettiva;
    }

}
