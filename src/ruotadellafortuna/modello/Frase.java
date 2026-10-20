package ruotadellafortuna.modello;

/**
* Classe per mascherare la frase e gestire i caratteri scelti per mostrarla.
*/
public class Frase {

    /** Serve a contenere la coppia tema-frase */ 
    private UnioneStringa dati; 

    /** La frase che vedranno i giocatori lungo il round, quindi quella con gli underscore(_) */
    private char[] nascosta;

    /**
     * Costruttore per inizializzare i parametri della classe e inizializzare l'array che tiene la frase oscurata
     * 
     * @param dati È la coppia tema-frase
     */
    public Frase(UnioneStringa dati){
        this.dati = dati;   
        
        String fraseOriginale = dati.getFrase().toUpperCase();
        this.nascosta = new char[fraseOriginale.length()];

        //Oscuramento della frase
        for(int i = 0; i < fraseOriginale.length(); i++){
            char c = fraseOriginale.charAt(i);

            if(Character.isLetter(c)){
                this.nascosta[i] = '_';
            } else {
                this.nascosta[i] = c;
            }
        }
    }

    /**
     * Metodo per visualizzare la frase oscurata, a ogni completamento viene aggiornata
     * 
     * @return restituisce la frase oscurata/semi completata
     */
    public String getVisualizzaFrase(){
        String out = "";
        for(char c : nascosta){
            out += c + " ";
        }
        return out;
    }

    /**
     * Metodo per confrontare se il carattere scelto dal giocatore è contenuto nella frase
     * 
     * @param lettera E' il carattere scelto dal giocatore
     * @return restituisce il numero di occorrenze corrispondenti al carattere
     */
    public int scopriLettera(char lettera){
        lettera = Character.toUpperCase(lettera);
        String fraseOriginale = dati.getFrase().toUpperCase();
        int contatore = 0;

        for(int i = 0; i < fraseOriginale.length(); i++){
            if(fraseOriginale.charAt(i) == lettera && nascosta[i] == '_'){
                nascosta[i] = lettera;
                contatore++;
            }
        }
        return contatore;
    }

    /**
     * Metodo che controlla se la frase è completa
     * 
     * @return Restituisce true se è completa, false e non lo è
     */
    public boolean isCompleta(){
        for(char c : nascosta){
            if(c == '_'){
                return false;
            }
        }
        return true;
    }

    /**
     * Serve a controllare se la frase nascosta ha solo più vocali da trovare
     * 
     * @return Restituisce true se mancano solo vocali o false se ci sono ancora consonanti da indovinare
     */
    public boolean mancanoSoloVocali(){
        String fraseOriginale = dati.getFrase().toUpperCase();
        for(int i = 0; i < fraseOriginale.length(); i++){
            if(nascosta[i] == '_'){
                char c = fraseOriginale.charAt(i);
                if (c != 'A' && c != 'E' && c != 'I' && c != 'O' && c != 'U') {
                return false;
                }
            }
        }
        return true;
    }

    /**
     * Metodo che restituisce il tema della frase scelta
     * 
     * @return Restituisce la frase scelta
     */
    public String getTema(){
        return dati.getTema();
    }

    /**
     * Metedo che restituisce la frase originale senza essere nascosta
     * @return Restituisce la frase
     */
    public String getFraseOriginale(){
        return dati.getFrase().toUpperCase();
    }
}
