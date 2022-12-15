package server.machine.io;

/**
 * Classe que crea els Motors. És cridat per Menjadora.
 * Retorna a la Menjadora un Motor.
 * @author Oriol Coll Salvia
 */
public class Actuador {
    
    //VARIABLES
    private boolean interruptor = false;
    private boolean block = false;
       
    //CONSTRUCTOR
    public Actuador(){
    }
    
    //ACCESSORS
    public boolean isInterruptor(){      
        return interruptor;
    }
    
    public boolean isBlock() {    
        return block;
    }

    //METODES
    /**
     * Afegeix un Motor
     * @return un nou Motor
     */
    public static Actuador addMotor() {
        return new Actuador();
    }
    
    
    //FUNCIONS
    /**
     * Activa el Motor si no esta bloquejat
     */
    public void activaRele(){
        if(!block){
            this.interruptor = true;
        }
    }
    /**
     * Desactiva el Motor posant el seu interruptor a false
     */
    public void desactivaRele(){
        this.interruptor = false;
    }
    /**
     * Bloca el Motor si no hi ha pinso o està la Menjadora desactivada
     */
    public void blocaRele(){
        block = true;
    }
    /**
     * Desbloca el Motor per permetre la activació
     */    
    public void desblocaRele(){
        block = false;
    }    
}
