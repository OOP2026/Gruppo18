package model.PROGETTO_GESTIONE_OSPEDALE;

public class Paziente {

    private String Num_Nosologico;
    private String nome_paziente;
    private String cognome_paziente;
    private String dataNascita;
    private String codiceFiscale;
    private String gruppoSanguigno;

    public String getGruppoSanguigno() {
        return gruppoSanguigno;
    }
    public void setGruppoSanguigno(String gruppoSanguigno) {
       this.gruppoSanguigno = gruppoSanguigno;
    }

    public String getNum_Nosologico() {
        return Num_Nosologico;
    }

    public void setNum_Nosologico (String num_Nosologico) {
        Num_Nosologico = num_Nosologico;
    }

    public String getNome_paziente() {
        return nome_paziente;
    }

    public void setNome_paziente(String nome_paziente) {
        this.nome_paziente = nome_paziente;
    }

    public String getCognome_paziente() {
        return cognome_paziente;
    }

    public void setCognome_paziente(String cognome_paziente) {
        this.cognome_paziente = cognome_paziente;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Paziente(String nome_paziente, String cognome_paziente, String num_Nosologico,String gruppoSanguigno) {
        this.nome_paziente = nome_paziente;
        this.cognome_paziente = cognome_paziente;
        this.Num_Nosologico = num_Nosologico;
        this.gruppoSanguigno = gruppoSanguigno;
    }
}
