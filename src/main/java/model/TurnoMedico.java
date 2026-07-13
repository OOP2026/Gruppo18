package model;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TurnoMedico {

    private LocalDate data;
    private LocalDateTime inizio;
    private LocalDateTime fine;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getInizio() {
        return inizio;
    }

    public void setInizio(LocalDateTime inizio) {
        this.inizio = inizio;
    }

    public LocalDateTime getFine() {
        return fine;
    }

    public void setFine(LocalDateTime fine) {
        this.fine = fine;
    }

    public TurnoMedico(LocalDate data, LocalDateTime inizio, LocalDateTime fine){
        this.data = data;
        this.inizio = inizio;
        this.fine = fine;
    }

}
