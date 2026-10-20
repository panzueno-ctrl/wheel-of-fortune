package ruotadellafortuna.modello;
import ruotadellafortuna.util.Randomizzatore;

/**
 * RuotaClassica rappresenta il gioco classico "Wheel of Fortune", la configurazione degli spicchi
 * è quella standard(24 spicchi totali contenenti SOLDI, PASSA e BANCAROTTA)
 */
public class RuotaClassica {

    /**
     * Array contenente tutti gli spicchi della ruota
    */
    protected Spicchi[] vincite;

    /**
     * Costruttore della ruota classica(24 spiccchi)
    */
    public RuotaClassica(){
        this.vincite = new Spicchi[]
            {
                new Spicchi(350), new Spicchi(100), new Spicchi(500), new Spicchi(250), new Spicchi(400),
                new Spicchi(Spicchi.tipoSpicchio.BANCAROTTA), new Spicchi(2000), new Spicchi(200), new Spicchi(350),  
                new Spicchi(100), new Spicchi(500), new Spicchi(Spicchi.tipoSpicchio.PASSA),  new Spicchi(300), new Spicchi(150),
                new Spicchi(400), new Spicchi(250), new Spicchi(500), new Spicchi(Spicchi.tipoSpicchio.BANCAROTTA), new Spicchi(200),
                new Spicchi(300), new Spicchi(150), new Spicchi(500), new Spicchi(200), new Spicchi(Spicchi.tipoSpicchio.PASSA)
            }; 
    }

    /**
     * GiraRandom simula la rotazione di una reale ruota della fortuna selezionando uno
     * spicchio casualmente.
     * @return Lo spicchio randomizzato
    */
    public Spicchi giraRandom(){
        int i = Randomizzatore.generatore(this.vincite.length);
        return vincite[i];
    }

   
}
