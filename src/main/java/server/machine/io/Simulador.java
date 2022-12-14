/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.machine.io;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Maquina;
import server.Servidor_Menjadora;
import server.machine.Menjadora;

/**
 *
 * @author oriol
 * Aquesta classe no crea cap objecte.
 */
public class Simulador{
    
    //VARIABLES
    public boolean dreta;
    public double sensorPlat_esquerra = 12.4;
    public double sensorPlat_dreta = 20.1;
    public double sensorNivell_esquerra = 2;
    public double sensorNivell_dreta = 2;
    //private SimuladorMascota mascota1 = new SimuladorMascota(13.0, 4, true, "clyde");
    //private SimuladorMascota mascota2 = new SimuladorMascota(15.0, 5, false, "bonny");
    
    private SimuladorMascota mascota1;
    private SimuladorMascota mascota2;
    
    private Menjadora menjadoraDreta, menjadoraEsquerra;
    
    private Maquina maquina;
    
    //CONSTRUCTORS
    public Simulador(Menjadora menjadoraDreta,Menjadora menjadoraEsquerra,double sensorPlat_esq, double sensorPlat_dret, double sensorNivell_esq, double sensorNivell_dreta){
        this.sensorPlat_esquerra=sensorPlat_esq;
        this.sensorPlat_dreta=sensorPlat_dret;
        this.sensorNivell_esquerra=sensorNivell_esq;
        this.sensorNivell_dreta=sensorNivell_dreta;
        this.menjadoraDreta=menjadoraDreta;
        this.menjadoraEsquerra=menjadoraEsquerra;
        mascota1 = new SimuladorMascota(menjadoraDreta.getGramsRaccio()/1.5, 5, true,menjadoraDreta.getMascota().getNom());
        mascota2 = new SimuladorMascota(menjadoraEsquerra.getGramsRaccio()/1.6+1, 5, false,menjadoraEsquerra.getMascota().getNom());
    }
    public Simulador(){
    }
    
       //ACCESSORS
    public void setSensorPlat_esquerra(double sensorPlat_esquerra) {
        this.sensorPlat_esquerra = sensorPlat_esquerra;
    }

    public void setSensorPlat_dreta(double sensorPlat_dreta) {
        this.sensorPlat_dreta = sensorPlat_dreta;
    }

    public void setSensorNivell_esquerra(double sensorNivell_esquerra) {
        this.sensorNivell_esquerra = sensorNivell_esquerra;
    }

    public void setSensorNivell_dreta(double sensorNivell_dreta) {
        this.sensorNivell_dreta = sensorNivell_dreta;
    }

    public double getSensorPlat_esquerra() {
        return sensorPlat_esquerra;
    }

    public double getSensorPlat_dreta() {
        return sensorPlat_dreta;
    }

    public double getSensorNivell_esquerra() {
        return sensorNivell_esquerra;
    }

    public double getSensorNivell_dreta() {
        return sensorNivell_dreta;
    }
    
    public double retornaNivell(){
        if(!dreta){
            return sensorNivell_esquerra;
        }else{
            return sensorNivell_dreta;
        }
    }
    
    public double retornaPlat(){
        if(!dreta){
            return sensorPlat_esquerra;
        }else{
            return sensorPlat_dreta;
        }
    }

    public SimuladorMascota getMascota1() {
        return mascota1;
    }

    public SimuladorMascota getMascota2() {
        return mascota2;
    }

    //METODES
    public static Simulador addSimulador(){
        return new Simulador();
    }
    
    //FUNCIONS
    public void startSimulacio(boolean sortirPrograma){
        while(!sortirPrograma){
            simulaMascotes();
            simulaHuma();
        }
    }

    public void simulaMascotes(){
        if(mascota1.isDreta()){
            this.sensorPlat_dreta = mascota1.menja(this.sensorPlat_dreta);
            this.sensorPlat_esquerra = mascota2.menja(this.sensorPlat_esquerra);
        }else{
            this.sensorPlat_esquerra = mascota1.menja(this.sensorPlat_esquerra);
            this.sensorPlat_dreta = mascota2.menja(this.sensorPlat_dreta);
        }
       
    }
    
    public Maquina getMaquina (){
        return this.maquina;
    }
    public void setMaquina (Maquina maquina){
        this.maquina = maquina;
    }
    
    public void simulaHuma(){
        try {
                TimeUnit.SECONDS.sleep(6);//Important! Definim el temps entre execucions del programa     
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor_Menjadora.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    //Divideix el nombre de raccions entre 24 hores
    public void reparteixRaccions(double raccions){
        double tempsEntreRaccions = raccions / 24;
        
    }

}

class SimuladorMascota{
        private double quantitatMenjada;
        private int tempsMenjada;
        private boolean dreta;
        private String nomMascota;
        
        public SimuladorMascota(double quantitatMenjada, int tempsMenjada, boolean dreta, String nom){
            this.quantitatMenjada = quantitatMenjada;
            this.tempsMenjada = tempsMenjada;
            this.dreta = dreta;
            this.nomMascota = nom;
        }

        public double getQuantitatMenjada() {
            return quantitatMenjada;
        }

        public int getTempsMenjada() {
            return tempsMenjada;
        }

        public boolean isDreta() {
            return dreta;
        }

        public String getNomMascota() {
            return nomMascota;
        }
        

        public void setQuantitatMenjada(double quantitatMenjada) {
            this.quantitatMenjada = quantitatMenjada;
        }

        public void setTempsMenjada(int tempsMenjada) {
            this.tempsMenjada = tempsMenjada;
        }

        public void setDreta(boolean dreta) {
            this.dreta = dreta;
        }

        public void setNomMascota(String nomMascota) {
            this.nomMascota = nomMascota;
        }
        
    
        
        public double menja(double menjarPlat){
            if(menjarPlat > 0){
                if(menjarPlat < quantitatMenjada){
                    System.out.println("La " + nomMascota +  " ha menjat " + menjarPlat + " grams");
                    menjarPlat=0;
                }else{
                    menjarPlat = menjarPlat - quantitatMenjada;
                    System.out.println("La " + nomMascota +  " ha menjat " + quantitatMenjada + " grams");
                }
            }else{
                System.out.println("Plat buit, la mascota " + nomMascota +  " no ha menjat!");
            }
            return menjarPlat;
        }
        
    }

class SimuladorHuma{
    
    private int tempsReaccio;

    public void carregaDiposit(Maquina maquina){
            maquina.getMenjadoraDreta().getDiposit().getSensorNivell().setValorSimulador(50);
            maquina.getMenjadoraEsquerra().getDiposit().getSensorNivell().setValorSimulador(50);
            
    }

}