package entity;

public class Proposta {

    private long id;
    private float prezzo;
    private Prodotto prodotto;
    private Cliente cliente;
    private StatoProposta.Stato stato;
    public Proposta(long id, float prezzo, Prodotto prodotto, Cliente cliente){
        this.id = id;
        this.prezzo = prezzo;
        this.stato = StatoProposta.Stato.OFFERTA;
        this.prodotto = prodotto;
        this.cliente = cliente;
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

    public StatoProposta.Stato getStato() {
        return stato;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public void setStato(StatoProposta.Stato stato) {
        this.stato = stato;
    }
}
