package ruotadellafortuna.coredelgioco;
import java.math.BigDecimal;
import java.util.ArrayList;
import ruotadellafortuna.util.Input;
import ruotadellafortuna.modello.Frase;
import ruotadellafortuna.modello.Giocatore;
import ruotadellafortuna.modello.Spicchi;
import ruotadellafortuna.modello.RuotaClassica;

/**
 * Gestisce la logica del singolo round del gioco, si occupa di far girare i turni dei giocatori,
 * girare la ruota e gestire acquisti di vocali e verificare la soluzione della frase
 * 
 */
public class Round {
    
    /** È il numero del round attuale della partita */
    private int numeroRound;

    /** È la frase nascosta che viene tratteggiata e scoperta piano piano */
    private Frase fraseNascosta;

    /** È l'array dei giocatori partecipanti */
    private Giocatore[] giocatori;

    /** È la ruota della fortuna, può essere la classica o la versione estesa */
    private RuotaClassica ruotaFortuna;

    /** Serve a gestire l'input da tastiera */
    private Input giocatoriIn;

    /** È una lista che tiene tutte le lettere inserite dai giocatori nel round */ 
    private ArrayList<Character> lettereScritteDaiGiocatori;

    /** Indica se il JOLLY+ è già stato assegnato a un giocatore in questo round */
    private boolean jollyPlusAssegnatoNelRound;

    /** Lista dei giocatori che hanno posseduto un jolly lungo il round */
    private ArrayList<Giocatore> giocatoriConJolly;


    /**
     * Costruttore di classe
     * @param numeroRound Identifica il numero del round attuale
     * @param fraseNascosta È la frase nascosta estratta, ovviamente in questo caso è l'oggetto frase
     * @param giocatori La lista dei giocatori nel turno
     * @param ruotaFortuna La ruota della fortuna della partita attuale, può essere classica o avanzata.
     * @param giocatoriIn Serve a leggere l'input dei giocatori
     */
    public Round(int numeroRound, Frase fraseNascosta, Giocatore[] giocatori, RuotaClassica ruotaFortuna, Input giocatoriIn){
        this.numeroRound = numeroRound;
        this.fraseNascosta = fraseNascosta;
        this.giocatori = giocatori;
        this.ruotaFortuna = ruotaFortuna;
        this.giocatoriIn = giocatoriIn;
        this.jollyPlusAssegnatoNelRound = false;
        this.giocatoriConJolly = new ArrayList<>();
        this.lettereScritteDaiGiocatori = new ArrayList<>();



        /*
        I giocatori devono avere il montepremi round azzerato, per questo viene fatto nel costruttore
        Questo vale anche per i jolly anche se non si gioca nella versione avanzata
        */
        for(Giocatore g : giocatori){
            g.perdeMontepremiRound();
            g.possiedeJolly(false);
            g.possiedeJollyPlus(false);
        }
        
    }

    /**
     * Avvia il round o la fase finale
     * 
     * @return Restituisce il vincitore
     */
    public Giocatore gioca(){
        System.out.printf("\n|| *** ROUND %d ***\n", numeroRound);
        
        boolean roundFinito = false;
        int indiceTurnoAttuale = 0;

        Giocatore vincitore = null;

        while(roundFinito != true){
            Giocatore turnoGiocatore = giocatori[indiceTurnoAttuale];
            boolean mantieneTurno = giocaTurnoSuccessivo(turnoGiocatore);

            if(fraseNascosta.isCompleta()){
                roundFinito = true;
                vincitore = turnoGiocatore;
                break;
            }

            if(!mantieneTurno){
                indiceTurnoAttuale = (indiceTurnoAttuale + 1) % giocatori.length; 
            }
        }

        vincitore.vinceRound();
        System.out.printf("\n|| Fine ROUND %s ha vinto!", vincitore.getNome());
        return vincitore;
    }

    /**
     * Gestisce le azioni di un giocatore durante il suo turno
     * 
     * @param g Il giocatore attuale
     * @return Restistuisce True se può ancora giocare, False se deve cedere il turno
     */
    private boolean giocaTurnoSuccessivo(Giocatore g){
        System.out.printf("\n|| Board: (%s) %s", fraseNascosta.getTema(), fraseNascosta.getVisualizzaFrase());
        System.out.printf("\n|| Turno di %s | Punteggio round: %s\n", g.getNome(), g.getValoreMontepremiRound());

        boolean puoAvereUnaVocale = g.getValoreMontepremiRound().compareTo(new BigDecimal("500.00")) >= 0;
        boolean soloVocaliRestanti = fraseNascosta.mancanoSoloVocali();

        String richiesta;
        
        if(soloVocaliRestanti){
            // Scelte se mancano solo le vocali
            if(puoAvereUnaVocale) richiesta = "Digita [B] per: comprare una vocale (500€) | Digita [G] per: Indovinare la frase";
            else richiesta =  "Digita [G] per: Indovinare la frase, sei obbligato perché non hai abbastanza soldi per comprare una vocale";
        } else {
            // Scelte di base
            if(puoAvereUnaVocale) richiesta = "Digita [S] per: girare la ruota della fortuna | Digita [B] per: comprare una vocale (500€) | Digita [G] per: Indovinare la frase";
            else richiesta =  "digita [S] per: girare la ruota della fortuna | Digita [G] per: Indovinare la frase";
        }
        

        char scelta = giocatoriIn.leggiLettera(richiesta);
        switch (scelta) {
            case 'S':
                if(soloVocaliRestanti){
                    System.out.println("|| Non puoi più girare la ruota della fortuna, sono rimaste solo vocali");
                    return true;
                }
                return giraLaRuota(g);
        
            case 'B':
                if(puoAvereUnaVocale){
                    return compraVocale(g);
                } else {
                    System.out.println("|| Non hai abbastanza soldi per comprare una vocale!");
                    return true;
                }
            case 'G':
                return tentaSoluzione(g);
            default:
                System.out.println("|| Scelta non valida");
                return true;
        }
    }

    /**
     * Serve a gestire la logica della rotazione della ruota e l'inserimento di una consonante
     * 
     * @param g Il giocatore corrente
     * @return True se mantiene il turno, false se lo cede
     */
    private boolean giraLaRuota(Giocatore g){
        Spicchi spicchioEstratto = ruotaFortuna.giraRandom();
        System.out.printf("|| Spicchio estratto: %s\n", spicchioEstratto.toString());

        if(spicchioEstratto.getTipo() == Spicchi.tipoSpicchio.PASSA){
            if (g.haJollyPlus()) {
                System.out.println("|| Usato il JOLLY+ per salvarti dal PASSA!");
                g.possiedeJollyPlus(false);
                return true;
            } else if (g.haJolly()) {
                System.out.println("|| Usato il JOLLY per salvarti dal PASSA!");
                g.possiedeJolly(false);
                return true;
            }
            return false;
        } else if(spicchioEstratto.getTipo() == Spicchi.tipoSpicchio.BANCAROTTA){
            if (g.haJollyPlus()) {
                System.out.println("|| Usato il JOLLY+ per salvarti dalla BANCAROTTA! I soldi sono salvi.");
                g.possiedeJollyPlus(false);
                return true;
            } else if (g.haJolly()) {
                System.out.println("|| Usato il JOLLY per salvarti dalla BANCAROTTA! Il turno è salvo, ma i soldi sono persi.");
                g.perdeMontepremiRound();
                g.possiedeJolly(false);
                return true;
            }
            g.perdeMontepremiRound();
            return false;
        } else if(spicchioEstratto.getTipo() == Spicchi.tipoSpicchio.JOLLY){
            if(giocatoriConJolly.contains(g)){
                System.out.println("|| È uscito il jolly ma lo hai già avuto una volta, non ti verrà assegnato. Scrivi una consonante per continuare"); 
            } else {
                g.possiedeJolly(true);
                System.out.println("|| Hai ottenuto una carta JOLLY! Scrivi una consonante (valore 0€) per continuare.");
                giocatoriConJolly.add(g);
            }
          } else if(spicchioEstratto.getTipo() == Spicchi.tipoSpicchio.JOLLYPLUS){
            if(!jollyPlusAssegnatoNelRound){
                g.possiedeJollyPlus(true);
                System.out.println("|| Hai ottenuto una carta JOLLY+! Scrivi una consonante (valore 0€) per continuare.");
                jollyPlusAssegnatoNelRound = true;
            } else System.out.println("|| È uscito il jolly plus ma è già stato assegnato a un altro giocatore in precedenza. Scrivi una consonante per continuare"); 
        }

        char consonante = '\0';
        boolean inputValido = false;

        while(!inputValido){
            consonante = giocatoriIn.leggiLettera("Indovina una consonante");
            if(consonante == 'A' || consonante == 'E' || consonante == 'I' || consonante == 'O' || consonante == 'U') System.out.println("|| Errore devi inserire una consonante!");
            else inputValido = true;
        }

        if(lettereScritteDaiGiocatori.contains(consonante)){
            System.out.println("|| Lettera già scritta da un altro giocatore!");
            return false;
        }
        
        lettereScritteDaiGiocatori.add(consonante);

        int occorrenze = fraseNascosta.scopriLettera(consonante);
        if(occorrenze > 0){
            g.guadagnaSoldiRound(spicchioEstratto.getValore(), occorrenze);
            
            BigDecimal soldiVinti = spicchioEstratto.getValore().multiply(new BigDecimal(occorrenze));
            char singolarePlurale = occorrenze > 1 ? 'a' : 'e';  
            System.out.printf("|| Hai trovato %d occorrenz%s! Hai guadagnato %.2f\n", occorrenze, singolarePlurale, soldiVinti);
            return true;
        } else {
            System.out.println("Lettera non trovata!");
            return false;
        }
    }

    /**
     * Permette al giocatore di pagare 500€ per pagare la vocale
     * In questa fase il turno non si cede anche se non viene trovata
     * 
     * @param g Il giocatore corrente
     * @return Sempre true perché il giocatore non può perdere il turno in questa fase
     */
    public boolean compraVocale(Giocatore g){
        g.pagaVocale(new BigDecimal("500.00"));
        char vocale = '\0';
        boolean inputValido = false;

        while(!inputValido){
            vocale = giocatoriIn.leggiLettera("Inserisci la vocale che vuoi");
            if(vocale == 'A' || vocale == 'E' || vocale == 'I' || vocale == 'O' || vocale == 'U') inputValido = true;
            else System.out.println("|| Devi inserire una vocale!");
        }

        if(lettereScritteDaiGiocatori.contains(vocale)){
            System.out.println("|| Vocale già scritta da un altro giocatore!");
            return true;
        }

        lettereScritteDaiGiocatori.add(vocale);
        int occorrenze = fraseNascosta.scopriLettera(vocale);

        if(occorrenze > 0){
            char singolarePlurale = occorrenze > 1 ? 'a' : 'e';  
            System.out.printf("|| Hai trovato %d occorrenz%s!", occorrenze, singolarePlurale);
        } else {
            System.out.println("|| La vocale inserita non è presente!");
        }
        return true;
    }

    /**
     * Permette al giocatore di indovinare la frase del round
     * 
     * @param g Il giocatore corrente
     * @return True se indovina l'intera frase(termina anche il round), false se fallisce e cede il turno 
     */
    public boolean tentaSoluzione(Giocatore g){
        String tentativo = giocatoriIn.leggiStringa("Scrivi la frase");

        // Trasforma in maiuscolo ciò che ha scritto il giocatore per evitare problemi di maiuscole/minuscole
        tentativo = tentativo.toUpperCase();
        if(tentativo.equals(fraseNascosta.getFraseOriginale())){
            // Mostra tutta la frase se è corretta
            for(char c: tentativo.toCharArray()){
                fraseNascosta.scopriLettera(c);
            }
            
            System.out.printf("|| Corretto! Il totale guadagnato in questo round è di %s\n", g.getValoreMontepremiRound());
            return true;

        } else {
        System.out.println("|| Sbagliato!");
        return false;
        }
       
    }
}
