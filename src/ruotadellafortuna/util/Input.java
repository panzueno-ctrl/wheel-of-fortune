package ruotadellafortuna.util;
import java.util.*;

/**
 * Input è la classe per gestire l'input da tastiera lungo tutto il gioco.
 * Include anche la validazione dell'input per prevenire il crash lungo il gioco
 * a seguito di input non validi
 */
public class Input {
    /**
     * Utilizzato per leggere tutti gli input da tastiera
     */
    public Scanner in = new Scanner(System.in);

    /** 
     * Costruttore vuoto 
    */
    public Input(){

    }

    /**
     * Mostra il messaggio allegato alla richiesta di input
     * 
     * @param messaggio Il messaggio mostrato al giocatore
     * @return restituisce l'input da tastiera dell'utente
     */
    public String leggiStringa(String messaggio){
        while(true){
            System.out.print(messaggio + ": ");
            String input = in.nextLine().trim();
            if(!input.isEmpty()){
                return input;
            }
            System.out.println("Errore, l'input non può essere vuoto");
        }
    }

    /**
     * Serve a mostrare il messaggio che servirà a far capire che input è richiesto
     * @param messaggio Il messaggio mostrato al giocatore
     * @return Restituisce l'input da tastiera del giocatore in maiuscolo
     */
    public char leggiLettera(String messaggio){
        while(true){
            String input = leggiStringa(messaggio);
            if(input.length() == 1 && Character.isLetter(input.charAt(0))){
                return Character.toUpperCase(input.charAt(0));
            }
            System.out.println("Errori, devi inserire solo una lettera");
        }
    }

    /**
     * Mostra il messaggio allegato alla richiesta di input
     * 
     * @param messaggio prende l'input da tastiera dell'utente, in questo caso prende un intero
     * @return restituisce l'input da tastiera dell'utente
     */
    public int intero(String messaggio){
        while(true){
            System.out.println(messaggio + " ");
            try{
                String letto = in.nextLine();
                int input = Integer.parseInt(letto);
                return input;
            } catch(NumberFormatException e){
                System.out.println("Errore puoi solo inserire un numero intero(senza virgola)");
            }   
        }
    }
    
}
