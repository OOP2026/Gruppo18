package project.implementazioneDao;

import project.dao.RepartoDAO;
import project.database_connection.ConnessioneDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RepartoDAOImpl implements RepartoDAO {
    private static final Logger LOGGER = Logger.getLogger(RepartoDAOImpl.class.getName());
    private final Connection connection;

    public RepartoDAOImpl() {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public List<String> ottieniTuttiNomiReparti() {
        List<String> reparti = new ArrayList<>();
        String query = "SELECT nome_reparto FROM Reparto";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reparti.add(rs.getString("nome_reparto"));
            }
        } catch (SQLException e) {
            LOGGER.severe("Errore caricamento reparti: " + e.getMessage());
        }
        return reparti;
    }
}