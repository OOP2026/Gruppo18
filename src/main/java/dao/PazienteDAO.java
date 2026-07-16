package dao;

public interface PazienteDAO {

    // Niente più oggetto Paziente. Passiamo direttamente i dati primitivi.
    boolean inserisciPaziente(String codiceFiscale, String numNosologico, String nome, String cognome, String dataNascita, String gruppoSanguigno);

    // Restituisce un array di stringhe invece dell'oggetto Paziente
    String[] trovaPazientePerNosologico(String numNosologico);

    String trovaCodiceFiscaleDaNosologico(String numNosologico);

    String recuperaTuttaAnagrafica();
}