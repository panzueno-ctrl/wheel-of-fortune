package ruotadellafortuna.modello;
import java.util.*;
import java.io.*;

import ruotadellafortuna.eccezionidelgioco.EccezioniDelGioco;
import ruotadellafortuna.util.Randomizzatore;

/**
 * TutteLeFrasi gestisce l'archivio di tutte le nostre frasi e temi. Da qui estriamo a caso tema e frase.
 * La frase aumenta di difficolà in base ai round
 */

public class TutteLeFrasi {
    
    /** Conitne la coppia tema-frase del gioco */
    private List<UnioneStringa>[] archivioFrasi;

    /**
     * Costruttore che inizializza e carica le frasi
     * 
     * @param percorsoFile Il percorso del file delle frasi personalizzate dell'utente, se vale null carica quelle di base 
     * @throws EccezioniDelGioco Se il file non esiste viene lanciata l'eccezione
     */
    @SuppressWarnings("unchecked")
    public TutteLeFrasi(String percorsoFile) throws EccezioniDelGioco{
        
        // Serve per creare l'array list utilizzato per i 6 round del gioco
        this.archivioFrasi = new ArrayList[6];
        for(int i = 0; i < 6; i++){
            this.archivioFrasi[i] = new ArrayList<>();
        }

        if(percorsoFile != null){
            caricaFile(percorsoFile);
        } else{
            caricamentoFrasiDiBase();
        }
    }

    /**
     * Carica le frasi dal file indicato dal giocatore quando avvia il gioco
     * 
     * @param percorsoFile Il percorso del file delle frasi personalizzate dell'utente, se vale null carica quelle di base 
     * @throws EccezioniDelGioco Se il file non esiste viene lanciata l'eccezione
     */
    private void caricaFile(String percorsoFile) throws EccezioniDelGioco{

        try(BufferedReader br = new BufferedReader(new FileReader(percorsoFile))){
            String linea;
            while((linea = br.readLine()) != null){
                String[] parti = linea.split("#");

                if(parti.length == 3){
                    int fase = Integer.parseInt(parti[0].trim());
                    String tema = parti[1].trim().toUpperCase();
                    String frase = parti[2].trim().toUpperCase();

                    int indiceFase = fase -1;
                    if(indiceFase >= 0 && indiceFase <= 5){
                        this.archivioFrasi[indiceFase].add(new UnioneStringa(tema, frase));
                    }
                }
            }
        } catch(IOException e){
            throw new EccezioniDelGioco("Impossibile caricare il file: "+ percorsoFile, e);
        } catch(NumberFormatException e){
            throw new EccezioniDelGioco("Errore nel formato del numero del round", e);
        }
    }


    /**
     * Le frasi di base pronte all'avvio del gioco se il giocatore non carica un file personalizzato con le sue frasi contenute
     */
    private void caricamentoFrasiDiBase(){

        // Round 1 Corte
        archivioFrasi[0].add(new UnioneStringa("ANIMALI", "IL CANE ABBAIA"));
        archivioFrasi[0].add(new UnioneStringa("CIBI", "LA MELA ROSSA"));
        archivioFrasi[0].add(new UnioneStringa("MOTORI", "LA FIAT PANDA"));

        // Round 2 Medie
        archivioFrasi[1].add(new UnioneStringa("ANIMALI", "IL GATTO MIAGOLA"));
        archivioFrasi[1].add(new UnioneStringa("CIBI", "LA PIZZA CALDA"));
        archivioFrasi[1].add(new UnioneStringa("MOTORI", "LA FERRARI ROSSA"));

        // Round 3
        archivioFrasi[2].add(new UnioneStringa("ANIMALI", "IL CONIGLIO MANGIA LE CAROTE"));
        archivioFrasi[2].add(new UnioneStringa("CIBI", "SPAGHETTI AL POMODORO"));
        archivioFrasi[2].add(new UnioneStringa("MOTORI", "RUOTA DI SCORTA"));

        // Round 4
        archivioFrasi[3].add(new UnioneStringa("ANIMALI", "LA SCIMMIA SALTA SUI RAMI"));
        archivioFrasi[3].add(new UnioneStringa("CIBI", "BISTECCA ALLA FIORENTINA"));
        archivioFrasi[3].add(new UnioneStringa("MOTORI", "FRENO A MANO ELETTRONICO"));

        // Round 5 Lunghe
        archivioFrasi[4].add(new UnioneStringa("ANIMALI", "LA PIOVRA GIGANTE DEL PACIFICO"));
        archivioFrasi[4].add(new UnioneStringa("CIBI", "TORTELLINI IN BRODO DI POLLO"));
        archivioFrasi[4].add(new UnioneStringa("MOTORI", "SPECCHIETTO RETROVISORE DESTRO"));

        // Fase Finale Round 6, molto lunghe e più difficili
        archivioFrasi[5].add(new UnioneStringa("PROVERBI", "A MALI ESTREMI ESTREMI RIMEDI"));
        archivioFrasi[5].add(new UnioneStringa("ANIMALI", "L ORNITORINCO DEPONE LE UOVA"));
        archivioFrasi[5].add(new UnioneStringa("MOTORI", "ROLLS ROYCE PHANTOM LIMOUSINE"));
    }

    /**
     * Estrae una frase casuale, la difficoltà dipende dal round, la frase scelta viene cancellata per non sceglierla di nuovo
     * @param nRound Il numero del round corrente
     * @return Restituisce un nuovo oggetto Frase composto da tema e frase scelta
     * @throws EccezioniDelGioco Lancia l'eccezione se le frasi terminano lungo i round
     */

    public Frase estrazioneFrase(int nRound)throws EccezioniDelGioco{
        
        int indiceFase = nRound - 1;
        List<UnioneStringa> listaRound = this.archivioFrasi[indiceFase];

        if(listaRound.isEmpty()){
            throw new EccezioniDelGioco("Frasi esaurite per il round " + nRound);
        }

        int estrazione = Randomizzatore.generatore(listaRound.size());
        UnioneStringa estratta = listaRound.remove(estrazione);

        return new Frase(estratta);
    }
    
}
