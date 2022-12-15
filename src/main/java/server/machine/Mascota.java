package server.machine;


import java.util.Scanner;
import server.machine.Menjadora;

/**
 * Classe que crea les Mascotes. És cridat per Maquina.
 * Retorna una Mascota amb les seves característiques.
 * Cada Mascota s'assignarà a una Menjadora i li definirà els valors de funcionament. 
 * @author Oriol Coll Salvia
 */
public class Mascota {
    
    //VARIABLES
    private String nom = "nom Mascota";
    private boolean dreta;
    private boolean gat = true;
    private int edat = 2;
    private double pesMascota = 4.0;
    
    //CONSTRUCTORS
    /**
     * Construeix la Mascota
     * @param dreta booleà posició dreta/esquerra
     * @param nom de la Mascota
     * @param gat booleà tipus gat/gos
     * @param edat de la Mascota [integer]
     * @param pesMascota [double]
     */
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
      
    //METODES
    /**
     * Afegeix la Mascota
     * @param dreta booleà posició Menjadora dreta/esquerra
     * @return una nova Mascota
     */
    public static Mascota addMascota(boolean dreta){       
        return new Mascota(dreta);
    }

    /**
     * Actualitza la Mascota.
     * És cridada per la Pantalla Configuració
     * Controla que els valors estiguin dinds del rang.
     * @param nom
     * @param gat
     * @param edat
     * @param pesMascota 
     */
    public void actualitzaMascota(String nom, boolean gat, int edat, double pesMascota){
        this.nom=nom;
        this.gat=gat;
        //Controls d'errors d'entrada        
        if(edat > 0 && edat < 100){
            this.edat=edat;
        }else{
            edat = 2;
        }
        //Controls d'errors d'entrada  
        if(pesMascota > 0 && pesMascota < 100){
            this.pesMascota=pesMascota;
        }else{
            pesMascota = 4.0;
        }     
    }
    
    //ACCESSORS

    /**
     * Re-assigna el tipus de Mascota
     * @param gat booleà gat/gos
     */
    public void setGat(String gat) {
        //Control d'errors d'entrada
        if(gat.equalsIgnoreCase("gat")){
            this.gat=true;
        }else{
            this.gat=false;
        }
    }

    /**
     * Re-assigna la Edat de la Mascota
     * @param edat [integer]
     */
    public void setEdat(int edat) {
        //Control d'errors d'entrada
        if(edat > 0 && edat < 100){
            this.edat=edat;
        }else{
            edat = 2;
        }
    }

    /**
     * Re-assigna el Pes de la Mascota
     * @param pesMascota [double]
     */
    public void setPesMascota(double pesMascota) {
        //Control d'errors d'entrada
        if(pesMascota > 0 && pesMascota < 100){
            this.pesMascota=pesMascota;
        }else{
            pesMascota = 4.0;
        }
    }
    /**
     * @return si és dreta o esquerra en text
     */
    public String stringDreta() {
        if(dreta){
            return "dreta";
        }else{
           return "esquerra"; 
        }
    }
    
    public int getEdat() {
        return edat;
    }

    public double getPesMascota() {
        return pesMascota;
    }   
    public String getNom() {
        return nom;
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

}
