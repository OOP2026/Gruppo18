package PROGETTO_GESTIONE_OSPEDALE;

public class Paziente {

    private int num_nosologico;
    private String nome_paziente;
    private String cognome_paziente;

    public Paziente(String nome_paziente, String cognome_paziente, int num_nosologico){
        this.nome_paziente = nome_paziente;
        this.cognome_paziente = cognome_paziente;
        this.num_nosologico = num_nosologico;
    }
}
