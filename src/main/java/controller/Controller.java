package controller;

import gui.*;
import model.PROGETTO_GESTIONE_OSPEDALE.*;
import dao.*;
import implementazioneDao.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class Controller {

    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    // Finestre (View)
    private final LoginWindow loginWindow;
    private final AdminWindow adminWindow;
    private final MedicoWindow medicoWindow;

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
        // Inizializzazione delle Finestre
        this.loginWindow = new LoginWindow();
        this.adminWindow = new AdminWindow();
        this.medicoWindow = new MedicoWindow();

        // Inizializzazione delle istanze DAO reali basate su PostgreSQL
        this.amministratoreDAO = new AmministratoreDAOImpl();
        this.medicoDAO = new MedicoDAOImpl();
        this.pazienteDAO = new PazienteDAOImpl();
        this.ricoveroDAO = new RicoveroDAOImpl();
        this.prestazioneDAO = new PrestazioneDAOImpl();
        this.turnoDAO = new TurnoDAOImpl();

        // Configurazione iniziale dei pannelli a schede nelle finestre
        if (adminWindow.getTabbedPane() != null) {
            adminWindow.getTabbedPane().setSelectedIndex(0);
        }
        if (medicoWindow.getTabbedPane1() != null) {
            medicoWindow.getTabbedPane1().setSelectedIndex(0);
        }

        // Collegamento dei Listener agli elementi grafici
        inizializzaEventiLogin();
        inizializzaEventiAdmin();
        inizializzaEventiMedico();

        // Apertura della schermata iniziale dell'applicazione
        loginWindow.setVisible(true);
    }

    private void eseguiLogout(JFrame finestraAttuale) {
        LOGGER.info("Logout effettuato. Ritorno alla schermata di Login.");
        utenteLoggatoEmail = null;
        finestraAttuale.setVisible(false);
        loginWindow.setVisible(true);
    }

    private void inizializzaEventiLogin() {
        loginWindow.getBtnAccedi().addActionListener(e -> {
            String user = loginWindow.getUsername();
            String pass = loginWindow.getPassword();

            if (user.isEmpty() || pass.isEmpty()) {
                loginWindow.mostraErrore("Inserisci username e password!");
                return;
            }

            // Verifica delle credenziali mediante i rispettivi DAO sul database
            if (amministratoreDAO.verificaCredenziali(user, pass)) {
                utenteLoggatoEmail = user;
                LOGGER.info("Login eseguito. Ruolo: Amministratore. Utente: " + user);
                loginWindow.setVisible(false);
                adminWindow.setVisible(true);
            } else if (medicoDAO.verificaCredenziali(user, pass)) {
                utenteLoggatoEmail = user;
                LOGGER.info("Login eseguito. Ruolo: Medico. Utente: " + user);
                loginWindow.setVisible(false);
                medicoWindow.setVisible(true);
            } else {
                loginWindow.mostraErrore("Credenziali errate o non presenti nel database.");
            }
        });
    }

    private void inizializzaEventiAdmin() {
        // Gestione dei pulsanti di navigazione del menu principale amministratore
        adminWindow.getBtnIniziaRicovero().addActionListener(e -> adminWindow.getTabbedPane().setSelectedIndex(1));
        adminWindow.getBtnAssegnaPaziente().addActionListener(e -> adminWindow.getTabbedPane().setSelectedIndex(2));
        adminWindow.getBtnVisualizzaAnagrafica().addActionListener(e -> adminWindow.getTabbedPane().setSelectedIndex(3));
        adminWindow.getTurnoButton().addActionListener(e -> adminWindow.getTabbedPane().setSelectedIndex(4));
        adminWindow.getBtnLogout().addActionListener(e -> eseguiLogout(adminWindow));

        // Gestione pulsanti di ritorno al menu principale
        ActionListener tornaAlMenuAdmin = e -> {
            adminWindow.svuotaCampi();
            adminWindow.getTabbedPane().setSelectedIndex(0);
        };
        adminWindow.getINDIETROButton().addActionListener(tornaAlMenuAdmin);
        adminWindow.getINDIETROButton1().addActionListener(tornaAlMenuAdmin);
        adminWindow.getINDIETROButton2().addActionListener(tornaAlMenuAdmin);
        adminWindow.getIndietro4Button().addActionListener(tornaAlMenuAdmin);
        // Operazione di avvio di un nuovo Ricovero
        adminWindow.getINIZIARICOVEROButton().addActionListener(e -> {
            String nosologico = adminWindow.getNumNosologicoRicovero();
            String letto = adminWindow.getNumLetto();
            // 1. Troviamo il codice fiscale
            String cfcontrollo = pazienteDAO.trovaCodiceFiscaleDaNosologico(nosologico);
            String datafineprevista = adminWindow.getDataDimissionePrevista();

            if (cfcontrollo == null) {
                JOptionPane.showMessageDialog(adminWindow, "Nessun paziente trovato con questo nosologico!", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (ricoveroDAO.haRicoveroAttivo(cfcontrollo)) {
                JOptionPane.showMessageDialog(adminWindow,
                        "Impossibile procedere: Il paziente risulta già ricoverato in un altro letto!",
                        "Paziente già ricoverato",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (nosologico.isEmpty() || letto.isEmpty()) {
                LOGGER.severe("Impossibile procedere: i campi nosologico e letto sono obbligatori.");
                return;
            }

            // 3. Se supera i controlli, salva il ricovero
            boolean successo = ricoveroDAO.iniziaRicovero(cfcontrollo, letto, utenteLoggatoEmail,datafineprevista);

            if (successo) {
                LOGGER.info("Ricovero registrato correttamente per il paziente con codice fiscale: " + cfcontrollo);
                adminWindow.svuotaCampi();
                adminWindow.getTabbedPane().setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore nell'esecuzione dell'inserimento del ricovero nel database.");
            }
        });

        // Operazione di registrazione di un nuovo Paziente in anagrafica
        adminWindow.getASSEGNAPAZIENTEButton().addActionListener(e -> {
            String nome = adminWindow.getNomePaziente();
            String cognome = adminWindow.getCognomePaziente();
            String nos = adminWindow.getNumNosologicoNuovo();
            String cf = adminWindow.getCodiceFiscale();
            String data = adminWindow.getDataNascita();
            String gruppoSangue = adminWindow.getGruppoSanguigno(); // Leggi il nuovo campo


            if (nome.isEmpty() || cognome.isEmpty() || nos.isEmpty() || cf.isEmpty() || data.isEmpty() || gruppoSangue.isEmpty()) {
                JOptionPane.showMessageDialog(adminWindow, "Attenzione: devi compilare tutti i campi", "Campi Incompleti", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Paziente nuovoPaziente = new Paziente(nome, cognome, nos, gruppoSangue);
            nuovoPaziente.setCodiceFiscale(cf);
            nuovoPaziente.setDataNascita(data);


            boolean successo = pazienteDAO.inserisciPaziente(nuovoPaziente);

            if (successo) {
                LOGGER.info("Anagrafica salvata. Paziente registrato: " + nome + " " + cognome);
                adminWindow.svuotaCampi();
                adminWindow.getTabbedPane().setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore durante il salvataggio dei dati anagrafici del paziente.");
            }
        });

        // Navigazione e caricamento Anagrafica
        adminWindow.getBtnVisualizzaAnagrafica().addActionListener(e -> {
            // 1. Chiede al DAO di interrogare PostgreSQL
            String datiAnagrafica = pazienteDAO.recuperaTuttaAnagrafica();

            // 2. Inserisce il testo estratto nella JTextArea
            adminWindow.setTestoAnagrafica(datiAnagrafica);

            // 3. Sposta la visuale dell'Amministratore sulla scheda giusta
            adminWindow.getTabbedPane().setSelectedIndex(3);
        });

        // Aggiunta turni medico
        adminWindow.getAggiungiOrarioButton().addActionListener(e -> {
            String email = adminWindow.getEmailMedico();
            String dataTurno = adminWindow.getDataTurno();
            String oraInizio = adminWindow.getOraInizio();
            String oraFine = adminWindow.getOraFine();
            boolean successo = turnoDAO.inserisciTurnoMedico(email, dataTurno, oraInizio, oraFine);
            if (successo) {
                LOGGER.info("Turno salvato per medico: " + email);
                adminWindow.svuotaCampi();
                adminWindow.getTabbedPane().setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore durante il salvataggio del turno.");
            }
        });

    }

    private void inizializzaEventiMedico() {
        // Gestione dei pulsanti di navigazione del menu principale medico
        medicoWindow.getBtnEseguiPrestazione().addActionListener(e -> medicoWindow.getTabbedPane1().setSelectedIndex(1));
        medicoWindow.getBtnPianificaPrestazione().addActionListener(e -> medicoWindow.getTabbedPane1().setSelectedIndex(2));
        medicoWindow.getBtnModificaVerbale().addActionListener(e -> medicoWindow.getTabbedPane1().setSelectedIndex(3));
        medicoWindow.getDIMETTIPAZIENTEButton().addActionListener(e -> medicoWindow.getTabbedPane1().setSelectedIndex(5));
        // Visualizzazione dei turni lavorativi nell'agenda
        medicoWindow.getBtnVisualizzaAgenda().addActionListener(e -> {
            String agenda = turnoDAO.recuperaAgendaMedico(utenteLoggatoEmail);
            medicoWindow.setTestoAgenda(agenda);
            medicoWindow.getTabbedPane1().setSelectedIndex(4);
        });

        medicoWindow.getBtnLogout().addActionListener(e -> eseguiLogout(medicoWindow));

        ActionListener tornaAlMenuMedico = e -> {
            medicoWindow.svuotaCampi();
            medicoWindow.getTabbedPane1().setSelectedIndex(0);
        };
        medicoWindow.getINDIETROButton().addActionListener(tornaAlMenuMedico);
        medicoWindow.getINDIETROButton1().addActionListener(tornaAlMenuMedico);
        medicoWindow.getINDIETROButton2().addActionListener(tornaAlMenuMedico);
        medicoWindow.getINDIETROButton3().addActionListener(tornaAlMenuMedico);
        medicoWindow.getIndietroButton5().addActionListener(tornaAlMenuMedico);

        // Operazione di verbalizzazione di una prestazione medica esistente
        medicoWindow.getCONFERMAButton().addActionListener(e -> {
            String pres = medicoWindow.getPrestazioneEseguita();
            String verb = medicoWindow.getVerbale();
            String dataFinePrestazione = medicoWindow.getcampoOraFinePrestazione();
            String Tipoprestazione = medicoWindow.getcampoTipoPrestazione();

            if (pres.isEmpty() || verb.isEmpty() || dataFinePrestazione.isEmpty()) {
                LOGGER.severe("Impossibile procedere: codice prestazione, tipologia, verbale e la data di fine prestazione sono campi richiesti.");
                return;
            }


            boolean successo = prestazioneDAO.aggiungiVerbale(pres, verb, dataFinePrestazione, Tipoprestazione);
            if (successo) {
                LOGGER.info("verbale e ora fine prestazione aggiunte alla prestazione " + pres + " registrata con successo.");
                medicoWindow.svuotaCampi();
                medicoWindow.getTabbedPane1().setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore nel processo di registrazione della prestazione.");
            }
        });

        // Operazione di pianificazione temporale di una nuova prestazione
        medicoWindow.getPIANIFICAPRESTAZIONEButton().addActionListener(e -> {
            String data = medicoWindow.getDataInizioPrestazione();
            String ricovero = medicoWindow.getRicovero();
            String tipo = medicoWindow.getDescrizioneTipo();
            String EmailMedico = utenteLoggatoEmail;

            if (data.isEmpty() || ricovero.isEmpty()) {
                LOGGER.severe("Impossibile procedere: specificare la data e l'identificativo del ricovero.");
                return;
            }

            boolean successo = prestazioneDAO.inserisciNuovaPrestazione(data, ricovero, tipo, EmailMedico);

            if (successo) {
                LOGGER.info("Nuova prestazione pianificata correttamente per la data indicata.");
                medicoWindow.svuotaCampi();
                medicoWindow.getTabbedPane1().setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore nell'inserimento della pianificazione della prestazione.");
            }
        });

        // Operazione di modifica di un verbale preesistente
        medicoWindow.getMODIFICAButton().addActionListener(e -> {
            String pres = medicoWindow.getPrestazioneDaModificare();
            String nuovoVerbale = medicoWindow.getNuovoVerbale();

            if (pres.isEmpty() || nuovoVerbale.isEmpty()) {
                LOGGER.severe("Impossibile procedere: i campi codice prestazione e testo verbale sono richiesti.");
                return;
            }

            boolean successo = prestazioneDAO.aggiornaVerbale(pres, nuovoVerbale);

            if (successo) {
                LOGGER.info("Il verbale relativo alla prestazione " + pres + " è stato modificato con successo.");
                medicoWindow.svuotaCampi();
                medicoWindow.getTabbedPane1().setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore nella modifica dei dati del verbale clinico.");
            }
        });


        medicoWindow.getDimettiButton().addActionListener(e -> {

            String nosologico = medicoWindow.getCampoNosologicoDimissione();
            String dataEffettiva = medicoWindow.getCampoDataDimissione();

            if (nosologico.isEmpty() || dataEffettiva.isEmpty()) {
                JOptionPane.showMessageDialog(medicoWindow, "Inserisci il numero nosologico e la data di dimissione.", "fallimento", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String codiceFiscale = pazienteDAO.trovaCodiceFiscaleDaNosologico(nosologico);

            // CONTROLLO FONDAMENTALE: se non ha trovato il paziente, cf è null!
            if (codiceFiscale == null) {
                JOptionPane.showMessageDialog(medicoWindow, "Nessun paziente trovato con questo numero nosologico.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean successo = ricoveroDAO.dimissioneRicovero(codiceFiscale, dataEffettiva);

            if (successo) {
                LOGGER.info("Paziente dimesso con successo: " + codiceFiscale);
                JOptionPane.showMessageDialog(medicoWindow, "Paziente dimesso con successo.");
                medicoWindow.svuotaCampi();
                medicoWindow.getTabbedPane1().setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore durante la dimissione del paziente.");
                JOptionPane.showMessageDialog(medicoWindow, "Errore: Paziente non trovato o non attualmente ricoverato.", "Errore Dimissione", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}