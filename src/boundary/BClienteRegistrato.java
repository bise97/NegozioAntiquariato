package boundary;

import control.GestioneNegozio;
import entity.Proposta;
import entity.TecnicaDArte;
import exception.OperationException;

import java.io.File;
import java.util.ArrayList;

import static boundary.utilsIO.TerminalIO.*;

public class BClienteRegistrato {

    private String username;
    private final String[] tipiProdotto = {"PRODOTTO","DIPINTO", "SCULTURA"};

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
        float prezzoProposto;
        GestioneNegozio gN = GestioneNegozio.getInstance();

        tipo = inserisciTipo();
        prezzoProposto = askUserFloat("Inserisci il prezzo proposto di " + tipo + " :");

        try {
            gN.inserisciProposta(this.username,tipo, prezzoProposto,this);
            System.out.println("Proposta aggiunta!");
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
    }
    public ArrayList<Object> inserisciScultura(){
        float peso;
        float altezza;
        ArrayList<Object> lista = new ArrayList<>();

        peso = askUserFloat("Inserisci il peso della scultura in KG: ");
        altezza = askUserFloat("Inserisci l'altezza in cm: ");

        lista.add(peso);
        lista.add(altezza);

        return lista;
    }

    public ArrayList<Object> inserisciDipinto(){
        TecnicaDArte tecnica;
        float larghezzaTela;
        float altezzaTela;
        ArrayList<Object> lista = new ArrayList<>();

        tecnica = inserisciTecnicaDArte();
        larghezzaTela = askUserFloat("Inserisci la larghezza del dipinto in cm: ");
        altezzaTela = askUserFloat("Inserisci l'altezza del dipinto in cm: ");

        lista.add(tecnica);
        lista.add(larghezzaTela);
        lista.add(altezzaTela);

        return lista;
    }
    public ArrayList<Object> inserisciProdotto(){
        String nome;
        String descrizione;
        ArrayList<File> pathImmagini = new ArrayList<>();
        int num;
        File file;
        boolean repeatFile = true;

        ArrayList<Object> lista = new ArrayList<>();

        nome = askUserString255("Inserisci il nome: ");
        descrizione = askUserString255("Inserisci la descrizione: ");
        num = askUserInt("Numero di immagini: ");
        while(num>4){
            num = askUserInt("Inserisci un numero di immagini non superiore a 4: ");
        }
        for (int i=0; i<num;i++){
            do {
                    file = new File(askUserString255("Inserisci il path dell' Immagine " + (i + 1) + " : "));
                    if(file.canRead()) {
                        pathImmagini.add(file);
                        repeatFile = false;
                    }else{
                        System.out.println("Immagine non trovata!");
                    }
            }while (repeatFile);
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

            option = askUser(
                    "\nBenvenuto " + username + " scegli una funzionalita' da eseguire: " +
                    "\n1. Visualizza Articoli in Negozio " +
                    "\n2. Visualizza Carrello " +
                    "\n3. Inserisci una nuova Proposta di vendita " +
                    "\n4. Visualizza le tue Proposte di vendita" +
                    "\n5. Esci ");

            switch (option) {
                case "1", "2" -> System.out.println("Funzionalita non ancora disponibile!");
                case "3" -> inserisciProposta();
                case "4" -> visualizzaProposteCliente();
                case "5" -> {
                    f = false;
                    username = null;
                }
                default -> System.out.println("Opzione non disponibile!");
            }
        }
    }

    private TecnicaDArte inserisciTecnicaDArte(){
        TecnicaDArte[] values = TecnicaDArte.values();
        int scelta = -1;
        String input;
        StringBuilder print;
        do{
            print = new StringBuilder("Selezionare il numero della tecnica desiderata:");
            for(int i = 0; i < values.length; i++){
                print.append("\n").append(i + 1).append(". ").append(values[i].toString());
            }
            input = askUser(print.toString());
            if(!input.equals("")){
                try {
                    scelta = Integer.parseInt(input) - 1;
                    if (scelta < 0 || scelta >= values.length) System.out.println("Valore inserito non corretto");
                }catch (NumberFormatException e){
                    System.out.println("Valore inserito non corretto");
                }
            }
        }while(scelta < 0 || scelta >= values.length);
        return values[scelta];
    }


    public String inserisciTipo(){
        int scelta = -1;
        String input;
        StringBuilder print;
        do{
            print = new StringBuilder("Selezionare il numero del tipo di prodotto:");
            for(int i = 0; i < tipiProdotto.length; i++){
                print.append("\n").append(i + 1).append(". ").append(tipiProdotto[i]);
            }
            input = askUser(print.toString());
            if(!input.equals("")){
                try {
                    scelta = Integer.parseInt(input) - 1;
                    if(scelta < 0 || scelta >= tipiProdotto.length) System.out.println("Valore inserito non corretto");
                }catch (NumberFormatException e){
                    System.out.println("Valore inserito non corretto");
                }
            }
        }while(scelta < 0 || scelta >= tipiProdotto.length);
        return tipiProdotto[scelta];
    }

    public void visualizzaProposteCliente(){
        ArrayList<Proposta> proposte = new ArrayList<>();
        GestioneNegozio gN = GestioneNegozio.getInstance();
        proposte = gN.visualizzaProposteCliente(username);
        for(Proposta p: proposte){
            System.out.println(p);
        }
    }
}
