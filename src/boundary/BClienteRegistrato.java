package boundary;

import control.GestioneNegozio;
import entity.Immagine;
import entity.TecnicaDArte;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class BClienteRegistrato {

    private String username;

    public BClienteRegistrato(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void inserisciProposta(){
        String tipo;
        String prezzoProposto;
        GestioneNegozio gN = GestioneNegozio.getInstance();

        BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("\nInserisci il tipo del prodotto da inserire: ");
            tipo = bufferedRead.readLine().toUpperCase();
            System.out.println("\nInserisci il prezzo proposto del prodotto da inserire: ");
            prezzoProposto = bufferedRead.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gN.inserisciProposta(this.username,tipo, Float.parseFloat(prezzoProposto),this);

    }
    public ArrayList<Object> inserisciScultura(){
        String peso;
        String altezza;
        ArrayList<Object> lista = new ArrayList<Object>();
        BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("\nInserisci il peso: ");
            peso = bufferedRead.readLine();
            System.out.println("\nInserisci l'altezza: ");
            altezza = bufferedRead.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lista.add(Float.parseFloat(peso));
        lista.add(Float.parseFloat(altezza));

        return lista;
    }

    public ArrayList<Object> inserisciDipinto(){
        String tecnica;
        String larghezzaTela;
        String altezzaTela;
        ArrayList<Object> lista = new ArrayList<Object>();
        BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("\nInserisci la tecnica: ");
            tecnica = bufferedRead.readLine();
            System.out.println("\nInserisci la larghezza: ");
            larghezzaTela = bufferedRead.readLine();
            System.out.println(("\nInserisci l'altezza: "));
            altezzaTela = bufferedRead.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lista.add(TecnicaDArte.valueOf(tecnica));
        lista.add(Float.parseFloat(larghezzaTela));
        lista.add(Float.parseFloat(altezzaTela));

        return lista;
    }
    public ArrayList<Object> inserisciProdotto(){
        String nome;
        String descrizione;
        ArrayList<File> pathImmagini = new ArrayList<>();
        int num;
        File file = null;

        ArrayList<Object> lista = new ArrayList<Object>();

        BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("\nInserisci il nome: ");
            nome = bufferedRead.readLine();
            System.out.println("\nInserisci la descrizione: ");
            descrizione = bufferedRead.readLine();
            System.out.println("\nNumero di immagini: ");
            num = Integer.parseInt(bufferedRead.readLine());
            while(num>4){
                System.out.println(("\nInserisci un numero di immagini non superiore a 4: "));
                num = Integer.parseInt(bufferedRead.readLine());
            }
            for (int i=0; i<num;i++){
                System.out.println("\nInserisci il path dell' Immagine " + (i+1) + " : ");
                file = new File(bufferedRead.readLine());
                pathImmagini.add(file);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lista.add(nome);
        lista.add(descrizione);
        lista.add(pathImmagini);

        return lista;
    }

    public void run(){
        boolean f = true;
        String option;

        while(f){

            System.out.println("UTENTE: " + username +
                    "\n\nScegli una funzionalita' da eseguire: " +
                    "\n1. Visualizza Articoli in Negozio " +
                    "\n2. Visualizza Carrello " +
                    "\n3. Inserisci una nuova Proposta di vendita " +
                    "\n4. Esci ");

            BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(System.in));
            try {
                option = bufferedRead.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            switch (option){
                case "1":
                    System.out.println("Funzionalita non ancora disponibile!");
                    break;
                case "2":
                    System.out.println("Funzionalita non ancora disponibile!");
                    break;
                case "3":
                    inserisciProposta();
                    break;
                case "4":
                    f = false;
                    break;
                default:
                    System.out.println("Opzione non disponibile!");
            }
        }
    }
}
