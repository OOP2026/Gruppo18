package project.implementazioneDao;

import project.dao.LettoDAO;
import project.database_connection.ConnessioneDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LettoDAOImpl implements LettoDAO {
    private static final Logger LOGGER = Logger.getLogger(LettoDAOImpl.class.getName());
    private final Connection connection;

    public LettoDAOImpl() {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public List<String> trovaLettiLiberi() {
        List<String> lettiLiberi = new ArrayList<>();
        // Query che esclude i letti presenti nei ricoveri con stato 'ATTIVO'
        String query = "SELECT numero_letto FROM Letto WHERE numero_letto NOT IN " +
                "(SELECT numero_letto FROM Ricovero WHERE stato = 'ATTIVO')";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lettiLiberi.add(rs.getString("numero_letto"));
            }
        } catch (SQLException e) {
            LOGGER.severe("Errore ricerca letti liberi: " + e.getMessage());
        }
        return lettiLiberi;
    }
}