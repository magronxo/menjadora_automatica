/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.machine.io;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        valor = new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return valor;
    }
    public void setValorSimulador(double valor){
        this.valor = valor;
    } 
    //METODES
    public static Sensor addSensor(int tipus, double valor){
        return new Sensor(tipus, valor);
    }
}
