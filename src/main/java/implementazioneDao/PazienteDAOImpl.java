package implementazioneDao;

import dao.PazienteDAO;
import database_connection.ConnessioneDatabase;
import model.PROGETTO_GESTIONE_OSPEDALE.Paziente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Implementazione PostgreSQL dell'interfaccia PazienteDAO.
 * Gestisce la comunicazione diretta con il database relazionale per la tabella Paziente,
 * utilizzando interrogazioni parametriche per prevenire SQL injection.
 */
public class PazienteDAOImpl implements PazienteDAO {

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
    public boolean inserisciPaziente(Paziente paziente) {
        // Aggiunto gruppo_sanguigno e un sesto punto interrogativo
        String query = "INSERT INTO Paziente (codice_fiscale, numero_nosologico, nome, cognome, data_nascita, gruppo_sanguigno) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, paziente.getCodiceFiscale());
            stmt.setString(2, paziente.getNum_Nosologico());
            stmt.setString(3, paziente.getNome_paziente());
            stmt.setString(4, paziente.getCognome_paziente());
            stmt.setDate(5, java.sql.Date.valueOf(paziente.getDataNascita()));

            // Il nuovo campo!
            stmt.setString(6, paziente.getGruppoSanguigno());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Errore DB - Inserimento paziente fallito: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Paziente trovaPazientePerNosologico(String numNosologico) {
        // Implementazione base. Può essere espansa in base alle esigenze delle altre interfacce grafiche.
        return null;
    }

    @Override
    public String trovaCodiceFiscaleDaNosologico(String numNosologico) {
        String query = "SELECT codice_fiscale FROM Paziente WHERE numero_nosologico = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numNosologico);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("codice_fiscale");
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
                        .append("\n  Codice Fiscale: ").append(rs.getString("codice_fiscale"))
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