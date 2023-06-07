package entity;

import java.io.File;
import java.util.ArrayList;

public class Dipinto extends Prodotto{
    private float altezzaTela;
    private float larghezzaTela;
    private TecnicaDArte tecnica;

    public Dipinto(String nome, String descrizione, ArrayList<File> pathsImmagini, float altezzaTela, float larghezzaTela, TecnicaDArte tecnica) {
        super(nome, descrizione, pathsImmagini);
        this.altezzaTela = altezzaTela;
        this.larghezzaTela = larghezzaTela;
        this.tecnica = tecnica;
    }

    public float getAltezzaTela() {
        return altezzaTela;
    }

    public void setAltezzaTela(float altezzaTela) {
        this.altezzaTela = altezzaTela;
    }

    public float getLarghezzaTela() {
        return larghezzaTela;
    }

    public void setLarghezzaTela(float larghezzaTela) {
        this.larghezzaTela = larghezzaTela;
    }

    public TecnicaDArte getTecnica() {
        return tecnica;
    }

    public void setTecnica(TecnicaDArte tecnica) {
        this.tecnica = tecnica;
    }
}
