package dao;

public interface RicoveroDAO {
    boolean iniziaRicovero(String codiceFiscale, String numeroLetto, String emailAmministratore,String DataFinePrevista);
    boolean haRicoveroAttivo(String codiceFiscale);
    boolean dimissioneRicovero(String codice_fiscale, String datadimissioneeffettiva);
}