package project.model;

import java.time.LocalDateTime;

public class Intervento extends Prestazione {

    // Variabile esclusiva solo per gli interventi
    private String tipoIntervento;

    public Intervento(String tipo, LocalDateTime inizio, LocalDateTime fine, String verbale, String tipoIntervento) {
        // Passa i primi 4 parametri al padre
        super(tipo, inizio, fine, verbale);

        // Salva l'ultimo parametro localmente
        this.tipoIntervento = tipoIntervento;
    }

    // --- GETTER E SETTER SPECIFICI ---

    public String getTipoIntervento() {
        return tipoIntervento;
    }

    public void setTipoIntervento(String tipoIntervento) {
        this.tipoIntervento = tipoIntervento;
    }
}