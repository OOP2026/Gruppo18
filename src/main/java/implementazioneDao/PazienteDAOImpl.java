package implementazioneDao;

import dao.PazienteDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class PazienteDAOImpl implements PazienteDAO {
    static final String CODICE_FISCALE="codice_fiscale";
    private static final Logger LOGGER = Logger.getLogger(PazienteDAOImpl.class.getName());
    private final Connection connection;

    /**
     * Costruttore della classe.
     * Inizializza la connessione al database recuperandola dal Singleton ConnessioneDatabase.
     */
    public PazienteDAOImpl() {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean inserisciPaziente(String codiceFiscale, String numNosologico, String nome, String cognome, String dataNascita, String gruppoSanguigno) {
        String query = "INSERT INTO Paziente (codice_fiscale, numero_nosologico, nome, cognome, data_nascita, gruppo_sanguigno) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, codiceFiscale);
            stmt.setString(2, numNosologico);
            stmt.setString(3, nome);
            stmt.setString(4, cognome);
            // La tua logica perfetta per trasformare la stringa in Data SQL
            stmt.setDate(5, java.sql.Date.valueOf(dataNascita));
            stmt.setString(6, gruppoSanguigno);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Errore DB - Inserimento paziente fallito: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String[] trovaPazientePerNosologico(String numNosologico) {
        String[] datiPaziente = null;
        String query = "SELECT * FROM Paziente WHERE numero_nosologico = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numNosologico);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                datiPaziente = new String[6];
                datiPaziente[0] = rs.getString(CODICE_FISCALE);
                datiPaziente[1] = rs.getString("numero_nosologico");
                datiPaziente[2] = rs.getString("nome");
                datiPaziente[3] = rs.getString("cognome");
                datiPaziente[4] = rs.getDate("data_nascita").toString();
                datiPaziente[5] = rs.getString("gruppo_sanguigno");
            }
        } catch (SQLException e) {
            LOGGER.severe("Errore DB - Ricerca paziente fallita: " + e.getMessage());
        }

        return datiPaziente;
    }

    @Override
    public String trovaCodiceFiscaleDaNosologico(String numNosologico) {
        String query = "SELECT codice_fiscale FROM Paziente WHERE numero_nosologico = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numNosologico);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString(CODICE_FISCALE);
            }
        } catch (SQLException e) {
            LOGGER.severe("Errore DB - Ricerca CF: " + e.getMessage());
        }

        return null;
    }

    @Override
    public String recuperaTuttaAnagrafica() {
        String query = "SELECT nome, cognome, codice_fiscale, numero_nosologico, data_nascita FROM Paziente ORDER BY cognome, nome";
        StringBuilder anagrafica = new StringBuilder();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                anagrafica.append("Paziente: ").append(rs.getString("cognome")).append(" ").append(rs.getString("nome"))
                        .append("\n  Codice Fiscale: ").append(rs.getString(CODICE_FISCALE))
                        .append("\n  Nosologico: ").append(rs.getString("numero_nosologico"))
                        .append("\n  Data Nascita: ").append(rs.getDate("data_nascita"))
                        .append("\n---------------------------------------------------\n");
            }

            if (anagrafica.length() == 0) {
                return "Nessun paziente attualmente registrato nel database.";
            }
            return anagrafica.toString();

        } catch (SQLException e) {
            LOGGER.severe("Errore DB - Impossibile caricare l'anagrafica: " + e.getMessage());
            return "Errore di connessione durante il caricamento dell'anagrafica.";
        }
    }
}