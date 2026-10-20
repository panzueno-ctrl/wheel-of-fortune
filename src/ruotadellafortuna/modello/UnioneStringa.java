package ruotadellafortuna.modello;

/**
 * Classe di supporto che modella una coppia di stringhe
 * È utilizzata per associare il tema alla frase
 * UnioneStringa
 */
public class UnioneStringa {
    
    /** Conterrà il tema della frase sorteggiata */
    private String tema;

    /** Conterrà la frase sorteggiata */
    private String frase;

    /**
     * Costruttore della classe
     * 
     * @param tema il tema della frase scelta
     * @param frase La frase da indovinare associata ovviamente al tema scelto
     */
    public UnioneStringa(String tema, String frase){
        this.tema = tema;
        this.frase = frase;
    }

    /**
     * Il getter del tema
     * 
     * @return Restituisce il tema
     */
    public String getTema(){
        return tema;
    }

    /**
     * Il getter della frase nascosta
     * 
     * @return Restitutisce la frase nascota
     */
    public String getFrase(){
        return frase;
    }

    
}
