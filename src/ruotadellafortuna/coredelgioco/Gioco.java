package ruotadellafortuna.coredelgioco;


import java.util.*;
import ruotadellafortuna.eccezionidelgioco.EccezioniDelGioco;
import ruotadellafortuna.modello.Frase;
import ruotadellafortuna.modello.Giocatore;
import ruotadellafortuna.modello.RuotaAvanzata;
import ruotadellafortuna.modello.RuotaClassica;
import ruotadellafortuna.modello.TutteLeFrasi;
import ruotadellafortuna.util.Input;
import ruotadellafortuna.util.Randomizzatore;

/**
 * Questa è la classe che dirige l'intero gioco: gestisce l'inizializzazione, l'ordine di chi gioca,
 * i 5 Round e la fase finale
 */

public class Gioco {
    /** È lo stato attuale in cui si trova il gioco */
    private Stato statoAttuale;

    /** L'array dei giocatori partecipanti */
    private Giocatore[] giocatori;

    /** È la ruota della fortuna, può essere la classica o la versione estesa */
    private RuotaClassica ruotaFortuna;

    /** È la libreria delle frasi, contiene tutte le frasi di base o esterne indicate dall'utente */
    private TutteLeFrasi libFrasi;
    
    /** Serve a gestire l'input da tastiera */
    private Input input;

    /** È l'indice del giocatore col montepremi più alto */
    private int indiceVincitoreUltimoRound = -1;

    /**
     * Costruttore di classe della classe gioco
     * 
     * @param modalita È la modalità scelta da riga di comando, quindi classica o estesa
     * @param percorsoFile È il percorso file scelto dall'utente al lancio del gioco per scegliere se usare frasi personalizzate o no
     * @param input Serve per leggere da tastiera
     */
    public Gioco(String modalita, String percorsoFile, Input input){
        this.input = input;
        this.statoAttuale = Stato.INIZIALIZZAZIONE;

        if(modalita.equalsIgnoreCase("extended") || modalita.equalsIgnoreCase("estesa")){
            System.out.println("--- La modalità scelta è: Estesa ---");
            this.ruotaFortuna = new RuotaAvanzata();
        } else {
            System.out.println("--- La modalità scelta è: Classica ---");
            this.ruotaFortuna = new RuotaClassica();
        }

        //Inizializza la libreria delle frasi personalizzate scelte dall'utente
        try{
            this.libFrasi = new TutteLeFrasi(percorsoFile);
        } catch(EccezioniDelGioco e){
            System.err.println("\nErrore durante il caricamento delle frasi personalizzate");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        creaGiocatori();
    }

    /**
     * Serve a creare i giocatori e a mescolarli
     */
    public void creaGiocatori(){
        this.giocatori = new Giocatore[3];

        List<Giocatore> listaGiocatoriTmp = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            String nome = input.leggiStringa("Nome per il giocatore " + (i+1));
            listaGiocatoriTmp.add(new Giocatore(nome, (i+1)));
        }
    
        /* 
        Serve a fare randomizzare l'ordine dei giocatori, in questo modo si può mettere un seed per far si che sia
        sempre uguale
        */
        Collections.shuffle(listaGiocatoriTmp, Randomizzatore.getRand());

        for(int i = 0; i < 3; i++){
            this.giocatori[i] = listaGiocatoriTmp.get(i);
        }

        System.out.print("|| I giocatori giocheranno in questo ordine: [");
        for(int i = 0; i < 3; i++){
            if(i == 2) System.out.printf(" %s", giocatori[i].getNome());
            else System.out.printf(" %s, ", giocatori[i].getNome());
           
        } System.out.println("]");
    }

    /**
     * Metodo che avvia il loop dei 5 round della partita più il finale del campione
     */
    public void avviaPartita(){
        for(int i = 1; i <= 5; i++){
            this.statoAttuale = Stato.INIZIO_ROUND;

            riordinaPerVincitore();

            try{
                Frase fraseEstratta = libFrasi.estrazioneFrase(i);

                Round roundCorrente = new Round(i, fraseEstratta, giocatori, ruotaFortuna, input);
                Giocatore vincitore = roundCorrente.gioca();

                impostaVincitoreDelRound(vincitore);
            } catch(EccezioniDelGioco e){
                // Questo causa l'interruzione di tutto il gioco per errori gravi ad esmepio aver terminato le frasi
                System.err.println("Errore durante l'esecuzione del gioco: "+ e.getMessage());
                return;
            }
        }

        this.statoAttuale = Stato.FASE_FINALE;
        faseFinale();
    }

    /**
     * Trova il giocatore col montepremi più alto
     */
    public void faseFinale(){
        Giocatore campione = giocatori[0];

        for(int i = 1; i < 3; i++){
            if(giocatori[i].getValoreMontepremi().compareTo(campione.getValoreMontepremi()) > 0){
                campione = giocatori[i];
            }
        }

        try{
            Frase fraseFinale = libFrasi.estrazioneFrase(6);
            FaseFinale finale = new FaseFinale(fraseFinale, campione, input);
            finale.gioca();
        } catch(EccezioniDelGioco e){
            System.err.println("Errore impossibile estrarre la frase finale: " + e.getMessage());
        }

        this.statoAttuale = Stato.FINE_GIOCO;
    }

    /**
     * Salva l'indice del vincitore dell'ultimo round
     */
    private void impostaVincitoreDelRound(Giocatore vincitore) {
        for (int i = 0; i < giocatori.length; i++) {
            if (giocatori[i].getNumGioc() == vincitore.getNumGioc()) {
                this.indiceVincitoreUltimoRound = i;
                break;
            }
        }
    }
    
    /**
    * Riordina l'ordine dei giocatori per permettere al vincitore di giocare per primo al prossimo round
    */
    private void riordinaPerVincitore() {
        // Se il vincitore è già il primo è già ordinato
        if (indiceVincitoreUltimoRound <= 0) return; 

        Giocatore[] nuovoOrdine = new Giocatore[3];
        int indiceInserimento = 0;

        for (int i = indiceVincitoreUltimoRound; i < 3; i++) {
            nuovoOrdine[indiceInserimento++] = giocatori[i];
        }
        for (int i = 0; i < indiceVincitoreUltimoRound; i++) {
            nuovoOrdine[indiceInserimento++] = giocatori[i];
        }

        this.giocatori = nuovoOrdine;
        this.indiceVincitoreUltimoRound = 0;
    }
}
