package ruotadellafortuna.util;

import java.util.Random;
/**
 * Randomizzatore è la classe usata per gestire il randomizzatore
 * in tutte le parti del gioco. È possibile inserire un seed per rendere una 
 * partita ripetibile
 */

public class Randomizzatore {
    /** L'unico generatore casuale di numeri per tutto il gioco */
    private static Random rand;

    /**
     * Costruttore vuoto
     */
    public Randomizzatore() {

    }

    /**
     * Inizializzazione serve a inizializzare il generatore di numeri casuali.
     * È chiamato una sola volta all'avvio del main
     * 
     * @param seed Se il seed è null la sequenza di generazione sarà casuale.
     */

    public static void inizializzazione(Integer seed){ //Integer è indispensabile essendo che i tipi primitivi non possono essere NULL!
        if(seed != null){
            rand = new Random(seed);
        } else {
            rand = new Random();
        }
    }

    /**
     * Restituisce un numero casuale tra 0 e il limite impostato(questo è escluso)
     * 
     * @param limite Il numero più altro randomizzabile
     * @return Un numero casuale tra 0 e limite - 1
     */

    public static int generatore(int limite){
        if(rand == null){
            rand = new Random(); // nel caso nel main ci si dimentichi di inizializzarlo
        }
        return rand.nextInt(limite);
    }

    /**
     * Restituisce l'istanza del randomizzatore
     * 
     * @return Restituisce l'oggetto Random
     */
    public static Random getRand(){
        if(rand == null) inizializzazione(null);
        return rand;
    }


}
