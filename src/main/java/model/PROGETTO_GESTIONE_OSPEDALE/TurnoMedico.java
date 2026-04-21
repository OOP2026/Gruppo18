package PROGETTO_GESTIONE_OSPEDALE;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TurnoMedico {

    private LocalDate data;
    private LocalDateTime inizio;
    private LocalDateTime fine;

    public TurnoMedico(LocalDate data, LocalDateTime inizio, LocalDateTime fine){
        this.data = data;
        this.inizio = inizio;
        this.fine = fine;
    }

}
