package entity;

import java.sql.Date;

public class CartaDiCredito {
    private String numeroCarta;
    private String nomeIntestatario;
    private String cognomeIntestatario;
    private Date dataScadenza;
    private String username;

    public CartaDiCredito(String numeroCarta, String nomeIntestatario, String cognomeIntestatario, String dataScadenza, String username){
        this.numeroCarta = numeroCarta;
        this.nomeIntestatario = nomeIntestatario;
        this.cognomeIntestatario = cognomeIntestatario;
        this.dataScadenza = Date.valueOf(dataScadenza); //format: yyyy-mm-dd
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "CartaDiCredito{" +
                "numeroCarta='" + numeroCarta + '\'' +
                ", nomeIntestatario='" + nomeIntestatario + '\'' +
                ", cognomeIntestatario='" + cognomeIntestatario + '\'' +
                ", dataScadenza=" + dataScadenza +
                ", username='" + username + '\'' +
                '}';
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public String getNomeIntestatario() {
        return nomeIntestatario;
    }

    public void setNomeIntestatario(String nomeIntestatario) {
        this.nomeIntestatario = nomeIntestatario;
    }

    public String getCognomeIntestatario() {
        return cognomeIntestatario;
    }

    public void setCognomeIntestatario(String cognomeIntestatario) {
        this.cognomeIntestatario = cognomeIntestatario;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }
}
