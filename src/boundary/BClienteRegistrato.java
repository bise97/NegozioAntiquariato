package boundary;

import control.GestioneNegozio;
import entity.TecnicaDArte;
import java.io.File;
import java.util.ArrayList;
import static boundary.utilsIO.TerminalIO.askUser;

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
        String prezzoProposto;
        GestioneNegozio gN = GestioneNegozio.getInstance();

        tipo = inserisciTipo();
        prezzoProposto = askUser("\nInserisci il prezzo proposto di " + tipo + " :");

        gN.inserisciProposta(this.username,tipo, Float.parseFloat(prezzoProposto),this);
    }
    public ArrayList<Object> inserisciScultura(){
        String peso;
        String altezza;
        ArrayList<Object> lista = new ArrayList<>();

        peso = askUser("\nInserisci il peso della scultura in KG: ");
        altezza = askUser("\nInserisci l'altezza in cm: ");

        lista.add(Float.parseFloat(peso));
        lista.add(Float.parseFloat(altezza));

        return lista;
    }

    public ArrayList<Object> inserisciDipinto(){
        TecnicaDArte tecnica;
        String larghezzaTela;
        String altezzaTela;
        ArrayList<Object> lista = new ArrayList<>();

        tecnica = inserisciTecnicaDArte();
        larghezzaTela = askUser("\nInserisci la larghezza del dipinto in cm: ");
        altezzaTela = askUser("\nInserisci l'altezza del dipinto in cm: ");

        lista.add(tecnica);
        lista.add(Float.parseFloat(larghezzaTela));
        lista.add(Float.parseFloat(altezzaTela));

        return lista;
    }
    public ArrayList<Object> inserisciProdotto(){
        String nome;
        String descrizione;
        ArrayList<File> pathImmagini = new ArrayList<>();
        int num;
        File file;

        ArrayList<Object> lista = new ArrayList<>();

        nome = askUser("\nInserisci il nome: ");
        descrizione = askUser("\nInserisci la descrizione: ");
        num = Integer.parseInt(askUser("\nNumero di immagini: "));
        while(num>4){
            num = Integer.parseInt(askUser("\nInserisci un numero di immagini non superiore a 4: "));
        }
        for (int i=0; i<num;i++){
            file = new File(askUser("\nInserisci il path dell' Immagine " + (i+1) + " : "));
            pathImmagini.add(file);
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
                scelta = Integer.parseInt(input) - 1;
                if(scelta < 0 || scelta >= values.length) System.out.println("Valore inserito non corretto");
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
                scelta = Integer.parseInt(input) - 1;
                if(scelta < 0 || scelta >= tipiProdotto.length) System.out.println("Valore inserito non corretto");
            }
        }while(scelta < 0 || scelta >= tipiProdotto.length);
        return tipiProdotto[scelta];
    }

    public void visualizzaProposteCliente(){
        GestioneNegozio gN = GestioneNegozio.getInstance();
        gN.visualizzaProposteCliente(username);
    }
}
