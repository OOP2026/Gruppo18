package implementazioneDao;

import dao.PrestazioneDAO;
import database_connection.ConnessioneDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Logger;

public class PrestazioneDAOImpl implements PrestazioneDAO {
    private static final Logger LOGGER = Logger.getLogger(PrestazioneDAOImpl.class.getName());
    private final Connection connection;

    public PrestazioneDAOImpl() {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean aggiornaVerbale(String codicePrestazione, String nuovoVerbale) {
        String query = "UPDATE Prestazione SET verbale = ? WHERE id_prestazione = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nuovoVerbale);
            stmt.setInt(2, Integer.parseInt(codicePrestazione));
            return stmt.executeUpdate() > 0;
        } catch (SQLException | NumberFormatException e) {
            LOGGER.severe("Errore Aggiornamento Verbale: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean inserisciNuovaPrestazione(String dataInizio, String codiceRicovero, String tipo,String emailMedico) {
        String query = "INSERT INTO Prestazione (inizio_prestazione, id_ricovero, tipo,email_medico) VALUES (?, ?, CAST (? AS tipoprestazione),?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            Timestamp tsInizio = Timestamp.valueOf(dataInizio);
            stmt.setTimestamp(1, tsInizio);
            stmt.setInt(2, Integer.parseInt(codiceRicovero));
            stmt.setString(3,tipo);
            stmt.setString(4,emailMedico);

            return stmt.executeUpdate() > 0;
        } catch (SQLException | IllegalArgumentException e) {
            LOGGER.severe("Errore Pianificazione Prestazione: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean aggiungiVerbale(String codicePrestazione, String Verbale, String orafineprestazione,String Tipoprestazione) {
        String query = "UPDATE Prestazione SET fine_prestazione = ?, verbale = ?, tipo_intervento= ? WHERE id_prestazione = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            Timestamp finePrestazione = Timestamp.valueOf(orafineprestazione);
            stmt.setTimestamp(1,finePrestazione);
            stmt.setString(2,Verbale);
            stmt.setString(3,Tipoprestazione);
            stmt.setInt(4, Integer.parseInt(codicePrestazione));

            return stmt.executeUpdate() > 0;
        } catch (SQLException | IllegalArgumentException e) {
            LOGGER.severe("Errore Pianificazione Prestazione: " + e.getMessage());
            return false;
        }
    }
}