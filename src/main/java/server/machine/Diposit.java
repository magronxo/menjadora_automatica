/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.machine;
import server.machine.io.Sensor;

/**
 *
 * @author oriol
 * Classe que crea un Diposit. És cridat per Menjadora.
 * Reb el id de la Menjadora.
 * Retorna a la Menjadora un Diposit amb el seu Sensor de nivell.
 */
public class Diposit {
    
    //CONSTANTS
    private final static int TIPUS_SENSOR = 2; //Sensor de nivell del dipòsit
    private final static double DIPOSIT_BUIT = 10.0; //Distànica sensor menjar que defineix dipòsit buit
    private final static double DIPOSIT_PLE = 50.0;//Distància
    
    //VARIABLES
    private double valorAlertaDiposit = 20.0;//Distància sensor menjar llença avís carregar dipòsit
    private static boolean dreta;
    private Sensor sensorNivell;
    private boolean alertaDiposit, dipositBuit;
    
    //CONSTRUCTORS
    public Diposit(Sensor sensorNivell, boolean dreta){
        this.sensorNivell=sensorNivell;
        this.dreta=dreta;
    }
    public Diposit(){
    }
    
    //ACCESSORS
    public boolean isDreta(){
        return dreta;
    }
    public String stringDreta(){
       if(dreta){
           return "dreta";
       }else
           return "esquerra";
       
    }
    public Sensor getSensorNivell() {
        return sensorNivell;
    }

    public double getValorAlertaDiposit() {
        return valorAlertaDiposit;
    }

    public boolean isAlertaDiposit() {
        return alertaDiposit;
    }

    /*public boolean isDipositBuit() {
        return dipositBuit;
    }*/
    
    public void setValorAlertaDiposit(double valorAlertaDiposit) {
        if(valorAlertaDiposit > 15 && valorAlertaDiposit < 40){
            this.valorAlertaDiposit = valorAlertaDiposit;
        }else{
            valorAlertaDiposit = 20;
        }
    }
    
    
        //METODES
    public static Diposit addDiposit(double valorSensor){
        Sensor sensorNivell = new Sensor().addSensor(TIPUS_SENSOR, valorSensor);
        return new Diposit(sensorNivell,dreta);
    }
    
        //FUNCIONS
    public double getPercentatgeDiposit(){
        double percentatge = ((DIPOSIT_BUIT - sensorNivell.getValor())/DIPOSIT_BUIT) *100;
        return percentatge; 
    }
    
    public void setAlertaDiposit(){
        if(sensorNivell.getValor() > valorAlertaDiposit){
            this.alertaDiposit = true;
            //TODO   Activa la icona d'Alerta al Diposit
        }
    }
        
    public Boolean estaBuit(){
        if(sensorNivell.getValor() >= DIPOSIT_BUIT){
            return true;
        }else{
           return false; 
        }
    }

    /*public void comprovaDiposit(){
        if(sensorNivell.getValor() < valorAlertaDiposit){
            this.alertaDiposit = true;
        }
        if(sensorNivell.getValor() <= DIPOSIT_BUIT){
            dipositBuit = true;
        }
    }*/
    
}