package model;

public class Utente {

    private String password;
    private String username;
    private String nome;
    private String cognome;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Utente(String username, String password, String nome, String cognome) {
        this.cognome = cognome;
        this.nome = nome;
        this.password = password;
        this.username = username;
    }
}
