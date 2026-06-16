package gui;

import javax.swing.*;

public class MedicoWindow extends JFrame {
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

    public MedicoWindow() {
        setTitle("Pannello Medico");
        setSize(700, 500);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        if (mainPanel != null) setContentPane(mainPanel);

        //Nasconde le tab in alto (linguette) del JTabbedPane
        tabbedPane1.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
                return 0; // Imposta l'altezza delle linguette a zero!
            }
        });
    }

    //Getter del menu'
    public JTabbedPane getTabbedPane1() { return tabbedPane1; }
    public JButton getBtnEseguiPrestazione() { return btnEseguiPrestazione; }
    public JButton getBtnPianificaPrestazione() { return btnPianificaPrestazione; }
    public JButton getBtnModificaVerbale() { return btnModificaVerbale; }
    public JButton getBtnVisualizzaAgenda() { return btnVisualizzaAgenda; }
    public JButton getBtnLogout() { return btnLogout; }

    //Getter della schermata Pianifica Prestazione
    public JButton getPIANIFICAPRESTAZIONEButton() { return PIANIFICAPRESTAZIONEButton; }
    public JButton getINDIETROButton() { return INDIETROButton; }
    public String getDataInizioPrestazione() { return dataInizioPrestazioneTextField.getText(); }
    public String getRicovero() { return ricoveroTextField.getText(); }

    //Getter della schermata Esegui Prestazione
    public JButton getCONFERMAButton() { return CONFERMAButton; }
    public JButton getINDIETROButton1() { return INDIETROButton1; }
    public String getPrestazioneEseguita() { return prestazioneTextField.getText(); }
    public String getVerbale() { return txtAreaVerbale.getText(); }
    public boolean isIntervento() { return interventoRadioButton.isSelected(); }
    public boolean isVisita() { return visitaRadioButton.isSelected(); }

    //Getter della schermata Modifica Verbale
    public JButton getMODIFICAButton() { return MODIFICAButton; }
    public JButton getINDIETROButton2() { return INDIETROButton2; }
    public String getPrestazioneDaModificare() { return prestazioneTextField1.getText(); }
    public String getNuovoVerbale() { return nuovoVerbaleTextArea.getText(); }

    // Comando che serve al controller per scrivere l'agenda presa dal Database dentro l'area di testo
    public void setTestoAgenda(String testo) { turnoDelMedicoTextArea.setText(testo); }
    public JButton getINDIETROButton3() { return INDIETROButton3; }

    public String getDescrizioneTipo() {
        if (this.isIntervento()) return "Intervento";
        if (this.isVisita()) return "Visita";
        return "Non specificato";
    }

    //Metodi di Utility
    public void svuotaCampi() {
        dataInizioPrestazioneTextField.setText("");
        ricoveroTextField.setText("");
        txtAreaVerbale.setText("");
        prestazioneTextField.setText("");
        prestazioneTextField1.setText("");
        nuovoVerbaleTextArea.setText("");
        turnoDelMedicoTextArea.setText("");

        interventoRadioButton.setSelected(false);
        visitaRadioButton.setSelected(false);
    }
}