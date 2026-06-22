package gui;

import javax.swing.*;

public class AdminWindow extends JFrame {
    //Componenti grafici
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

    //Finestra ADMIN
    public AdminWindow() {
        setTitle("Pannello Amministratore");
        setSize(700, 500);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        if (mainPanel != null) setContentPane(mainPanel);

        //Rimuove le linguette del JTabbedPane
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
                return 0; // Imposta l'altezza delle linguette a zero!
            }
        });
    }

    // Getter per il menu
    public JTabbedPane getTabbedPane() { return tabbedPane; }
    public JButton getBtnIniziaRicovero() { return btnIniziaRicovero; }
    public JButton getBtnAssegnaPaziente() { return btnAssegnaPaziente; }
    public JButton getBtnVisualizzaAnagrafica() { return btnVisualizzaAnagrafica; }
    public JButton getTurnoButton() { return turnoButton; }
    public JButton getBtnLogout() { return btnLogout; }

    // Getter della schermata menu'
    public JButton getINIZIARICOVEROButton() { return INIZIARICOVEROButton; }
    public JButton getINDIETROButton() { return INDIETROButton; }

    public String getNumNosologicoRicovero() { return numNosologicoTextField1.getText(); }
    public String getNumLetto() { return numLettoTextField.getText(); }
    public String getDataDimissionePrevista() { return CampoDataFinePrevista.getText(); }

    public JButton getASSEGNAPAZIENTEButton() { return ASSEGNAPAZIENTEButton; }
    public JButton getINDIETROButton1() { return INDIETROButton1; }
    public String getGruppoSanguigno() {
        return CampoGruppoSanguigno.getText();
    }
    public String getNomePaziente() { return campoNome.getText(); }
    public String getCognomePaziente() { return campoCognome.getText(); }
    public String getNumNosologicoNuovo() { return numNosologicoTextField.getText(); }
    public JButton getINDIETROButton2() { return INDIETROButton2; }

    public String getCodiceFiscale() {
        return campoCodiceFiscale.getText();
    }

    public String getDataNascita() {
        return campoDataNascita.getText();
    }

    // Getter della schermata Turni medici
    public String getEmailMedico() { return campoEmailMedico.getText(); }
    public String getDataTurno() { return campoDataTurno.getText(); }
    public String getOraInizio() { return campoOraInizio.getText(); }
    public String getOraFine() { return campoOraFine.getText(); }
    public JButton getAggiungiOrarioButton() { return aggiungiorarioButton; }
    public JButton getIndietro4Button() { return indietro4Button; }

    public void setTestoAnagrafica(String testo) {
        AreaAnagrafica.setText(testo);
    }
    public void svuotaCampi() {
        // --- CAMPI ANAGRAFICA ---
        if (nomeTextField != null) nomeTextField.setText("nome");
        if (cognomeTextField != null) cognomeTextField.setText("cognome");
        if (numNosologicoTextField1 != null) numNosologicoTextField1.setText("numnosologico");
        if (campoNome != null) campoNome.setText("nome");
        if (campoCognome != null) campoCognome.setText("cognome");
        if (campoCodiceFiscale != null) campoCodiceFiscale.setText("codiceFiscale");
        if (campoDataNascita != null) campoDataNascita.setText("dataNascita");
        if (CampoGruppoSanguigno != null) CampoGruppoSanguigno.setText("gruppo sanguigno");

        // --- CAMPI RICOVERO ---
        if (numNosologicoTextField != null) numNosologicoTextField.setText("numnosologico");
        if (numLettoTextField != null) numLettoTextField.setText("letto");
        if (CampoDataFinePrevista != null) CampoDataFinePrevista.setText("data dimissione prevista");

        // --- AREA LISTA PAZIENTI ---
        if (AreaAnagrafica != null) AreaAnagrafica.setText("");

        // --- CAMPI TURNO MEDICO ---
        if (campoEmailMedico != null) campoEmailMedico.setText("email medico");
        if (campoDataTurno != null) campoDataTurno.setText("data turno");
        if (campoOraInizio != null) campoOraInizio.setText("ora inizio");
        if (campoOraFine != null) campoOraFine.setText("ora fine");
    }
}