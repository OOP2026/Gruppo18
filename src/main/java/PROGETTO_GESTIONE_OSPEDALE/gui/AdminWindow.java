package PROGETTO_GESTIONE_OSPEDALE.gui;

import javax.swing.*;

public class AdminWindow extends JFrame {
    //Componenti grafici
    private JPanel mainPanel;
    private JButton btnIniziaRicovero;
    private JButton btnAssegnaPaziente;
    private JButton btnVisualizzaAnagrafica;
    private JButton btnLogout;
    private JTabbedPane tabbedPane;
    private JPanel menu;
    private JTextField numNosologicoTextField1;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JButton INIZIARICOVEROButton;
    private JTextField numNosologicoTextField;
    private JTextField numLettoTextField;
    private JButton ASSEGNAPAZIENTEButton;
    private JButton INDIETROButton;
    private JButton INDIETROButton1;
    private JTextField textField1;
    private JButton INDIETROButton2;
    private JTextField dataInizioRicoveroTextField;
    private JTextField dataDimissionePrevistaTextField;
    private JTextField dataDimissioneTextField;

    //Finestra ADMIN
    public AdminWindow() {
        setTitle("Pannello Amministratore");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    public JButton getBtnLogout() { return btnLogout; }

    // Getter della schermata menu'
    public JButton getINIZIARICOVEROButton() { return INIZIARICOVEROButton; }
    public JButton getINDIETROButton() { return INDIETROButton; }

    public String getNumNosologicoRicovero() { return numNosologicoTextField.getText(); }
    public String getNumLetto() { return numLettoTextField.getText(); }
    public String getDataInizio() { return dataInizioRicoveroTextField.getText(); }
    public String getDataDimissionePrevista() { return dataDimissionePrevistaTextField.getText(); }
    public String getDataDimissioneEffettiva() { return dataDimissioneTextField.getText(); }

    public JButton getASSEGNAPAZIENTEButton() { return ASSEGNAPAZIENTEButton; }
    public JButton getINDIETROButton1() { return INDIETROButton1; }

    public String getNomePaziente() { return nomeTextField.getText(); }
    public String getCognomePaziente() { return cognomeTextField.getText(); }
    public String getNumNosologicoNuovo() { return numNosologicoTextField1.getText(); }

    public JButton getINDIETROButton2() { return INDIETROButton2; }
    public String getRicercaNosologico() { return textField1.getText(); }

    public void svuotaCampi() {
        numNosologicoTextField.setText("");
        numLettoTextField.setText("");
        nomeTextField.setText("");
        cognomeTextField.setText("");
        numNosologicoTextField1.setText("");
        textField1.setText("");
    }
}