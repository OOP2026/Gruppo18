package dao;

public interface PrestazioneDAO {
    boolean aggiornaVerbale(String codicePrestazione, String nuovoVerbale);
    boolean inserisciNuovaPrestazione(String dataInizio, String codiceRicovero,String tipo,String emailMedico);
    boolean aggiungiVerbale(String codicePrestazione, String Verbale,String orafineprestazione,String TipoPrestazione);
}