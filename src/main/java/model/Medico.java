package model;

public class Medico extends Utente {

    private int num_telefono;
    private String email;

    public int getNum_telefono() {
        return num_telefono;
    }

    public void setNum_telefono(int num_telefono) {
        this.num_telefono = num_telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Medico(String username, String password, String nome, String cognome) {
        super(username, password, nome, cognome);
    }
}