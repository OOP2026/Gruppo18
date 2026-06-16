package model.PROGETTO_GESTIONE_OSPEDALE;

public class Amministratore extends Utente {

    public Amministratore(String username, String password, String nome, String cognome) {
        super(username, password, nome, cognome);
    }

    public boolean iniziaRicovero(String numNosologico, String numLetto) {
        // MOCK: In attesa del DAO, restituisce sempre true per far procedere la GUI
        return true;
    }

    public boolean assegnaPaziente(String nome, String cognome, String numNosologico) {
        // MOCK: Restituisce sempre true
        return true;
    }

    public String visualizzaAnagraficaPaziente(String numNosologico) {
        // MOCK: Stringa fittizia per testare la View
        return "Dati anagrafici mockati per il paziente: " + numNosologico;
    }
}