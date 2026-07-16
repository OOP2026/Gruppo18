package project.dao;
import java.util.List;

public interface StanzaDAO {
    List<String> ottieniStanzePerReparto(int idReparto);
}