package server.machine;
import server.machine.io.Sensor;

/**
 * Classe que crea un Diposit. És cridat per Menjadora.
 * Retorna a la Menjadora un Diposit amb el seu Sensor de nivell.
 * @author Oriol Coll Salvia
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
    /**
     * Construeix el Diposit
     * @param sensorNivell sensor de distància del pinso [double]
     * @param dreta booleà posició Menjadora
     */
    public Diposit(Sensor sensorNivell, boolean dreta){
        this.sensorNivell=sensorNivell;
        this.dreta=dreta;
    }
    public Diposit(){
    }
 
    //METODES
    /**
     * Afegeix un Diposit
     * @return un nou diposit amb la seva posicio i el seu Sensor de nivell
     */
    public static Diposit addDiposit(){
        Sensor sensorNivell = new Sensor().addSensor(TIPUS_SENSOR, 50);
        return new Diposit(sensorNivell,dreta);
    }
    
    //FUNCIONS
    /**
     * Calcula el percentatge del Diposit
     * @return percentatge restant del contingut del Diposit
     */
    public double getPercentatgeDiposit(){
        double percentatge = (sensorNivell.getValor()/DIPOSIT_PLE) *100;
        return percentatge; 
    }
    
    /**
     * Activa o desactiva el booleà d'Alerta quan el nivell de pinso està per sota d'aquest
     */
    public void setAlertaDiposit(){
        if(sensorNivell.getValor() < valorAlertaDiposit){
            this.alertaDiposit = true;
        }else{
            this.alertaDiposit = false;
        }
    }
    /**
     * Comprova si el sensor del Diposit està per sota del mínim
     * @return si el Diposit està buit o no
     */    
    public Boolean estaBuit(){
        if(sensorNivell.getValor() <= DIPOSIT_BUIT){
            return true;
        }else{
           return false; 
        }
    }

    //ACCESSORS
    
     /**
     * Re-assigna el valor en que s'alertarà per nivell de pinso baix.
     * Controla que el valor estigui dins del rang.
     * @param valorAlertaDiposit [integer]
     */
    public void setValorAlertaDiposit(double valorAlertaDiposit) {
        if(valorAlertaDiposit > 15 && valorAlertaDiposit < 40){
            this.valorAlertaDiposit = valorAlertaDiposit;
        }else{
            valorAlertaDiposit = 20;
        }
    }
    
    /**
     * @return si és dreta o esquerra en text
     */
    public String stringDreta(){
       if(dreta){
           return "dreta";
       }else
           return "esquerra";
       
    }
    public boolean isDreta(){
        return dreta;
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

    public static double getDIPOSIT_BUIT() {
        return DIPOSIT_BUIT;
    } 
}