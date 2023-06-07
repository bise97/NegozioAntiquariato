package entity;

public class Articolo {
    private float prezzo;
    private int quantitaMagazzino;
    private final long codiceProdotto;

    public Articolo(float prezzo, int quantitaMagazzino, long codiceProdotto) {
        this.prezzo = prezzo;
        this.quantitaMagazzino = quantitaMagazzino;
        this.codiceProdotto = codiceProdotto;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantitaMagazzino() {
        return quantitaMagazzino;
    }

    public void setQuantitaMagazzino(int quantitaMagazzino) {
        this.quantitaMagazzino = quantitaMagazzino;
    }

    public long getCodiceProdotto() {
        return codiceProdotto;
    }

//    public void setProdotto(Prodotto prodotto) {
//        this.prodotto = prodotto;
//    }
}
