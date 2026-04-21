package PROGETTO_GESTIONE_OSPEDALE;

public class Amministratore extends Utente{

    public Amministratore(String username, String password, String nome, String cognome){
        super(username, password, nome, cognome);
    }

    void iniziaRicovero(){}

    void assegnaPaziente(){}

    void visualizzaAnagraficaPaziente(Paziente paziente){}

}
