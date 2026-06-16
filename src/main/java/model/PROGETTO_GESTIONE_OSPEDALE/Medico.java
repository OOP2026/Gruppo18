package model.PROGETTO_GESTIONE_OSPEDALE;

public class Medico extends Utente {

    private int num_telefono;
    private String email;

    public Medico(String username, String password, String nome, String cognome) {
        super(username, password, nome, cognome);
    }

    public boolean eseguiPrestazione(String codicePrestazione, String verbale, String tipo) {
        // MOCK: Restituisce sempre true
        return true;
    }

    public boolean pianificaPrestazione(String data, String codiceRicovero) {
        // MOCK: Restituisce sempre true
        return true;
    }

    public boolean modificaVerbale(String codicePrestazione, String nuovoVerbale) {
        // MOCK: Restituisce sempre true
        return true;
    }

    public String recuperaTurniAgenda() {
        // MOCK: Restituisce una stringa precompilata da mostrare nella GUI
        return "=== AGENDA DEL MEDICO (Modalità Test) ===\n" +
                "- Nessun dato dal database (DAO non ancora implementato).";
    }
}