/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.machine.io;

/**
 *
 * @author oriol
 * Classe que crea els Motors. És cridat per Menjadora.
 * Reb el id de la Menjadora.
 * Retorna a la Menjadora un Motor amb el seu interruptor.
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
    public static Actuador addMotor() {
        return new Actuador();
    }
    
    
    //FUNCIONS
    public void activaRele(){
        if(!block){
            this.interruptor = true;
        }

    }
    
    public void desactivaRele(){
        this.interruptor = false;
        //Atura l'increment de pes a la Simulació
    }
    
    public void blocaRele(){
        block = true;
    }
        
    public void desblocaRele(){
        block = false;
    }    
}
