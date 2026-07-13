package dao;

import model.Paziente;

/**
 * Interfaccia per la gestione dell'accesso ai dati dell'entità Paziente.
 * * @author Gruppo 18
 */
public interface PazienteDAO {

    /**
     * Inserisce un nuovo record paziente all'interno del database.
     * * @param paziente L'oggetto Paziente popolato con i dati anagrafici.
     * @return true se l'inserimento va a buon fine, false in caso di errore.
     */
    boolean inserisciPaziente(Paziente paziente);

    /**
     * Ricerca e restituisce un paziente basandosi sul suo numero nosologico univoco.
     * * @param numNosologico Il numero nosologico del paziente da cercare.
     * @return L'oggetto Paziente se trovato, altrimenti null.
     */
    Paziente trovaPazientePerNosologico(String numNosologico);

    /**
     * Estrae il Codice Fiscale di un paziente partendo dal suo numero nosologico.
     * Metodo di utilità per la gestione delle chiavi esterne nei ricoveri.
     * @return Il Codice Fiscale sotto forma di stringa, oppure null se non trovato.
     */
    String trovaCodiceFiscaleDaNosologico(String numNosologico);

    /**
     * Recupera tutti i pazienti registrati nel database e li formatta in una stringa.
     * @return Stringa formattata con l'elenco dei pazienti, pronta per la GUI.
     */
    String recuperaTuttaAnagrafica();
}