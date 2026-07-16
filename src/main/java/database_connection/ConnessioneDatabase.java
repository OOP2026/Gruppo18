package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Gestisce la connessione al database PostgreSQL utilizzando il pattern Singleton.
 * Garantisce che esista un'unica istanza di connessione per tutta l'applicazione.
 */
public class ConnessioneDatabase {

    private static final Logger LOGGER = Logger.getLogger( ConnessioneDatabase.class.getName());
    private static ConnessioneDatabase instance;
    private Connection connection;


    private static final String URL = "jdbc:postgresql://localhost:5432/Ospedale";
    private static final String USERNAME = "postgres";
    private static final String CREDENZIALE = "Admin_password";

    private ConnessioneDatabase() {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, CREDENZIALE);
            LOGGER.info("Connessione al database PostgreSQL stabilita con successo.");
        } catch (SQLException e) {
            LOGGER.severe("ERRORE: Impossibile connettersi al database. Verifica credenziali e server.");
        }
    }

    public static ConnessioneDatabase getInstance() {
        if (instance == null) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}