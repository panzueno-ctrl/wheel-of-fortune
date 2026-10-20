package ruotadellafortuna.modello;
import java.math.BigDecimal;

/**
 * Rappresenta il singolo spicchio della nostra ruota della fortuna
 * Uno spicchio può due valori: speciale o monetario
 */
public class Spicchi {

    /**
     * Enum TipoSpicchio rappresenta le tipologie che può assumere uno spicchio
     */

    public enum tipoSpicchio{
        
        /** Rappresenta il valore monetario */ SOLDI,
        /** Il giocatore passa il turno al successivo giocatore */ PASSA,
        /** Il giocatore perde il turno e il montepremi totale accumalato */ BANCAROTTA, 
        /** Permette di non perdere il turno in caso di passa(1 volta per round) */ JOLLY, 
        /** Permette do non perdere il turno e tenere i soldi(1 volta per round per un solo giocatore) */ JOLLYPLUS
    }

    private tipoSpicchio tipo;
    private BigDecimal valore;

    /**
     * Costruttore per uno spicchio contenente un valore monetario
     * Il tipo viene impostato di default su SOLDI
     * @param soldi Il valore dello spicchio(valore intero)
     */
    public Spicchi(int soldi){
        this.tipo = tipoSpicchio.SOLDI;
        this.valore = new BigDecimal(soldi);
    }

    /**
     * Costruttore per uno spicchio speciale
     * Il valore viene impostato di default a ZERO
     * @param speciale Il tipo di spicchio speciale(esempio: BANCAROTTA)
     */
    public Spicchi(tipoSpicchio speciale){
        this.tipo = speciale;
        this.valore = BigDecimal.ZERO;
    }

    /**
     * Retituisce il tipo di spicchio
     * 
     * @return Il valore dell'enum di TipoSpicchio
     */
    public tipoSpicchio getTipo(){
        return tipo;
    }

    /**
     * Restituisce il valore monetario estratto dallo spicchio
     * @return Il valore in BigDecimal (0 se lo spicchio è speciale)
     */
    public BigDecimal getValore(){
        return valore;
    }

    
    @Override
    public String toString(){
        if(tipo == tipoSpicchio.SOLDI){
            return String.format("%s €", valore);
        } else if(tipo == tipoSpicchio.JOLLYPLUS){
            return "JOLLY+";
        }
        else {
            return tipo.name();
        }
    }
}
