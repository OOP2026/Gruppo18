package project.gui;

import project.Controller;
import javax.swing.*;


public class LoginWindow extends JFrame {

    private JPanel mainPanel;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnAccedi;

    private final Controller controller;
    private AdminWindow adminWindow;
    private MedicoWindow medicoWindow;

    public LoginWindow(Controller controller) {
        this.controller = controller;
        setTitle("Ospedale - Login");
        setSize(350, 200);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        if (mainPanel != null) setContentPane(mainPanel);

        inizializzaEventi();
    }


    private void inizializzaEventi() {
        btnAccedi.addActionListener(e -> {
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                mostraErrore("Inserisci username e password!");
                return;
            }

            // Verifica delle credenziali chiamando la logica del Controller
            String ruolo = controller.effettuaLogin(user, pass);

            if (ruolo.equals("ADMIN")) {
                this.setVisible(false);
                if (adminWindow != null) adminWindow.setVisible(true);
            } else if (ruolo.equals("MEDICO")) {
                this.setVisible(false);
                if (medicoWindow != null) medicoWindow.setVisible(true);
            } else {
                mostraErrore("Credenziali errate o non presenti nel database.");
            }
        });
    }

    public void mostraErrore(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    public void setAdminWindow(AdminWindow adminWindow) {
        this.adminWindow = adminWindow;
    }

    public void setMedicoWindow(MedicoWindow medicoWindow) {
        this.medicoWindow = medicoWindow;
    }
}