package ruotadellafortuna.modello;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Rappresenta un partecipante al gioco
 * Gestisce: il montepremi totale, quello del singolo round e i jolly accumulati dai giocatori
 */
public class Giocatore {

    /** Sono il nome dei giocatori */
    private String nome;

    /** Il montepremi totale accumulato */
    private BigDecimal montepremi;

    /** Il montepremi accumulato durante il round */
    private BigDecimal montepremiRound;
    
    /** Indica il numero del giocatore */
    private int numGiocatore;

    /** Indica se il giocatore ha posseduto un jolly durante il round */
    private boolean possiedeJolly = false;

    /** Indica se il giocatore possiede JollyPlus */
    private boolean possiedeJollyPlus = false;

    /**
     * Costruttore per un nuovo giocatore. Iniziallizza il montepremi a 0
     * 
     * @param nome Il nome del giocatore
     * @param numGiocatore Il numero identificativo del giocatore
     */
    public Giocatore(String nome, int numGiocatore){
        this.nome = nome;
        this.montepremi = new BigDecimal("0.00");
        this.montepremiRound = new BigDecimal("0.00");
        this.numGiocatore = numGiocatore;
    }

    /**
     * Restituisce l'identificativo del giocatore
     * 
     * @return Il numero del giocatore
     */
    public int getNumGioc(){
        return numGiocatore;
    }

    /**
     * Restituisce il nome del giocatore
     * 
     * @return Il nome del giocatore
     */
    public String getNome(){
        return nome;
    }


    /**
     * Restituisce in formato stringa il montepremi totale del giocatore
     * 
     * @return Stringa formattata del montepremi totale
     */
    public String getMontepremi(){
        return String.format("Monte premi totali: €%.2f", montepremi);
    }

    /**
     * Restituisce in formato stringa il montepremi del round in corso del giocatore
     * 
     * @return Stringa formattata del montepremi del round attuale
     */
    public String getMontepremiRound(){
        return String.format("Monte premi round attuale: €%.2f", montepremiRound);
    }

    /**
     * Restituisce il valore numerico del montepremi del round in modo da utlizzarlo per la logica del pagamento della
     * vocale
     * 
     * @return Restituisce il montepremi del round
     */
    public BigDecimal getValoreMontepremiRound(){
        return this.montepremiRound;
    }

    /**
     * Restituisce il valore numerico del montepremi totale
     * 
     * @return Restituisce il montepremi del round
     */
    public BigDecimal getValoreMontepremi(){
        return this.montepremi;
    }

    /**
     * Azzera tutto il montepremi a seguito se si vuole fare una nuova partita con gli stessi giocatori
     */
    public void perdeMontepremi(){
        this.montepremi = new BigDecimal("0.00");
        this.montepremiRound = new BigDecimal("0.00");
    }

    /**
     * Azzera montepremi round a seguito di BANCAROTTA(unica eccezione è se si ha SUPERJOLLY)
     */
    public void perdeMontepremiRound(){
        this.montepremiRound = new BigDecimal("0.00");
    }

    
    /**
     * Trasferisce il montepremi del round sul montepremi totale in caso di vincita del giocatore
     */
    public void vinceRound(){
       this.montepremi = arrotondaSoldi(this.montepremi.add(this.montepremiRound));
       this.montepremiRound = new BigDecimal("0.00");

    }

    /**
     * Sottrae i soldi del montepremi per pagare la vocale
     * 
     * @param costo Il costo della vocale
     */
    public void pagaVocale(BigDecimal costo){
        this.montepremiRound = arrotondaSoldi(this.montepremiRound.subtract(costo));
    }

    /**
     * Se esce uno spicchio SOLDI viene sommato al montepremi del round
     * @param x La cifra da aggiungere
     * @param occorrenze Indica il numero di volte che è comparsa la lettera indovinata
    */
    public void guadagnaSoldiRound(BigDecimal x, int occorrenze){
        // Moltiplica il guadagno di soldi per il numero delle occorrenze che il giocatore ha beccato con la lettera
        BigDecimal soldiTotaliVinti = x.multiply(new BigDecimal(occorrenze));
        this.montepremiRound = arrotondaSoldi(this.montepremiRound.add(soldiTotaliVinti));
    }

    /**
     * Serve per raddoppiare il montepremi se si supera la fase finale
     */
    public void raddoppiaMontepremiTotale(){
        this.montepremi = arrotondaSoldi(this.montepremi.multiply(new BigDecimal("2.00")));
    }

    /**
     * Serve per arrotondare alla seconda cifra decimale
     * 
     * @param valore È il valore da arrotondare
     * @return Il valore arrotondato
     */
    public BigDecimal arrotondaSoldi(BigDecimal valore){
        return valore.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * verifica se il giocatore possiede un JOLLY
     * 
     * @return True Se il giocatore possiede il jolly
     */

    public boolean haJolly(){
        return possiedeJolly;
    }

    /**
     * Se esce lo spicchio JOLLY viene impostato su true
     * 
     * @param possiedeJolly Aseegnazione true in caso di uscita di JOLLY
     */
    public void possiedeJolly(boolean possiedeJolly){
        this.possiedeJolly = possiedeJolly;
    }

    /**
     * Verifica se il giocatore possiede il JOLLYPLUS
     * 
     * @return possiedeJollyPlus Restituisce true se il giocatore ha JOLLYPLUS
     */
    public boolean haJollyPlus(){
        return possiedeJollyPlus;
    }

    /**
     * Se esce lo spicchio JOLLYPLUS viene impostato su true
     * 
     * @param possiedeJollyPlus Assegnazione true in caso di uscita di JOLLYPLUS
     */
    public void possiedeJollyPlus(boolean possiedeJollyPlus){
        this.possiedeJollyPlus = possiedeJollyPlus;
    }
    
    @Override
    public String toString(){
        return String.format("%s %s %d", this.nome, this.numGiocatore);
    }
}
