package controller;

import dao.*;
import implementazioneDao.*;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Controller {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    // Componenti di accesso ai dati (DAO)
    private final AmministratoreDAO amministratoreDAO;
    private final MedicoDAO medicoDAO;
    private final PazienteDAO pazienteDAO;
    private final RicoveroDAO ricoveroDAO;
    private final PrestazioneDAO prestazioneDAO;
    private final TurnoDAO turnoDAO;

    // Variabile di stato per mantenere in memoria l'utente attualmente loggato
    private String utenteLoggatoEmail;

    public Controller() {
        // Inizializzazione delle istanze DAO reali basate su PostgreSQL
        this.amministratoreDAO = new AmministratoreDAOImpl();
        this.medicoDAO = new MedicoDAOImpl();
        this.pazienteDAO = new PazienteDAOImpl();
        this.ricoveroDAO = new RicoveroDAOImpl();
        this.prestazioneDAO = new PrestazioneDAOImpl();
        this.turnoDAO = new TurnoDAOImpl();
    }

    public String effettuaLogin(String user, String pass) {
        if (amministratoreDAO.verificaCredenziali(user, pass)) {
            utenteLoggatoEmail = user;
            LOGGER.log(Level.INFO, "Login eseguito. Ruolo: Amministratore. Utente: {0}", user);
            return "ADMIN";
        } else if (medicoDAO.verificaCredenziali(user, pass)) {
            utenteLoggatoEmail = user;
            LOGGER.log(Level.INFO, "Login eseguito. Ruolo: Medico. Utente: {0}", user);
            return "MEDICO";
        }
        return "ERROR";
    }

    public void eseguiLogout() {
        LOGGER.info("Logout effettuato. Ritorno alla schermata di Login.");
        utenteLoggatoEmail = null;
    }

    // --- METODI LOGICI PER ADMIN ---

    public String trovaCodiceFiscale(String nosologico) {
        return pazienteDAO.trovaCodiceFiscaleDaNosologico(nosologico);
    }

    public boolean haRicoveroAttivo(String cf) {
        return ricoveroDAO.haRicoveroAttivo(cf);
    }

    public boolean iniziaRicovero(String cf, String letto, String dataFinePrevista) {
        return ricoveroDAO.iniziaRicovero(cf, letto, utenteLoggatoEmail, dataFinePrevista);
    }

    public boolean registraNuovoPaziente(String nome, String cognome, String nos, String cf, String data, String gruppoSangue) {
        // Usiamo l'interfaccia aggiornata del DAO che riceve i tipi primitivi
        return pazienteDAO.inserisciPaziente(cf, nos, nome, cognome, data, gruppoSangue);
    }

    public String visualizzaAnagrafica() {
        return pazienteDAO.recuperaTuttaAnagrafica();
    }

    public boolean aggiungiTurno(String email, String dataTurno, String oraInizio, String oraFine) {
        return turnoDAO.inserisciTurnoMedico(email, dataTurno, oraInizio, oraFine);
    }

    // --- METODI LOGICI PER MEDICO ---

    public String visualizzaAgenda() {
        return turnoDAO.recuperaAgendaMedico(utenteLoggatoEmail);
    }

    public boolean verbalizzaPrestazione(String pres, String verb, String dataFinePrestazione, String tipoPrestazione) {
        return prestazioneDAO.aggiungiVerbale(pres, verb, dataFinePrestazione, tipoPrestazione);
    }

    public boolean pianificaPrestazione(String data, String ricovero, String tipo) {
        return prestazioneDAO.inserisciNuovaPrestazione(data, ricovero, tipo, utenteLoggatoEmail);
    }

    public boolean aggiornaVerbale(String pres, String nuovoVerbale) {
        return prestazioneDAO.aggiornaVerbale(pres, nuovoVerbale);
    }

    public boolean dimettiPaziente(String nosologico, String dataEffettiva) {
        String codiceFiscale = pazienteDAO.trovaCodiceFiscaleDaNosologico(nosologico);
        if (codiceFiscale == null) {
            return false; // Paziente non trovato
        }
        return ricoveroDAO.dimissioneRicovero(codiceFiscale, dataEffettiva);
    }
}