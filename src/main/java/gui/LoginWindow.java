package gui;

import javax.swing.*;

public class LoginWindow extends JFrame {

    private JPanel mainPanel;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnAccedi;


    public LoginWindow() {
        setTitle("Ospedale - Login");
        setSize(350, 200);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        if (mainPanel != null) setContentPane(mainPanel);
    }

    // Metodi per il Controller
    public String getUsername() { return txtUsername.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public JButton getBtnAccedi() { return btnAccedi; }
    public void mostraErrore(String msg) { JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE); }
}