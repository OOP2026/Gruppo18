package model.PROGETTO_GESTIONE_OSPEDALE;

public class Stanza {

    private int num_stanza;
    private int capienza_stanza;

    public int getNum_stanza() {
        return num_stanza;
    }

    public void setNum_stanza(int num_stanza) {
        this.num_stanza = num_stanza;
    }

    public int getCapienza_stanza() {
        return capienza_stanza;
    }

    public void setCapienza_stanza(int capienza_stanza) {
        this.capienza_stanza = capienza_stanza;
    }

    public Stanza(int num_stanza, int capienza){
        this.num_stanza = num_stanza;
        capienza_stanza = capienza;
    }

}
