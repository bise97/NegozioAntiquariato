package entity;

import java.io.File;
import java.util.ArrayList;

public class Scultura extends Prodotto {

    private float peso;
    private float altezza;

    public Scultura(String nome, String descrizione, ArrayList<File> pathsImmagini, float peso, float alezza) {
        super(nome, descrizione, pathsImmagini);
        this.peso = peso;
        this.altezza = alezza;
    }

    public  Scultura(Prodotto prodotto, float peso, float alezza){
        super(prodotto.getImmagini(),prodotto.getNome(),prodotto.getDescrizione());
        this.peso = peso;
        this.altezza = alezza;
    }
    @Override
    public String toString() {
        return "Scultura{" +"codice=" + this.getCodice() +
                ", nome='" + this.getNome() + '\'' +
                ", descrizione='" + this.getDescrizione() + '\'' +", "+
                "peso=" + peso + " KG" +
                ", alezza=" + altezza + " cm " +
                '}';
    }

    public float getPeso() {
        return peso;
    }

    public float getAltezza() {
        return altezza;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setAltezza(float alezza) {
        this.altezza = alezza;
    }
}
