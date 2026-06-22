package implementazioneDao;

import dao.RicoveroDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class RicoveroDAOImpl implements RicoveroDAO {
    private static final Logger LOGGER = Logger.getLogger(RicoveroDAOImpl.class.getName());
    private final Connection connection;

    public RicoveroDAOImpl() {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean iniziaRicovero(String codiceFiscale, String numeroLetto, String emailAmministratore,String DataFinePrevista) {
        // La query ora fa il CAST esplicito del parametro 'stato' al tipo personalizzato 'statoricovero'
        String query = "INSERT INTO Ricovero (tipo_ricovero, data_inizio, stato, codice_fiscale, numero_letto, email_amministratore , data_fine_prevista) VALUES (?, ?, CAST(? AS statoricovero), ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "Ordinario");
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));

            stmt.setString(3, "ATTIVO");

            stmt.setString(4, codiceFiscale);
            stmt.setString(5, numeroLetto);
            stmt.setString(6, emailAmministratore);
            stmt.setTimestamp(7, java.sql.Timestamp.valueOf(DataFinePrevista + " 10:00:00"));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Errore Creazione Ricovero: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean haRicoveroAttivo(String codiceFiscale) {
        String query = "SELECT 1 FROM Ricovero WHERE codice_fiscale = ? AND stato = CAST('ATTIVO' AS statoricovero)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, codiceFiscale);
            ResultSet rs = stmt.executeQuery();

            // Se rs.next() è true, significa che ha trovato almeno un ricovero ATTIVO!
            return rs.next();

        } catch (SQLException e) {
            LOGGER.severe("Errore controllo ricoveri attivi: " + e.getMessage());
            // In caso di errore DB blocchiamo tutto per sicurezza
            return true;
        }
    }

    @Override
    public boolean dimissioneRicovero(String codice_fiscale, String datadimissioneeffettiva) {
        // 1. SET: modifichiamo data e stato
        // 2. WHERE: cerchiamo la riga che ha quel codice fiscale e che è ancora "ATTIVO"
        String query = "UPDATE ricovero SET data_fine_effettiva = ?, stato = CAST('DIMESSO' AS statoricovero) WHERE codice_fiscale = ? AND stato = CAST('ATTIVO' AS statoricovero)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // CONTROLLO DATA: Se l'utente scrive "2026-06-22" aggiungiamo l'orario fittizio
            String dataCorretta = datadimissioneeffettiva.trim();
            if (dataCorretta.length() <= 10) {
                dataCorretta += " 12:00:00";
            }

            // Indice 1: la data
            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(dataCorretta));

            // Indice 2: il codice fiscale per il WHERE
            stmt.setString(2, codice_fiscale);

            return stmt.executeUpdate() > 0;
        } catch (SQLException | IllegalArgumentException e) {
            LOGGER.severe("Errore dimissione paziente: " + e.getMessage());
            return false;
        }
    }
}