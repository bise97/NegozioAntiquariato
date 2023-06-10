package boundary;

import control.GestioneNegozio;
import entity.*;
import java.io.File;
import java.util.ArrayList;

import static boundary.utilsIO.TerminalIO.askUser;

public class BGestore {

        public void visualizzaArticoli(ArrayList<Articolo> articoli, ArrayList<Prodotto> prodotti){
                System.out.println("Articoli del negozio:");
                for(int i = 0 ; i < articoli.size(); i++){
                        Articolo a = articoli.get(i);
                        Prodotto p = prodotti.get(i);

                        String print = "Cod: " + a.getCodiceProdotto() +
                                ", prezzo: " + a.getPrezzo() +
                                ", quantità: " + a.getQuantitaMagazzino() +
                                ", nome: " + p.getNome() +
                                ", descrizione: " + p.getDescrizione();

                        if(p instanceof Dipinto){
                                print += ", tecnica: " + ((Dipinto) p).getTecnica() +
                                        ", altezza tela: " + ((Dipinto) p).getAltezzaTela() +
                                        ", larghezza tela: " + ((Dipinto) p).getLarghezzaTela();
                        }
                        if(p instanceof Scultura){
                                print += ", peso: " + ((Scultura) p).getPeso() +
                                        ", altezza: " + ((Scultura) p).getAltezza();
                        }
                        System.out.println(print);
                }
        }
        private void modificaArticolo(){
                String input;
                long codiceArticolo = -1;
                boolean flagRepeatInput;
                GestioneNegozio gestioneNegozio = GestioneNegozio.getInstance();
                do{
                        try{
                                gestioneNegozio.visualizzaArticoli(this);
                                input = askUser("Inserire il codice dell'articolo da modificare:");
                                codiceArticolo = Long.parseLong(input);
                                if(codiceArticolo < 0){
                                        System.out.println("Valore non valido. Riprova.");
                                        flagRepeatInput = true;
                                }else{
                                        flagRepeatInput = false;
                                }
                        }catch(NumberFormatException e){
                                System.out.println("Valore non valido. Riprova.");
                                flagRepeatInput = true;
                        }
                }while (flagRepeatInput);
                gestioneNegozio.modificaArticolo(codiceArticolo,this);
        }

        public void run(){
                boolean isOn = true;
                while(isOn){
                        String input = askUser("""
                                Benvenuto Gestore!
                                Selezionare l'operazione desiderata:
                                1. Modifica Articolo
                                2. Aggiungi Articolo
                                3. Rimuovi Articolo
                                4. Generazione Report Acquisti
                                5. Generazione Report Vendite
                                6. Visualizzare Proposte Clienti
                                7. Esci""");
                        try{
                                int scelta = Integer.parseInt(input);
                                switch (scelta) {
                                        case 1 -> modificaArticolo();
                                        case 2 -> aggiungiArticolo();
                                        case 3 -> rimuoviArticolo();
                                        case 4 -> generaReportAcquisti();
                                        case 5 -> generaReportVendite();
                                        case 6 -> visualizzaProposte();
                                        case 7 -> isOn = false;
                                        default -> System.out.println("Valore errato. Riprovare.");
                                }
                        }catch(NumberFormatException e){
                                System.out.println("Valore errato. Riprovare.");
                        }

                }
        }

        private void aggiungiArticolo(){
                System.out.println("Coming soon");
        }
        private void rimuoviArticolo(){
                System.out.println("Coming soon");
        }
        private void generaReportAcquisti(){
                System.out.println("Coming soon");
        }
        private void generaReportVendite(){
                System.out.println("Coming soon");
        }
        private void visualizzaProposte(){
                System.out.println("Coming soon");
        }
        private void gestionePropostaDelCliente(){
                System.out.println("Coming soon");
        }

        public void aggiornaCampiArticolo(Articolo articolo){
                String print = "Vuoi modificare il prezzo dell'articolo?" +
                        "\nValore attuale: "+articolo.getPrezzo()+
                        "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                String input = askUser(print);
                if(!input.equals("")) articolo.setPrezzo(Float.parseFloat(input));

                print = "Vuoi modificare la quantità in magazzino?" +
                        "\nValore attuale: "+articolo.getQuantitaMagazzino()+
                        "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                input = askUser(print);
                if(!input.equals("")) articolo.setQuantitaMagazzino(Integer.parseInt(input));
        }
        public void aggiornaCampiProdotto(Prodotto prodotto){
                String print = "Vuoi modificare il nome del prodotto?" +
                        "\nValore attuale: "+prodotto.getNome()+
                        "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                String input = askUser(print);
                if(!input.equals("")) prodotto.setNome(input);

                print = "Vuoi modificare la descrizione del prodotto?" +
                        "\nValore attuale: "+prodotto.getDescrizione()+
                        "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                input = askUser(print);
                if(!input.equals("")) prodotto.setDescrizione(input);

                aggiornaImmaginiProdotto(prodotto);

                if(prodotto instanceof Dipinto) aggiornaCampiDipinto((Dipinto) prodotto);
                if(prodotto instanceof Scultura) aggiornaCampiScultura((Scultura) prodotto);
        }

        private void aggiornaImmaginiProdotto(Prodotto prodotto){
                boolean flagRepeatInput;
                String input;
                do{
                        input = askUser("Vuoi modificare le immagini del prodotto? (y/n)");
                        if(input.equals("y") || input.equals("n")) {
                                flagRepeatInput = false;
                        }else{
                                flagRepeatInput = true;
                                System.out.println("Retry");
                        }
                }while(flagRepeatInput);

                if(input.equals("y")){
                        ArrayList<File> imageFiles = new ArrayList<>();
                        System.out.println("Inserire i paths delle nuove immagini(max.4)");
                        System.out.println("[Premere Invio per terminare]");
                        int i = 0;
                        boolean flagInsertNewPath;
                        do{
                                do{
                                        input = askUser("Inserire path "+(i+1)+":");
                                        if(!input.equals("")){
                                                File f = new File(input);
                                                if(f.canRead()){
                                                        imageFiles.add(f);
                                                        flagRepeatInput = false;
                                                }
                                                else{
                                                        System.out.println("Path non valido! Riprova");
                                                        flagRepeatInput = true;
                                                }
                                        }else{
                                                flagRepeatInput = false;
                                        }
                                }while(flagRepeatInput);
                                if(!input.equals("")){
                                        i = i + 1;
                                        flagInsertNewPath = true;
                                }
                                else{
                                      flagInsertNewPath = false;
                                }
                        }while(i < 4 && flagInsertNewPath);

                        prodotto.setImmaginiFromFiles(imageFiles);
                }

        }

        private void aggiornaTecnicaDArte(Dipinto dipinto){
                TecnicaDArte[] values = TecnicaDArte.values();
                int scelta = -1;
                String input;
                String print;
                do{
                        print = "Vuoi modificare la tecnica d'arte del dipinto?";
                        for(int i = 0; i < values.length; i++){
                                print += "\n"+(i+1)+". "+values[i].toString();
                        }
                        print += "\nValore attuale: "+dipinto.getTecnica()+
                                "\nNuovo valore (Selezionare il numero della tecnica desiderata, altrimenti Invio):";
                        input = askUser(print);
                        if(!input.equals("")){
                                scelta = Integer.parseInt(input) - 1;
                                if(scelta < 0 || scelta >= values.length) System.out.println("Valore inserito non corretto");
                        }
                }while(!input.equals("") && (scelta < 0 || scelta >= values.length));
                if(!input.equals("")) dipinto.setTecnica(values[scelta]);
        }

        public void aggiornaCampiDipinto(Dipinto dipinto){
                aggiornaTecnicaDArte(dipinto);
                String print = "Vuoi modificare l'altezza della tela?" +
                        "\nValore attuale: "+dipinto.getAltezzaTela()+
                        "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                String input = askUser(print);
                if(!input.equals("")) dipinto.setAltezzaTela(Float.parseFloat(input));

                print = "Vuoi modificare la larghezza della tela?" +
                        "\nValore attuale: "+dipinto.getLarghezzaTela()+
                        "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                input = askUser(print);
                if(!input.equals("")) dipinto.setLarghezzaTela(Float.parseFloat(input));
        }
        public void aggiornaCampiScultura(Scultura scultura){
                String print = "Vuoi modificare il peso della scultura?" +
                        "\nValore attuale: "+scultura.getPeso()+
                        "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                String input = askUser(print);
                if(!input.equals("")) scultura.setPeso(Float.parseFloat(input));

                print = "Vuoi modificare l'altezza della scultura?" +
                        "\nValore attuale: "+scultura.getAltezza()+
                        "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                input = askUser(print);
                if(!input.equals("")) scultura.setAltezza(Float.parseFloat(input));

        }
}
