package boundary;

import control.GestioneNegozio;
import entity.*;
import exception.DAOConnectionException;
import exception.DAOException;
import exception.OperationException;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import static boundary.utilsIO.TerminalIO.*;

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
                long codiceArticolo = -1;
                GestioneNegozio gestioneNegozio = GestioneNegozio.getInstance();
                try{
                        gestioneNegozio.visualizzaArticoli(this);
                }
                catch(DAOException | DAOConnectionException e){
                        System.out.println("Errore visulizzazione articoli.");
                }

                codiceArticolo = askUserLong("Inserire il codice dell'articolo da modificare:");
                try {
                        gestioneNegozio.modificaArticolo(codiceArticolo,this);
                } catch (DAOException | DAOConnectionException | OperationException e) {
                        System.out.println(e.getMessage());
                }
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
                boolean repeatInput = true;

                do{
                        String print = "Vuoi modificare il prezzo dell'articolo?" +
                                "\nValore attuale: "+articolo.getPrezzo()+
                                "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                        String input = askUser(print);
                        try{
                                if(!input.equals("")){
                                        float prezzo = Float.parseFloat(input);
                                        if(prezzo > 0){
                                                articolo.setPrezzo(prezzo);
                                                repeatInput = false;
                                        }
                                }  else{
                                        repeatInput = false;
                                }
                        }catch (NumberFormatException e){
                                System.out.println("Valore non corretto. Riprova.");
                        }

                }while (repeatInput);

                repeatInput = true;
                do{
                        String print = "Vuoi modificare la quantità dell'articolo?" +
                                "\nValore attuale: "+articolo.getQuantitaMagazzino()+
                                "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                        String input = askUser(print);
                        try{
                                if(!input.equals("")){
                                        int quantita = Integer.parseInt(input);
                                        if(quantita >= 0){
                                                articolo.setQuantitaMagazzino(quantita);
                                                repeatInput = false;
                                        }
                                }  else{
                                        repeatInput = false;
                                }
                        }catch (NumberFormatException e){
                                System.out.println("Valore non corretto. Riprova.");
                        }

                }while (repeatInput);
        }
        public void aggiornaCampiProdotto(Prodotto prodotto){
                String print = "Vuoi modificare il nome del prodotto?" +
                        "\nValore attuale: "+prodotto.getNome()+
                        "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                String input = askUserString255(print);
                if(!input.equals("")) prodotto.setNome(input);

                print = "Vuoi modificare la descrizione del prodotto?" +
                        "\nValore attuale: "+prodotto.getDescrizione()+
                        "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                input = askUserString255(print);
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
                                System.out.println("Riprova");
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
                boolean repeatInput = true;
                do{
                        print = "Vuoi modificare la tecnica d'arte del dipinto?";
                        for(int i = 0; i < values.length; i++){
                                print += "\n"+(i+1)+". "+values[i].toString();
                        }
                        print += "\nValore attuale: "+dipinto.getTecnica()+
                                "\nNuovo valore (Selezionare il numero della tecnica desiderata, altrimenti Invio):";
                        input = askUser(print);
                        if(!input.equals("")){
                                try{
                                        scelta = Integer.parseInt(input) - 1;
                                        if(scelta < 0 || scelta >= values.length) System.out.println("Valore inserito non corretto");
                                        else repeatInput = false;
                                }catch (NumberFormatException e){
                                        System.out.println("Valore non corretto. Riprova.");
                                }
                        }
                        else{
                                repeatInput = false;
                        }
                }while(repeatInput);
                if(!input.equals("")) dipinto.setTecnica(values[scelta]);
        }

        public void aggiornaCampiDipinto(Dipinto dipinto){
                aggiornaTecnicaDArte(dipinto);

                boolean repeatInput = true;
                do{
                        String print = "Vuoi modificare la larghezza della tela?" +
                                "\nValore attuale: "+dipinto.getLarghezzaTela()+
                                "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                        String input = askUser(print);
                        try{
                                if(!input.equals("")){
                                        float larghezzaTela = Float.parseFloat(input);
                                        if(larghezzaTela > 0){
                                                dipinto.setLarghezzaTela(larghezzaTela);
                                                repeatInput = false;
                                        }
                                }  else{
                                        repeatInput = false;
                                }
                        }catch (NumberFormatException e){
                                System.out.println("Valore non corretto. Riprova.");
                        }
                }while (repeatInput);

                repeatInput = true;
                do{
                        String print = "Vuoi modificare l'altezza della tela?" +
                                "\nValore attuale: "+dipinto.getAltezzaTela()+
                                "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                        String input = askUser(print);
                        try{
                                if(!input.equals("")){
                                        float altezzaTela = Float.parseFloat(input);
                                        if(altezzaTela > 0){
                                                dipinto.setAltezzaTela(altezzaTela);
                                                repeatInput = false;
                                        }
                                }  else{
                                        repeatInput = false;
                                }
                        }catch (NumberFormatException e){
                                System.out.println("Valore non corretto. Riprova.");
                        }
                }while (repeatInput);
        }
        public void aggiornaCampiScultura(Scultura scultura){
                boolean repeatInput = true;
                do{
                        String print = "Vuoi modificare il peso della scultura?" +
                                "\nValore attuale: "+scultura.getPeso()+
                                "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                        String input = askUser(print);
                        try{
                                if(!input.equals("")){
                                        float peso = Float.parseFloat(input);
                                        if(peso > 0){
                                                scultura.setPeso(peso);
                                                repeatInput = false;
                                        }
                                }  else{
                                        repeatInput = false;
                                }
                        }catch (NumberFormatException e){
                                System.out.println("Valore non corretto. Riprova.");
                        }
                }while (repeatInput);

                repeatInput = true;
                do{
                        String print = "Vuoi modificare l'altezza della scultura?" +
                                "\nValore attuale: "+scultura.getAltezza()+
                                "\nNuovo valore (Premere Invio se non si vuole modificare il campo):";
                        String input = askUser(print);
                        try{
                                if(!input.equals("")){
                                        float altezza = Float.parseFloat(input);
                                        if(altezza > 0){
                                                scultura.setAltezza(altezza);
                                                repeatInput = false;
                                        }
                                }  else{
                                        repeatInput = false;
                                }
                        }catch (NumberFormatException e){
                                System.out.println("Valore non corretto. Riprova.");
                        }
                }while (repeatInput);
        }
}
