package ruotadellafortuna.coredelgioco;

import ruotadellafortuna.util.Input;
import ruotadellafortuna.modello.Frase;
import ruotadellafortuna.modello.Giocatore;

/**
 * Gestisce la logica della fase finale del gioco in cui solo il campione partecipa con la possibilità
 * di raddopiare il montepremi
 * 
 */

public class FaseFinale {
    /** La frase che verrà nascosta */
    private Frase fraseFinale;
    /** È il giocatore col monte premi più alto */
    private Giocatore campione;
    /** Serve a gestire l'input da tastiera */
    private Input giocatoriIn;

    /**
     * Costruttore della fase finale
     * 
     * @param fraseFinale La frase difficile creata apposta per questa fase
     * @param campione Il giocatore con il montepremi più alto
     * @param giocatoriIn Serve per leggere gli input
     */
    public FaseFinale(Frase fraseFinale, Giocatore campione, Input giocatoriIn){
        this.fraseFinale = fraseFinale;
        this.campione = campione;
        this.giocatoriIn = giocatoriIn;
    }

    /**
     * Avvia il round della fase finale
     */
    public void gioca(){
        System.out.println("\n|| --- FASE FINALE ---");
        System.out.printf("|| Finalista: %s | %s", campione.getNome(), campione.getMontepremi());

        // N,R,T,E vengono fornite in omaggio se presenti nella frase
        char[] lettereOmaggio = {'N', 'R', 'T', 'E'};
        for(char c : lettereOmaggio){
            fraseFinale.scopriLettera(c);
        }

        System.out.printf("|| Board: (%s) %s", fraseFinale.getTema(), fraseFinale.getVisualizzaFrase());

        char cons1 = leggiConsonante(1);
        char cons2 = leggiConsonante(2);
        char cons3 = leggiConsonante(3);
        char vocale = leggiVocale();

        fraseFinale.scopriLettera(cons1);
        fraseFinale.scopriLettera(cons2);
        fraseFinale.scopriLettera(cons3);
        fraseFinale.scopriLettera(vocale);

        System.out.printf("|| Board finale: (%s) %s", fraseFinale.getTema(), fraseFinale.getVisualizzaFrase());

        String tentativo = giocatoriIn.leggiStringa("Scrivi la frase");
        tentativo = tentativo.toUpperCase();

        if(tentativo.equals(fraseFinale.getFraseOriginale())){
            campione.raddoppiaMontepremiTotale();
            System.out.println("|| Corretto hai raddopiato il montepremi!");
        } else {
            System.out.printf("|| Sbagliato la frase corretta era %s\n", fraseFinale.getFraseOriginale());
            System.out.printf("|| Complimen ti hai comunque vinto %.2f\n", campione.getValoreMontepremi());
        }

        System.out.println("|| GAME OVER");

    }
    /**
     * metodo privato di supporto per inserimento consonanti
     * 
     * @param numero Il numero della consonante inserita
     * @return Restituisce la consonante selezionata
     */
    private char leggiConsonante(int numero){
        char consonante = '\0';
        boolean inputValido = false;
        
        while (!inputValido) {
            consonante = giocatoriIn.leggiLettera("Scegli la consonante n. " + numero);
            if (consonante == 'A' || consonante == 'E' || consonante == 'I' || consonante == 'O' || consonante == 'U') {
                System.out.println("|| Errore, devi inserire una consonante, non una vocale!");
            } else {
                inputValido = true;
            }
        }
        return consonante;
    }

    /**
     * Metodo di supporto per l'inseriemento della vocale
     * 
     * @return Restituisce la vocale selezionata
     */
    private char leggiVocale() {
        char vocale = '\0';
        boolean inputValido = false;
        
        while (!inputValido) {
            vocale = giocatoriIn.leggiLettera("Scegli la vocale");
            if (vocale == 'A' || vocale == 'E' || vocale == 'I' || vocale == 'O' || vocale == 'U') {
                inputValido = true;
            } else {
                System.out.println("|| Errore, devi inserire una vocale!");
            }
        }
        return vocale;
    }

}
