package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class AdminWindow extends JFrame {
    static final String OPERAZIONE = "operazione Completata";
    private static final Logger LOGGER = Logger.getLogger(AdminWindow.class.getName());

    // Componenti grafici (I tuoi originali)
    private JPanel menu;
    private JPanel mainPanel;
    private JTextField campoCognome;
    private JTextField campoNome;
    private JTextField campoCodiceFiscale;
    private JTextField campoDataNascita;
    private JButton btnIniziaRicovero;
    private JButton btnAssegnaPaziente;
    private JButton btnVisualizzaAnagrafica;
    private JButton btnLogout;
    private JTabbedPane tabbedPane;
    private JTextField numNosologicoTextField1;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JButton INIZIARICOVEROButton;
    private JTextField numNosologicoTextField;
    private JTextField numLettoTextField;
    private JButton ASSEGNAPAZIENTEButton;
    private JButton INDIETROButton;
    private JButton INDIETROButton1;
    private JButton INDIETROButton2;
    private JTextArea AreaAnagrafica;
    private JTextField CampoGruppoSanguigno;
    private JTextField CampoDataFinePrevista;
    private JButton turnoButton;
    private JTextField campoEmailMedico;
    private JTextField campoOraFine;
    private JTextField campoDataTurno;
    private JTextField campoOraInizio;
    private JButton indietro4Button;
    private JButton aggiungiorarioButton;

    private final Controller controller;
    private final LoginWindow loginWindow;

    public AdminWindow(Controller controller, LoginWindow loginWindow) {
        this.controller = controller;
        this.loginWindow = loginWindow;

        setTitle("Pannello Amministratore");
        setSize(700, 500);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        if (mainPanel != null) setContentPane(mainPanel);

        // Rimuove le linguette del JTabbedPane
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
                return 0;
            }
        });
        if (tabbedPane != null) {
            tabbedPane.setSelectedIndex(0);
        }
        inizializzaEventi();
    }

    private void inizializzaEventi() {
        // Gestione dei pulsanti di navigazione del menu principale amministratore
        btnIniziaRicovero.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        btnAssegnaPaziente.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        btnVisualizzaAnagrafica.addActionListener(e -> tabbedPane.setSelectedIndex(3));
        turnoButton.addActionListener(e -> tabbedPane.setSelectedIndex(4));

        btnLogout.addActionListener(e -> {
            controller.eseguiLogout();
            this.setVisible(false);
            loginWindow.setVisible(true);
        });

        // Gestione pulsanti di ritorno al menu principale
        ActionListener tornaAlMenuAdmin = e -> {
            svuotaCampi();
            tabbedPane.setSelectedIndex(0);
        };
        INDIETROButton.addActionListener(tornaAlMenuAdmin);
        INDIETROButton1.addActionListener(tornaAlMenuAdmin);
        INDIETROButton2.addActionListener(tornaAlMenuAdmin);
        indietro4Button.addActionListener(tornaAlMenuAdmin);

        // operazione di avvio di un nuovo Ricovero
        INIZIARICOVEROButton.addActionListener(e -> {
            String nosologico = numNosologicoTextField1.getText();
            String letto = numLettoTextField.getText();
            String datafineprevista = CampoDataFinePrevista.getText();

            // 1. Troviamo il codice fiscale tramite Controller
            String cfcontrollo = controller.trovaCodiceFiscale(nosologico);

            if (cfcontrollo == null) {
                JOptionPane.showMessageDialog(this, "Nessun paziente trovato con questo nosologico", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (controller.haRicoveroAttivo(cfcontrollo)) {
                JOptionPane.showMessageDialog(this, "Impossibile procedere: Il paziente risulta già ricoverato in un altro letto", OPERAZIONE, JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (nosologico.isEmpty() || letto.isEmpty()) {
                LOGGER.severe("Impossibile procedere: i campi nosologico e letto sono obbligatori.");
                return;
            }

            // 3. Se supera i controlli, salva il ricovero tramite il Controller
            boolean successo = controller.iniziaRicovero(cfcontrollo, letto, datafineprevista);

            if (successo) {
                LOGGER.info("Ricovero registrato correttamente per il paziente con codice fiscale: " + cfcontrollo);
                JOptionPane.showMessageDialog(this, "Ricovero registrato con successo\nPaziente: " + cfcontrollo, OPERAZIONE, JOptionPane.INFORMATION_MESSAGE);
                svuotaCampi();
                tabbedPane.setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore nell'esecuzione dell'inserimento del ricovero nel database.");
            }
        });

        // OPERAZIONE di registrazione di un nuovo Paziente in anagrafica
        ASSEGNAPAZIENTEButton.addActionListener(e -> {
            String nome = campoNome.getText();
            String cognome = campoCognome.getText();
            String nos = numNosologicoTextField.getText();
            String cf = campoCodiceFiscale.getText();
            String data = campoDataNascita.getText();
            String gruppoSangue = CampoGruppoSanguigno.getText();

            if (nome.isEmpty() || cognome.isEmpty() || nos.isEmpty() || cf.isEmpty() || data.isEmpty() || gruppoSangue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Attenzione: devi compilare tutti i campi", "Campi Incompleti", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean successo = controller.registraNuovoPaziente(nome, cognome, nos, cf, data, gruppoSangue);

            if (successo) {
                LOGGER.info("Anagrafica salvata. Paziente registrato: " + nome + " " + cognome);
                JOptionPane.showMessageDialog(this, "paziente inserito correttamente\nPaziente:" + nome + " " + cognome, OPERAZIONE, JOptionPane.INFORMATION_MESSAGE);
                svuotaCampi();
                tabbedPane.setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore durante il salvataggio dei dati anagrafici del paziente.");
            }
        });

        // Navigazione e caricamento Anagrafica
        btnVisualizzaAnagrafica.addActionListener(e -> {
            // 1. Chiede al Controller di interrogare PostgreSQL
            String datiAnagrafica = controller.visualizzaAnagrafica();

            // 2. Inserisce il testo estratto nella JTextArea
            AreaAnagrafica.setText(datiAnagrafica);

            // 3. Sposta la visuale dell'Amministratore sulla scheda giusta
            tabbedPane.setSelectedIndex(3);
        });

        // Aggiunta turni medico
        aggiungiorarioButton.addActionListener(e -> {
            String email = campoEmailMedico.getText();
            String dataTurno = campoDataTurno.getText();
            String oraInizio = campoOraInizio.getText();
            String oraFine = campoOraFine.getText();

            boolean successo = controller.aggiungiTurno(email, dataTurno, oraInizio, oraFine);

            if (successo) {
                LOGGER.info("Turno salvato per medico: " + email);
                JOptionPane.showMessageDialog(this, "Turno salvato con successo\nMedico:" + email, OPERAZIONE, JOptionPane.INFORMATION_MESSAGE);
                svuotaCampi();
                tabbedPane.setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore durante il salvataggio del turno.");
            }
        });
    }

    public void svuotaCampi() {
        if (nomeTextField != null) nomeTextField.setText("nome");
        if (cognomeTextField != null) cognomeTextField.setText("cognome");
        if (numNosologicoTextField1 != null) numNosologicoTextField1.setText("numnosologico");
        if (campoNome != null) campoNome.setText("nome");
        if (campoCognome != null) campoCognome.setText("cognome");
        if (campoCodiceFiscale != null) campoCodiceFiscale.setText("codiceFiscale");
        if (campoDataNascita != null) campoDataNascita.setText("dataNascita");
        if (CampoGruppoSanguigno != null) CampoGruppoSanguigno.setText("gruppo sanguigno");

        if (numNosologicoTextField != null) numNosologicoTextField.setText("numnosologico");
        if (numLettoTextField != null) numLettoTextField.setText("letto");
        if (CampoDataFinePrevista != null) CampoDataFinePrevista.setText("data dimissione prevista");

        if (AreaAnagrafica != null) AreaAnagrafica.setText("");

        if (campoEmailMedico != null) campoEmailMedico.setText("email medico");
        if (campoDataTurno != null) campoDataTurno.setText("data turno");
        if (campoOraInizio != null) campoOraInizio.setText("ora inizio");
        if (campoOraFine != null) campoOraFine.setText("ora fine");
    }
}