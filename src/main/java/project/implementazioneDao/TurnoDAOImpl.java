package project.implementazioneDao;

import project.dao.TurnoDAO;
import project.database_connection.ConnessioneDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class TurnoDAOImpl implements TurnoDAO {
    private static final Logger LOGGER = Logger.getLogger(TurnoDAOImpl.class.getName());
    private final Connection connection;

    public TurnoDAOImpl() {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public String recuperaAgendaMedico(String emailMedico) {
        String query = "SELECT data_turno, ora_inizio, ora_fine FROM Turno WHERE email_medico = ? ORDER BY data_turno, ora_inizio";
        StringBuilder agenda = new StringBuilder();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, emailMedico);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                agenda.append("Data: ").append(rs.getDate("data_turno"))
                        .append(" | Dalle: ").append(rs.getTime("ora_inizio"))
                        .append(" Alle: ").append(rs.getTime("ora_fine"))
                        .append("\n");
            }

            if (agenda.length() == 0) {
                return "Nessun turno assegnato.";
            }
            return agenda.toString();

        } catch (SQLException e) {
            LOGGER.severe("Errore Recupero Agenda: " + e.getMessage());
            return "Errore nel caricamento dell'agenda.";
        }
    }
        public boolean inserisciTurnoMedico(String emailMedico, String dataTurno, String oraInizio, String oraFine) {
         //con on conflict aggiorno nel caso esista un turno in quella data
            String query = "INSERT INTO Turno (email_medico, data_turno, ora_inizio, ora_fine) " +
                    "VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setString(1, emailMedico.trim());
                stmt.setDate(2, java.sql.Date.valueOf(dataTurno.trim()));
                stmt.setTime(3, java.sql.Time.valueOf(oraInizio.trim()));
                stmt.setTime(4, java.sql.Time.valueOf(oraFine.trim()));

                int righeInserite = stmt.executeUpdate();
                return righeInserite > 0;

            } catch (SQLException | IllegalArgumentException e) {
                LOGGER.severe("Errore Gestione Orario Medico: " + e.getMessage());
                return false;
            }
        }
}