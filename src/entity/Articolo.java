package entity;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;

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

//    public ArrayList<Object> get(ArrayList<Field> fields){
//        ArrayList<Object> values = new ArrayList<>();
//        try{
//            for(Field f : fields){
//                values.add(f.get(this));
//            }
//        }catch(IllegalAccessException e){
//            System.out.println(e.getMessage());
//        }
//        return values;
//    }
}
