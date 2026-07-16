package implementazioneDao;

import dao.AmministratoreDAO;
import database_connection.ConnessioneDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AmministratoreDAOImpl implements AmministratoreDAO {
    private static final Logger LOGGER = Logger.getLogger(AmministratoreDAOImpl.class.getName());
    private final Connection connection;

    public AmministratoreDAOImpl() {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean verificaCredenziali(String email, String password) {
        String query = "SELECT 1 FROM Amministratore WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Se c'è almeno una riga, le credenziali sono corrette
        } catch (SQLException e) {
            LOGGER.severe("Errore login Amministratore: " + e.getMessage());
            return false;
        }
    }
}