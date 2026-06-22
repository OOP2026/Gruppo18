package gui;

import javax.swing.*;

public class MedicoWindow extends JFrame {
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
    public JButton getDIMETTIPAZIENTEButton() {return DIMETTIPAZIENTEButton;}

    //Getter della schermata Pianifica Prestazione
    public JButton getPIANIFICAPRESTAZIONEButton() { return PIANIFICAPRESTAZIONEButton; }
    public JButton getINDIETROButton() { return INDIETROButton; }
    public String getDataInizioPrestazione() { return dataInizioPrestazioneTextField.getText(); }
    public String getRicovero() { return ricoveroTextField.getText(); }
    public boolean isIntervento() { return interventoRadioButton.isSelected(); }
    public boolean isVisita() { return visitaRadioButton.isSelected(); }

    //Getter della schermata scrivi verbale
    public JButton getCONFERMAButton() { return CONFERMAButton; }
    public JButton getINDIETROButton1() { return INDIETROButton1; }
    public String getPrestazioneEseguita() { return prestazioneTextField.getText(); }
    public String getVerbale() { return txtAreaVerbale.getText(); }
    public String getcampoOraFinePrestazione() { return campoOraFinePrestazione.getText(); }
    public String getcampoTipoPrestazione() { return campoTipoPrestazione.getText(); }

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

    //getter schermata dimissione paziente
     public String getCampoNosologicoDimissione() {return campoNosologicoDimissione.getText();}
        public String getCampoDataDimissione() {return campoDataDimissione.getText();}
        public JButton getDimettiButton() {return dimettiButton;}
        public JButton getIndietroButton5() {return indietroButton5;}
    //svuotamento
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