package server;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.machine.io.Simulador;

/**
 * Classe Primera amb el mètode Main.
 * Classe que crea les Maquines que contindran les Menjadores i les Mascotes.
 * @author Oriol Coll Salvia
 */
public class Servidor_Menjadora {
    
    private static final int HORES_PER_EXECUCIO = 1; //Important! Configurar quantes hores es simulen per cada execució del programa
    private static int id = 1;
    private static boolean sortirPrograma = false;
    private static Maquina maquina;
    private static double horesExecucio;
    private static Simulador simulador;
    
    private static long initialTime = System.currentTimeMillis();
    private static int diesSimulats = 0;
    
    //VARIABLES DE SIMULACIO
    private static int tempsEntreExecucions= 5; //Important! Definim el temps entre execucions del programa


    //CONSTRUCTORS
    /**
     * Construeix el Servidor de les Maquines
     * @param id identificador del Servidor
     */
    public Servidor_Menjadora(int id){
        this.id=id;
    }
    public Servidor_Menjadora(){
    }
    
    //-------   MAIN  ---------
    
    /**
     * Inicialitza la Maquina amb tots els seus elements.
     * Definim els temps d'execució i executem el funcionament/simulació.
     * Simulem les hores i fem passar els dies.
     * @param args 
     */
    public static void main(String[] args) {
           
        ArrayList<Maquina> maquines = new ArrayList<Maquina>();
        maquina = new Maquina();
        maquines.add(maquina.addMaquina(1, initialTime));
        horesExecucio = 0;         

        while(!sortirPrograma){  
            for (Maquina maquina : maquines){
                maquina.funcionamentMaquina();
                maquina.getControlador().sumaDia(diesSimulats);
                horesExecucio = horesExecucio + HORES_PER_EXECUCIO;
                maquina.getControlador().escriuValorsGui();
                System.out.println("\n\tSon les "+ horesExecucio);
                if(horesExecucio >= 24){
                    maquina.resetejaDia();
                    horesExecucio = 0;
                    diesSimulats++;
                    maquina.getControlador().sumaDia(diesSimulats);
                }
            }
            try {
                TimeUnit.SECONDS.sleep(tempsEntreExecucions);//Important! Definim el temps entre execucions del programa

            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor_Menjadora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //ACCESSORS
    public static Maquina getMaquina() {
        return maquina;
    }

    public static double getHoresExecucio() {
        return horesExecucio;
    }
    
}
