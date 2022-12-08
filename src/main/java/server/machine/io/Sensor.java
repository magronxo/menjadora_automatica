/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.machine.io;

/**
 *
 * @author oriol
 * Classe que crea un Sensor. És cridat per Menjadora i Diposit.
 * Reb el id de la Menjadora/Diposit i el tipus de Sensor.
 * Retorna un Sensor amb el seu Valor.
 */
public class Sensor {
    
    //VARIABLES
    private int id;//Per quan hi hagi sensors fisics
    private boolean dreta;
    private int tipus;//Sensor de nivell o Sensor de pes del plat
    private double valor;


    
    
    //CONSTRUCTORS
    public Sensor(int tipus, double valor){
        this.tipus=tipus;
        this.valor=valor;
    }
    public Sensor(){
    }
    
    //ACCESSORS
    
    public double getValor() {
        return valor;
    }
    //METODES
    public static Sensor addSensor(int tipus, double valor){
        //int id,tipus;
        //double valor = llegeixValor(tipus);
        
        return new Sensor(tipus, valor);
    }

    
    //FUNCIONS
    public void setValorSimulador(double valor){
        
        this.valor = valor;

    } 
}
