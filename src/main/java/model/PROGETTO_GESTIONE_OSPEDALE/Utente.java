package model.PROGETTO_GESTIONE_OSPEDALE;

public class Utente {

    protected String password;
    protected String username;
    protected String nome;
    protected String cognome;

    public Utente(String username, String password, String nome, String cognome) {
        this.cognome = cognome;
        this.nome = nome;
        this.password = password;
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getNome(){
        return nome;
    }

}
