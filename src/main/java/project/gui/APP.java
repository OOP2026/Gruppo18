package project.gui;

import project.Controller;

public class APP {
    public static void main(String[] args) {
        // 1. Creiamo il "Cervello" (Si connette al database)
        Controller controller = new Controller();

        // 2. Creiamo le finestre e passiamo loro il controller
        // così sanno a chi chiedere di fare i calcoli
        LoginWindow loginWindow = new LoginWindow(controller);
        AdminWindow adminWindow = new AdminWindow(controller, loginWindow);
        MedicoWindow medicoWindow = new MedicoWindow(controller, loginWindow);

        // 3. Colleghiamo le finestre tra loro: il Login deve sapere
        // quali finestre aprire dopo aver inserito la password corretta
        loginWindow.setAdminWindow(adminWindow);
        loginWindow.setMedicoWindow(medicoWindow);

        // 4. Infine, rendiamo visibile la schermata iniziale!
        loginWindow.setVisible(true);
    }
}