package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class MedicoWindow extends JFrame {
    static final String OPERAZIONE = "operazione Completata";
    private static final Logger LOGGER = Logger.getLogger(MedicoWindow.class.getName());

    // Componenti grafici (I tuoi originali)
    private JPanel menu;
    private JPanel mainPanel;
    private JButton btnEseguiPrestazione;
    private JButton btnPianificaPrestazione;
    private JButton btnModificaVerbale;
    private JButton btnVisualizzaAgenda;
    private JButton btnLogout;
    private JTabbedPane tabbedPane1;
    private JTextField dataInizioPrestazioneTextField;
    private JButton PIANIFICAPRESTAZIONEButton;
    private JButton INDIETROButton;
    private JTextField ricoveroTextField;
    private JTextArea txtAreaVerbale;
    private JButton CONFERMAButton;
    private JButton INDIETROButton1;
    private JTextField prestazioneTextField;
    private JTextField prestazioneTextField1;
    private JTextArea nuovoVerbaleTextArea;
    private JButton MODIFICAButton;
    private JButton INDIETROButton2;
    private JRadioButton interventoRadioButton;
    private JRadioButton visitaRadioButton;
    private JTextArea turnoDelMedicoTextArea;
    private JButton INDIETROButton3;
    private JTextField campoOraFinePrestazione;
    private JTextField campoTipoPrestazione;
    private JTextField campoNosologicoDimissione;
    private JTextField campoDataDimissione;
    private JButton dimettiButton;
    private JButton indietroButton5;
    private JButton DIMETTIPAZIENTEButton;

    private final Controller controller;
    private final LoginWindow loginWindow;

    public MedicoWindow(Controller controller, LoginWindow loginWindow) {
        this.controller = controller;
        this.loginWindow = loginWindow;

        setTitle("Pannello Medico");
        setSize(700, 500);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        if (mainPanel != null) setContentPane(mainPanel);

        // Nasconde le tab in alto (linguette) del JTabbedPane
        tabbedPane1.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
                return 0;
            }
        });

        if (tabbedPane1 != null) {
            tabbedPane1.setSelectedIndex(0);
        }

        inizializzaEventi();
    }

    private void inizializzaEventi() {
        // Gestione dei pulsanti di navigazione del menu principale medico
        btnEseguiPrestazione.addActionListener(e -> tabbedPane1.setSelectedIndex(1));
        btnPianificaPrestazione.addActionListener(e -> tabbedPane1.setSelectedIndex(3));
        btnModificaVerbale.addActionListener(e -> tabbedPane1.setSelectedIndex(4));
        DIMETTIPAZIENTEButton.addActionListener(e -> tabbedPane1.setSelectedIndex(5));

        // Visualizzazione dei turni lavorativi nell'agenda
        btnVisualizzaAgenda.addActionListener(e -> {
            String agenda = controller.visualizzaAgenda();
            turnoDelMedicoTextArea.setText(agenda);
            tabbedPane1.setSelectedIndex(2);
        });

        btnLogout.addActionListener(e -> {
            controller.eseguiLogout();
            this.setVisible(false);
            loginWindow.setVisible(true);
        });

        ActionListener tornaAlMenuMedico = e -> {
            svuotaCampi();
            tabbedPane1.setSelectedIndex(0);
        };
        INDIETROButton.addActionListener(tornaAlMenuMedico);
        INDIETROButton1.addActionListener(tornaAlMenuMedico);
        INDIETROButton2.addActionListener(tornaAlMenuMedico);
        INDIETROButton3.addActionListener(tornaAlMenuMedico);
        indietroButton5.addActionListener(tornaAlMenuMedico);

        // operazione di verbalizzazione di una prestazione medica esistente
        CONFERMAButton.addActionListener(e -> {
            String pres = prestazioneTextField.getText();
            String verb = txtAreaVerbale.getText();
            String dataFinePrestazione = campoOraFinePrestazione.getText();
            String Tipoprestazione = campoTipoPrestazione.getText();

            if (pres.isEmpty() || verb.isEmpty() || dataFinePrestazione.isEmpty()) {
                LOGGER.severe("Impossibile procedere: codice prestazione, tipologia, verbale e la data di fine prestazione sono campi richiesti.");
                return;
            }

            boolean successo = controller.verbalizzaPrestazione(pres, verb, dataFinePrestazione, Tipoprestazione);
            if (successo) {
                LOGGER.info("verbale e ora fine prestazione aggiunte alla prestazione " + pres + " registrata con successo.");
                JOptionPane.showMessageDialog(this, "verbale e ora fine prestazione aggiunte\ni dati nella prestazione:" + pres +"sono stati aggiunti", OPERAZIONE, JOptionPane.INFORMATION_MESSAGE);
                svuotaCampi();
                tabbedPane1.setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore nel processo di registrazione della prestazione.");
            }
        });

        // operazione di pianificazione temporale di una nuova prestazione
        PIANIFICAPRESTAZIONEButton.addActionListener(e -> {
            String data = dataInizioPrestazioneTextField.getText();
            String ricovero = ricoveroTextField.getText();

            String tipo = "Non specificato";
            if (interventoRadioButton.isSelected()) tipo = "Intervento";
            if (visitaRadioButton.isSelected()) tipo = "Visita";

            if (data.isEmpty() || ricovero.isEmpty()) {
                LOGGER.severe("Impossibile procedere: specificare la data e l'identificativo del ricovero.");
                return;
            }

            boolean successo = controller.pianificaPrestazione(data, ricovero, tipo);

            if (successo) {
                LOGGER.info("Nuova prestazione pianificata correttamente per la data indicata.");
                JOptionPane.showMessageDialog(this, "nuova prestazione pianificata\n:" + data + "codice ricovero"+ricovero+"sono stati aggiunti", OPERAZIONE, JOptionPane.INFORMATION_MESSAGE);
                svuotaCampi();
                tabbedPane1.setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore nell'inserimento della pianificazione della prestazione.");
            }
        });

        // operazione di modifica di un verbale preesistente
        MODIFICAButton.addActionListener(e -> {
            String pres = prestazioneTextField1.getText();
            String nuovoVerbale = nuovoVerbaleTextArea.getText();

            if (pres.isEmpty() || nuovoVerbale.isEmpty()) {
                LOGGER.severe("Impossibile procedere: i campi codice prestazione e testo verbale sono richiesti.");
                return;
            }

            boolean successo = controller.aggiornaVerbale(pres, nuovoVerbale);

            if (successo) {
                LOGGER.info("Il verbale relativo alla prestazione " + pres + " è stato modificato con successo.");
                JOptionPane.showMessageDialog(this, "il verbale è stato modificato\n:"+" alla prestazione"+ pres, OPERAZIONE, JOptionPane.INFORMATION_MESSAGE);
                svuotaCampi();
                tabbedPane1.setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore nella modifica dei dati del verbale clinico.");
            }
        });

        dimettiButton.addActionListener(e -> {
            String nosologico = campoNosologicoDimissione.getText();
            String dataEffettiva = campoDataDimissione.getText();

            if (nosologico.isEmpty() || dataEffettiva.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci il numero nosologico e la data di dimissione.", "fallimento", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean successo = controller.dimettiPaziente(nosologico, dataEffettiva);

            if (successo) {
                LOGGER.info("Paziente dimesso con successo.");
                JOptionPane.showMessageDialog(this, "Paziente dimesso con successo.");
                JOptionPane.showMessageDialog(this, "paziente dimesso\n:"+ "nosologico paziente:"+nosologico, OPERAZIONE, JOptionPane.INFORMATION_MESSAGE);
                svuotaCampi();
                tabbedPane1.setSelectedIndex(0);
            } else {
                LOGGER.severe("Errore durante la dimissione del paziente.");
                JOptionPane.showMessageDialog(this, "Errore: Paziente non trovato o non attualmente ricoverato.", "Errore Dimissione", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void svuotaCampi() {
        if (dataInizioPrestazioneTextField != null) dataInizioPrestazioneTextField.setText("data inizio prestazione");
        if (ricoveroTextField != null) ricoveroTextField.setText("codice ricovero");
        if (txtAreaVerbale != null) txtAreaVerbale.setText("verbale");
        if (prestazioneTextField != null) prestazioneTextField.setText("codice prestazione");
        if(prestazioneTextField1 != null) prestazioneTextField1.setText("codice prestazione");
        if(nuovoVerbaleTextArea != null)  nuovoVerbaleTextArea.setText("verbale");
        if(turnoDelMedicoTextArea != null) turnoDelMedicoTextArea.setText("");
        if(campoTipoPrestazione!= null) campoTipoPrestazione.setText("tipo prestazione");
        if(interventoRadioButton != null) interventoRadioButton.setSelected(false);
        if(visitaRadioButton != null) visitaRadioButton.setSelected(false);
    }
}