/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package server;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oriol
 * 
 * 
 * Crea les Maquines passant-li el id
 */
public class Servidor_Menjadora {
    
    private static final int HORES_PER_EXECUCIO = 1; //Important! Configurar quantes hores es simulen per cada execució del programa
    private static int id = 1;
    private static boolean sortirPrograma = false;
    private static Maquina maquina;
    private static double horesExecucio;


    //CONSTRUCTORS
    public Servidor_Menjadora(int id){
        this.id=id;
    }
    public Servidor_Menjadora(){
    }
    
    //ACCESSORS
    public static Maquina getMaquina() {
        return maquina;
    }

    public static double getHoresExecucio() {
        return horesExecucio;
    }
    
    //-------   MAIN ---------
    public static void main(String[] args) {
           
        ArrayList<Maquina> maquines = new ArrayList<Maquina>();
        
        maquina = new Maquina();
        maquines.add(maquina.addMaquina(1));
        horesExecucio = 0;
        
        
        
        while(!sortirPrograma){
            for (Maquina maquina : maquines){
                maquina.funcionamentMaquina();
                horesExecucio = horesExecucio + HORES_PER_EXECUCIO;
                maquina.getControlador().escriuValorsGui();
                System.out.println("\n\tSon les "+ horesExecucio);
                if(horesExecucio >= 24){
                    maquina.resetejaDia();
                    horesExecucio = 0;
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);//Important! Definim el temps entre execucions del programa     
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor_Menjadora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }


}
