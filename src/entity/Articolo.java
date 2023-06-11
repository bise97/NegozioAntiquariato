package entity;

import exception.OperationException;

public class Articolo {
    private float prezzo;
    private int quantitaMagazzino;
    private final long codiceProdotto;

    public Articolo(float prezzo, int quantitaMagazzino, long codiceProdotto) throws OperationException{
        this.prezzo = prezzo;
        this.quantitaMagazzino = quantitaMagazzino;
        if(codiceProdotto < 0){
            throw new OperationException("Non puoi creare un articolo prima di avere caricato il prodotto sul database.");
        }else{
            this.codiceProdotto = codiceProdotto;
        }
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

    @Override
    public String toString() {
        return "Articolo{" +
                "prezzo=" + this.getPrezzo() +
                ", quantitaMagazzino=" + this.getQuantitaMagazzino() +
                ", codiceProdotto=" + this.getCodiceProdotto() +
                "}";
    }

}
