package project.model;

import java.time.LocalDateTime;
import java.util.Locale;

public class Ricovero {

    private String tipo_ricovero;
    private LocalDateTime data_inizio;
    private LocalDateTime data_fine_prevista;
    private LocalDateTime data_fine_effettiva;

    public String getTipo_ricovero() {
        return tipo_ricovero;
    }

    public void setTipo_ricovero(String tipo_ricovero) {
        this.tipo_ricovero = tipo_ricovero;
    }

    public LocalDateTime getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(LocalDateTime data_inizio) {
        this.data_inizio = data_inizio;
    }

    public LocalDateTime getData_fine_prevista() {
        return data_fine_prevista;
    }

    public void setData_fine_prevista(LocalDateTime data_fine_prevista) {
        this.data_fine_prevista = data_fine_prevista;
    }

    public LocalDateTime getData_fine_effettiva() {
        return data_fine_effettiva;
    }

    public void setData_fine_effettiva(LocalDateTime data_fine_effettiva) {
        this.data_fine_effettiva = data_fine_effettiva;
    }

    public Ricovero(String tipo, LocalDateTime inizio, LocalDateTime fine_prevista, LocalDateTime fine_effettiva){
        tipo_ricovero = tipo;
        data_inizio = inizio;
        data_fine_prevista = fine_prevista;
        data_fine_effettiva = fine_effettiva;
    }

}
