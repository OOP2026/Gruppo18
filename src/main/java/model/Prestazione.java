package model;

import java.time.LocalDateTime;

public abstract class Prestazione {

    private String tipo;
    private LocalDateTime inizio;
    private LocalDateTime fine;
    private String verbale;

    // Costruttore vuoto di default
    protected Prestazione() {
    }

    // Costruttore completo
    protected Prestazione(String tipo, LocalDateTime inizio, LocalDateTime fine, String verbale) {
        this.tipo = tipo;
        this.inizio = inizio;
        this.fine = fine;
        this.verbale = verbale;
    }

    // --- GETTER E SETTER ---

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getVerbale() {
        return verbale;
    }

    public void setVerbale(String verbale) {
        this.verbale = verbale;
    }
}