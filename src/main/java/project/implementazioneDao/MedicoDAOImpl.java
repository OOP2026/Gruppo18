package project.implementazioneDao;

import project.dao.MedicoDAO;
import project.database_connection.ConnessioneDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MedicoDAOImpl implements MedicoDAO {
    private static final Logger LOGGER = Logger.getLogger(MedicoDAOImpl.class.getName());
    private final Connection connection;

    public MedicoDAOImpl() {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean verificaCredenziali(String email, String password) {
        String query = "SELECT 1 FROM Medico WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.severe("Errore login Medico: " + e.getMessage());
            return false;
        }
    }
}