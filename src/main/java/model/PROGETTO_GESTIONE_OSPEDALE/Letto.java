package PROGETTO_GESTIONE_OSPEDALE;

public class Letto {

    private int num_letto;
    boolean occupato;

    public Letto(int ID, boolean occupato){
        num_letto = ID;
        this.occupato = occupato;
    }
}
