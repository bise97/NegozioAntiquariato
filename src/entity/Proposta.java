package entity;

public class Proposta {

    private long id;
    private float prezzo;
    private long codice;
    private String username;
    private StatoProposta stato;
    public Proposta(float prezzo, String username, long codice){
        this.prezzo = prezzo;
        this.stato = StatoProposta.OFFERTA;
        this.username = username;
        this.codice = codice;
    }

    @Override
    public String toString(){
        String print = "ID: "+id +
                "\nPrezzo: "+prezzo+
                "\nStato: "+stato;
        return print;
    }
    public long getId() {
        return id;
    }

    public float getPrezzo() {
        return prezzo;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public long getCodice() {
        return codice;
    }

    public void setCodice(long codice) {
        this.codice = codice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public StatoProposta getStato() {
        return stato;
    }

    public void setStato(StatoProposta stato) {
        this.stato = stato;
    }
}
