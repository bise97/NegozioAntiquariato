package entity;

public class Proposta {

    private long id;
    private float prezzo;
    private long codiceProdotto;
    private String username;
    private StatoProposta stato;
    public Proposta(float prezzo, String username, long codiceProdotto){
        this.prezzo = prezzo;
        this.stato = StatoProposta.OFFERTA;
        this.username = username;
        this.codiceProdotto = codiceProdotto;
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "id=" + id +
                ", prezzo=" + prezzo +
                ", codiceProdotto=" + codiceProdotto +
                ", username='" + username + '\'' +
                ", stato=" + stato +
                '}';
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
        return codiceProdotto;
    }

    public void setCodice(long codice) {
        this.codiceProdotto = codice;
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
