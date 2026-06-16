package controller;

import gui.*;
import model.PROGETTO_GESTIONE_OSPEDALE.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class Controller {
    //logger per sostituire i system out
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private static final String D_NOME = "Nome";
    private static final String D_COGNOME = "Cognome";
    //Finestre
    final LoginWindow loginWindow;
    final AdminWindow adminWindow;
    final MedicoWindow medicoWindow;

    //Variabile per mantenere in memoria l'utente loggato
    private String utenteLoggatoEmail;

    public Controller() {
        //Inizializzazione GUI
        loginWindow = new LoginWindow();
        adminWindow = new AdminWindow();
        medicoWindow = new MedicoWindow();

        //Mostra i menu principali
        adminWindow.getTabbedPane().setSelectedIndex(0);
        medicoWindow.getTabbedPane1().setSelectedIndex(0);

        inizializzaEventiLogin();
        inizializzaEventiAdmin();
        inizializzaEventiMedico();

        // Avvia l'app (mostra la schermata di Login)
        loginWindow.setVisible(true);
    }

    //Metodi di utility
    private void eseguiLogout(JFrame finestraAttuale) {
        LOGGER.info("Logout effettuato. Ritorno al Login.");
        utenteLoggatoEmail = null;

        finestraAttuale.setVisible(false);

        if (loginWindow != null) {
            loginWindow.setVisible(true);
        }
    }

    //Gestione login
    private void inizializzaEventiLogin() {
        loginWindow.getBtnAccedi().addActionListener(e -> {
            String user = loginWindow.getUsername();
            String pass = loginWindow.getPassword();

            if (user.isEmpty() || pass.isEmpty()) {
                loginWindow.mostraErrore("Inserisci username e password!");
                return;
            }

            //Qua si dovrà sostituire con una verifica query del DB
            if (user.equals("admin")) {
                utenteLoggatoEmail = user;
                LOGGER.info("Login Amministratore effettuato.");
                loginWindow.setVisible(false);
                adminWindow.setVisible(true);
            } else if (user.equals("medico")) {
                utenteLoggatoEmail = user;
                LOGGER.info("Login Medico effettuato.");
                loginWindow.setVisible(false);
                medicoWindow.setVisible(true);
            } else {
                loginWindow.mostraErrore("Credenziali errate (usa 'admin' o 'medico').");
            }
        });
    }

    //Gestione degli eventi della schermata ADMIN
    private void inizializzaEventiAdmin() {

        // Navigazione menù
        adminWindow.getBtnIniziaRicovero().addActionListener(e -> adminWindow.getTabbedPane().setSelectedIndex(1));
        adminWindow.getBtnAssegnaPaziente().addActionListener(e -> adminWindow.getTabbedPane().setSelectedIndex(2));
        adminWindow.getBtnVisualizzaAnagrafica().addActionListener(e -> adminWindow.getTabbedPane().setSelectedIndex(3));

        // Logout
        adminWindow.getBtnLogout().addActionListener(e -> eseguiLogout(adminWindow));

        // Tutti i tasti per tornare indietro al menu'
        ActionListener tornaAlMenuAdmin = e -> {
            adminWindow.svuotaCampi();
            adminWindow.getTabbedPane().setSelectedIndex(0);
        };
        adminWindow.getINDIETROButton().addActionListener(tornaAlMenuAdmin);
        adminWindow.getINDIETROButton1().addActionListener(tornaAlMenuAdmin);
        adminWindow.getINDIETROButton2().addActionListener(tornaAlMenuAdmin);

        // Conferma Ricovero
        adminWindow.getINIZIARICOVEROButton().addActionListener(e -> {
            String nosologico = adminWindow.getNumNosologicoRicovero();
            String letto = adminWindow.getNumLetto();

            if (nosologico.isEmpty() || letto.isEmpty()) {
                LOGGER.severe("ERRORE: Compila Nosologico e Letto per il ricovero.");
                return;
            }

                Amministratore admin = new Amministratore(utenteLoggatoEmail, "", D_NOME, D_COGNOME);
            boolean successo = admin.iniziaRicovero(nosologico, letto);

            if (successo) {
                LOGGER.info("RICOVERO: Paziente " + nosologico + " assegnato a letto " + letto);
                adminWindow.svuotaCampi();
                adminWindow.getTabbedPane().setSelectedIndex(0);
            } else {
                LOGGER.severe("ERRORE: Fallita creazione ricovero.");
            }
        });

        // Conferma assegna paziente
        adminWindow.getASSEGNAPAZIENTEButton().addActionListener(e -> {
            String nome = adminWindow.getNomePaziente();
            String cognome = adminWindow.getCognomePaziente();
            String nos = adminWindow.getNumNosologicoNuovo();

            if (nome.isEmpty() || cognome.isEmpty() || nos.isEmpty()) {
                LOGGER.severe("ERRORE: Compila tutti i campi dell'anagrafica.");
                return;
            }

            Amministratore admin = new Amministratore(utenteLoggatoEmail, "", D_NOME, D_COGNOME);
            boolean successo = admin.assegnaPaziente(nome, cognome, nos);

            if (successo) {
                LOGGER.info("ANAGRAFICA: Paziente " + nome + " " + cognome + " registrato.");
                adminWindow.svuotaCampi();
                adminWindow.getTabbedPane().setSelectedIndex(0);
            } else {
                LOGGER.severe("ERRORE: Registrazione anagrafica fallita.");
            }
        });
    }

    //Gestione della scheda degli eventi MEDICO
    private void inizializzaEventiMedico() {

        // Navigazione del menu'
        medicoWindow.getBtnEseguiPrestazione().addActionListener(e -> medicoWindow.getTabbedPane1().setSelectedIndex(1));
        medicoWindow.getBtnPianificaPrestazione().addActionListener(e -> medicoWindow.getTabbedPane1().setSelectedIndex(2));
        medicoWindow.getBtnModificaVerbale().addActionListener(e -> medicoWindow.getTabbedPane1().setSelectedIndex(3));

        // Visualizza agenda
        medicoWindow.getBtnVisualizzaAgenda().addActionListener(e -> {
            Medico medico = new Medico(utenteLoggatoEmail, "", D_NOME, D_COGNOME);
            String agenda = medico.recuperaTurniAgenda();

            medicoWindow.setTestoAgenda(agenda);
            medicoWindow.getTabbedPane1().setSelectedIndex(4);
        });

        // Logout
        medicoWindow.getBtnLogout().addActionListener(e -> eseguiLogout(medicoWindow));

        // Tasti per tornare al menu'
        ActionListener tornaAlMenuMedico = e -> {
            medicoWindow.svuotaCampi();
            medicoWindow.getTabbedPane1().setSelectedIndex(0);
        };
        medicoWindow.getINDIETROButton().addActionListener(tornaAlMenuMedico);
        medicoWindow.getINDIETROButton1().addActionListener(tornaAlMenuMedico);
        medicoWindow.getINDIETROButton2().addActionListener(tornaAlMenuMedico);
        medicoWindow.getINDIETROButton3().addActionListener(tornaAlMenuMedico);

        // Esegui prestazione
        medicoWindow.getCONFERMAButton().addActionListener(e -> {
            String pres = medicoWindow.getPrestazioneEseguita();
            String verb = medicoWindow.getVerbale();
            String tipo = medicoWindow.getDescrizioneTipo();

            if (pres.isEmpty() || verb.isEmpty() || tipo.equals("Non specificato")) {
                LOGGER.severe("ERRORE: Compila codice prestazione e verbale.");
                return;
            }

            Medico medico = new Medico(utenteLoggatoEmail, "", D_NOME, D_COGNOME);
            boolean successo = medico.eseguiPrestazione(pres, verb, tipo);
            if (successo) {
                LOGGER.info("PRESTAZIONE ESEGUITA: " + pres + " (" + tipo + ") - Verbale: " + verb);
                medicoWindow.svuotaCampi();
                medicoWindow.getTabbedPane1().setSelectedIndex(0);
            } else {
                LOGGER.severe("ERRORE: Fallita esecuzione prestazione.");
            }
        });

        // Pianifica prestazione
        medicoWindow.getPIANIFICAPRESTAZIONEButton().addActionListener(e -> {
            String data = medicoWindow.getDataInizioPrestazione();
            String ricovero = medicoWindow.getRicovero();

            if (data.isEmpty() || ricovero.isEmpty()) {
                LOGGER.severe("ERRORE: Compila data e codice ricovero.");
                return;
            }

            Medico medico = new Medico(utenteLoggatoEmail, "", D_NOME, D_COGNOME);
            boolean successo = medico.pianificaPrestazione(data, ricovero);

            if (successo) {
                LOGGER.info("PRESTAZIONE PIANIFICATA: Data " + data + " per ricovero " + ricovero);
                medicoWindow.svuotaCampi();
                medicoWindow.getTabbedPane1().setSelectedIndex(0);
            } else {
                LOGGER.severe("ERRORE: Fallita pianificazione prestazione.");
            }
        });

        // Modifica verbale
        medicoWindow.getMODIFICAButton().addActionListener(e -> {
            String pres = medicoWindow.getPrestazioneDaModificare();
            String nuovoVerbale = medicoWindow.getNuovoVerbale();

            if (pres.isEmpty() || nuovoVerbale.isEmpty()) {
                LOGGER.severe("ERRORE: Compila la prestazione e il nuovo verbale.");
                return;
            }

            Medico medico = new Medico(utenteLoggatoEmail, "", D_NOME, D_COGNOME);
            boolean successo = medico.modificaVerbale(pres, nuovoVerbale);

            if (successo) {
                LOGGER.info("VERBALE AGGIORNATO per prestazione: " + pres);
                medicoWindow.svuotaCampi();
                medicoWindow.getTabbedPane1().setSelectedIndex(0);
            } else {
                LOGGER.severe("ERRORE: Fallita modifica verbale.");
            }
        });
    }
}