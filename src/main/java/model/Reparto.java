package model;

public class Reparto {

    private int num_reparto;
    private String tipo_reparto;
    private int capienza;

    public int getNum_reparto() {
        return num_reparto;
    }

    public void setNum_reparto(int num_reparto) {
        this.num_reparto = num_reparto;
    }

    public String getTipo_reparto() {
        return tipo_reparto;
    }

    public void setTipo_reparto(String tipo_reparto) {
        this.tipo_reparto = tipo_reparto;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public Reparto(int num_reparto, String tipo_reparto, int capienza){
        this.num_reparto = num_reparto;
        this.tipo_reparto = tipo_reparto;
        this.capienza = capienza;
    }

}
