package ruotadellafortuna.eccezionidelgioco;

/**
 * Eccezione personalizzata quindi facente parte delle checked per il tutto il gioco
 * Viene sollevata in presenza di errori specifici causati dalla logica del gioco
 */
public class EccezioniDelGioco extends Exception {
    
    /**
     * Il costruttore di base con il messaggio di errore
     * 
     * @param message Il messaggio che descrive l'errore
     */
    public EccezioniDelGioco(String message){
        super(message);
    }

    /**
     * Costruttore per supportare il concatenamento delle eccezioni
     * 
     * @param message Il messaggio che descrive l'errore
     * @param exception L'eccezione scatenante dell'errore
     */
    public EccezioniDelGioco(String message, Throwable exception){
        super(message, exception);
    }
}
