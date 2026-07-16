package project.implementazioneDao;

import project.dao.StanzaDAO;
import project.database_connection.ConnessioneDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StanzaDAOImpl implements StanzaDAO {
    private static final Logger LOGGER = Logger.getLogger(StanzaDAOImpl.class.getName());
    private final Connection connection;

    public StanzaDAOImpl() {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public List<String> ottieniStanzePerReparto(int idReparto) {
        List<String> stanze = new ArrayList<>();
        String query = "SELECT numero_stanza FROM Stanza WHERE id_reparto = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idReparto);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stanze.add(rs.getString("numero_stanza"));
            }
        } catch (SQLException e) {
            LOGGER.severe("Errore caricamento stanze: " + e.getMessage());
        }
        return stanze;
    }
}