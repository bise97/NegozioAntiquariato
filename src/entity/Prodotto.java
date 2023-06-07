package entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Prodotto {
    private long codice;
    private String nome;
    private String descrizione;
    private ArrayList<Immagine> immagini;

    public Prodotto(String nome, String descrizione, ArrayList<File> pathsImmagini) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagini = new ArrayList<>();
        for(File f : pathsImmagini){
            try{
                immagini.add(new Immagine(f,this));
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Immagine> getImmagini() {
        return immagini;
    }

    public void setImmagini(ArrayList<Immagine> immagini) {
        this.immagini = immagini;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "codice=" + codice +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }

    public long getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setCodice(long codice) {
        this.codice = codice;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
