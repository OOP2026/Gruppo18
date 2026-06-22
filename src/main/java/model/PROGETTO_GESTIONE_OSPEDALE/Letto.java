package model.PROGETTO_GESTIONE_OSPEDALE;

public class Letto {

    private int num_letto;
    boolean occupato;

    public int getNum_letto() {
        return num_letto;
    }

    public void setNum_letto(int num_letto) {
        this.num_letto = num_letto;
    }

    public boolean isOccupato() {
        return occupato;
    }

    public void setOccupato(boolean occupato) {
        this.occupato = occupato;
    }
}
