package ruotadellafortuna.main;

import ruotadellafortuna.coredelgioco.Gioco;
import ruotadellafortuna.util.Input;
import ruotadellafortuna.util.Randomizzatore;

public class Main{
    public static void main(String[] args){
        // Valori di default
        String mode = "classic";
        Integer seed = null;
        String libPath = null;

        for(int i = 0; i < args.length; i++){

            if(args[i].equalsIgnoreCase("--mode") && (i + 1) < args.length){
                mode = args[i + 1].toLowerCase();
            } else if (args[i].equalsIgnoreCase("--seed") && (i + 1) < args.length){

                try{
                    seed = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e){
                    System.out.println("Attenzione, il seed utilizzato non è un numero valido, il gioco proseguirà con il seed casuale.");
                }

            } else if(args[i].equalsIgnoreCase("--lib") && (i + 1) < args.length){
                libPath = args[i + 1];
            }
        }

        Randomizzatore.inizializzazione(seed);

        System.out.println("----- Wheel of Fortune -----");
        System.out.println("Modalità scelta: " + mode);
        System.out.println("Seed: " + (seed != null ? seed : "Casuale"));
        System.out.println("Libreria personalizzata: " + (libPath != null ? libPath : "Nessuna libreria indicata, il gioco proseguirà con quella di base"));
        System.out.println("----------------------------------\n");

        Input input = new Input();
        Gioco partita = new Gioco(mode, libPath, input);
        partita.avviaPartita();

    }
            
}
