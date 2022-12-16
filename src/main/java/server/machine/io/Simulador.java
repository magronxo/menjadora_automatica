package server.machine.io;

import server.Maquina;
import server.machine.Menjadora;

/**
 * Classe que Simula el comportament del Sistema.
 * La Menjadora serveix el pinso, la Mascota el consumeix i l'Humà re-omple el dipòsit
 * @author Oriol Coll Salvia
 */
public class Simulador{
    
    //VARIABLES
    public boolean dreta;
    public double sensorPlat_esquerra = 12.4;
    public double sensorPlat_dreta = 20.1;
    public double sensorNivell_esquerra = 2;
    public double sensorNivell_dreta = 2;
    
    private Maquina maquina;
    private SimuladorMascota mascotaDreta;
    private SimuladorMascota mascotaEsquerra;
    private Menjadora menjadoraDreta, menjadoraEsquerra;
    
    //VARIABLES DE SIMULACIO
    private double ganaMascotaDreta= 1.5; //Gana de les Mascotes. 1 = es menja tota la raccio
    private double ganaMascotaEsquerra = 1.6; // Com més alt, menys mengen

    //CONSTRUCTORS
    /**
     * Construeix el Simulador amb les seves Menjadores, Mascotes i els valors dels Sensors
     */
    public Simulador(Menjadora menjadoraDreta,Menjadora menjadoraEsquerra,double sensorPlat_esq, double sensorPlat_dret, double sensorNivell_esq, double sensorNivell_dreta){
        this.sensorPlat_esquerra=sensorPlat_esq;
        this.sensorPlat_dreta=sensorPlat_dret;
        this.sensorNivell_esquerra=sensorNivell_esq;
        this.sensorNivell_dreta=sensorNivell_dreta;
        this.menjadoraDreta=menjadoraDreta;
        this.menjadoraEsquerra=menjadoraEsquerra;
        mascotaDreta = new SimuladorMascota(menjadoraDreta.getGramsRaccio()/ganaMascotaDreta, 5, true,menjadoraDreta.getMascota().getNom());
        mascotaEsquerra = new SimuladorMascota(menjadoraEsquerra.getGramsRaccio()/ganaMascotaEsquerra, 5, false,menjadoraEsquerra.getMascota().getNom());
    }
    public Simulador(){
    }
    
    
    //METODES
    /**
     * Afegeix el Simulador
     * @return un nou Simulador
     */
    public static Simulador addSimulador(){
        return new Simulador();
    }
    
    //FUNCIONS
    /**
     * Inicia la Simulació
     * @param sortirPrograma booleà que activa la Simulació
     */
    public void startSimulacio(boolean sortirPrograma){
        while(!sortirPrograma){
            simulaMascotes();
            //simulaHuma();
        }
    }

    /**
     * Inicia la Simulació de les Mascotes.
     * Assigna la Menjadora a la Mascota.
     */
    public void simulaMascotes(){
        if(mascotaDreta.isDreta()){
            this.sensorPlat_dreta = mascotaDreta.menja(this.sensorPlat_dreta);
            this.sensorPlat_esquerra = mascotaEsquerra.menja(this.sensorPlat_esquerra);
        }else{
            this.sensorPlat_esquerra = mascotaDreta.menja(this.sensorPlat_esquerra);
            this.sensorPlat_dreta = mascotaEsquerra.menja(this.sensorPlat_dreta);
        }    
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
    
    public void setMaquina (Maquina maquina){
        this.maquina = maquina;
    } 
    
    public Maquina getMaquina (){
        return this.maquina;
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

    public SimuladorMascota getMascotaDreta() {
        return mascotaDreta;
    }

    public SimuladorMascota getMascotaEsquerra() {
        return mascotaEsquerra;
    }

}

/**
 * Classe interna del Simulador.
 * Simula la Mascota com menja
 * @author Oriol Coll Salvia
 */
class SimuladorMascota{
        private double quantitatMenjada;
        private int tempsMenjada;
        private boolean dreta;
        private String nomMascota;
        
        /**
         * Constueix una Mascota de Simulació per a que menji
         * @param quantitatMenjada
         * @param tempsMenjada
         * @param dreta
         * @param nom 
         */
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
        
        /**
         * Simula que la Mascota menja
         * @param menjarPlat valor de grams de pinso que hi ha al plat
         * @return 
         */
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
