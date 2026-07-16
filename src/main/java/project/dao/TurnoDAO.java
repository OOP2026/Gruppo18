package project.dao;

public interface TurnoDAO {
    String recuperaAgendaMedico(String emailMedico);
    boolean inserisciTurnoMedico(String emailMedico, String dataTurno, String oraInizio, String oraFine);
}