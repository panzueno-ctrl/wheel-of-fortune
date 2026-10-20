package ruotadellafortuna.modello;

/**
 * RuotaAvanzata è la variante avanzata del gioco classico "Wheel of Fortune" che aggiunge 2 ulteriori spicchi(24->26)
 * chiamati JOLLY e JOLLYPLUS
 */
public class RuotaAvanzata extends RuotaClassica {

    /**
     * Costruttore della ruota avanzata che estende l'array con 
     * JOLLY tra spicchio valore(400) e spicchio speciale(BANCAROTTA) e
     * JOLLYPLUS tra spicchio valore(500) e spicchio speciale(BANCAROTTA)
     */
    public RuotaAvanzata(){
        // chiama il costruttore padre, poi sovrascrive l'array del padre con la versione modificata con JOLLY e JOLLYPLUS
        super();

        this.vincite = new Spicchi[]
            {
                new Spicchi(350), new Spicchi(100), new Spicchi(500), new Spicchi(250), new Spicchi(400), new Spicchi(Spicchi.tipoSpicchio.JOLLY),
                new Spicchi(Spicchi.tipoSpicchio.BANCAROTTA), new Spicchi(2000), new Spicchi(200), new Spicchi(350),  
                new Spicchi(100), new Spicchi(500), new Spicchi(Spicchi.tipoSpicchio.PASSA),  new Spicchi(300), new Spicchi(150),
                new Spicchi(400), new Spicchi(250), new Spicchi(500), new Spicchi(Spicchi.tipoSpicchio.JOLLYPLUS), new Spicchi(Spicchi.tipoSpicchio.BANCAROTTA), 
                new Spicchi(200), new Spicchi(300), new Spicchi(150), new Spicchi(500), new Spicchi(200), 
                new Spicchi(Spicchi.tipoSpicchio.PASSA)
            }; 
    }
    
}
