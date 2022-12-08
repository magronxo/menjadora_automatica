/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.machine;


import java.util.Scanner;
import server.machine.Menjadora;

/**
 *
 * @author oriol
 * Classe que crea les Mascotes. És cridat per Maquina.
 * Reb el id de la Maquina.
 * Demana les característiques de la Mascota.
 * Retorna una Mascota amb la seva Menjadora assignada i configurada.
 */
public class Mascota {
    
    //public final static Scanner DADES = new Scanner(System.in);
    
    //VARIABLES
    private String nom = "nom Mascota";
    private boolean dreta;
    private boolean gat = true;
    private int edat = 2;
    private double pesMascota = 4.0;
    
    //CONSTRUCTORS
    public Mascota(boolean dreta,String nom, boolean gat, int edat, double pesMascota){
        this.dreta=dreta;
        this.nom=nom;
        if(gat){
            this.gat = true;
        }else{
            this.gat = false;
        }
        this.edat=edat;
        this.pesMascota=pesMascota; 
    }
    public Mascota(boolean dreta){
        this.dreta=dreta;
    }
    
    //ACCESSORS
    public String getNom() {
        return nom;
    }
    public String stringDreta() {
        if(dreta){
            return "dreta";
        }else{
           return "esquerra"; 
        }
    }
    
    public boolean getGat() {
        return gat;
    }

    public boolean isDreta() {
        return dreta;
    }

    public boolean isGat() {
        return gat;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setGat(String gat) {
        //Control d'errors d'entrada
        if(gat.equalsIgnoreCase("gat")){
            this.gat=true;
        }else{
            this.gat=false;
        }
    }

    public void setEdat(int edat) {
        //Control d'errors d'entrada
        if(edat > 0 && edat < 100){
            this.edat=edat;
        }else{
            edat = 2;
        }
    }

    public void setPesMascota(double pesMascota) {
        //Control d'errors d'entrada
        if(pesMascota > 0 && pesMascota < 100){
            this.pesMascota=pesMascota;
        }else{
            pesMascota = 4.0;
        }
    }
    
    public int getEdat() {
        return edat;
    }

    public double getPesMascota() {
        return pesMascota;
    }   
    
    //METODES
    public static Mascota addMascota(boolean dreta){
        
        return new Mascota(dreta);
    }

    public void actualitzaMascota(String nom, boolean gat, int edat, double pesMascota){
        this.nom=nom;
        this.gat=gat;
        //Controls d'errors d'entrada        
        if(edat > 0 && edat < 100){
            this.edat=edat;
        }else{
            edat = 2;
        }
        
        if(pesMascota > 0 && pesMascota < 100){
            this.pesMascota=pesMascota;
        }else{
            pesMascota = 4.0;
        }
        
    }

}
