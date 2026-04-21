package PROGETTO_GESTIONE_OSPEDALE;

public class Medico extends Utente {

    private int num_telefono;
    private String email;

    public Medico(String username, String password, String nome, String cognome) {
        super(username, password, nome, cognome);
    }

    void eseguiPrestazione() {
    }

    void pianificaPrestazione() {
    }

    void modificaVerbale(Prestazione verbale_prestazione){
    }

    void visualizzaAgenda(){
    }

}