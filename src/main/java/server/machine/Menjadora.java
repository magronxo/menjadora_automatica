package server.machine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import server.machine.io.Actuador;
import server.machine.io.Sensor;

/**
 * Classe que crea les Menjadores.
 * Retorna la Menjadora amb el seu Diposit, el seu Motor i el seu Sensor del plat.
 * Retorna la Menjadora amb les Raccions definides segons les característiques de la Mascota assignada.
 * @author Oriol Coll Salvia
 */
public class Menjadora {
    
    private static final int TIPUS_SENSOR = 1;
    private static final int KG_MARGE_PES = 1;
    private static final double LIMIT_PES_PLAT = 2;
    private static final int HORA_SEGONS = 1; ///Important! Aquí definim quants segons dura una hora a la simulació. Al programa final posar 3600. 1/HORES_PER_SEGON

    //VARIABLES
    private int limitRaccionsDia, percentatgeAvui, raccionsAcumuladesAvui;
    private boolean dreta;
    private double gramsRaccio,horesEntreRaccions, gramsAcumulatAvui, limitDiari;
    
    private Mascota mascota;
    private Diposit diposit;
    private Actuador motorMenjadora;
    private Sensor sensorPlat;
    
    private Timestamp horaUltimaRaccio;
    private long initialTime;
    
    //VARIABLES DE SIMULACIO
    private double ritmeBuidatgeDiposit = 0.05;

    //CONSTRUCTORS
    /**
     * Construeix la Menjadora
     */
    public Menjadora(boolean dreta, Diposit diposit, Actuador motorMenjadora, Mascota mascota, Sensor sensorPlat){
        this.dreta=dreta;
        this.diposit=diposit;
        this.motorMenjadora=motorMenjadora;
        this.sensorPlat=sensorPlat;
        this.raccionsAcumuladesAvui=0;
        this.mascota = mascota;
        this.horaUltimaRaccio = new Timestamp(System.currentTimeMillis());
    }
    
    public Menjadora(boolean dreta){
         this.dreta=dreta;
    }
    public Menjadora(){
    }
 
    //MÈTODES
    /**
     * Afegeix la Menjadora
     * @param dreta booleà dreta o esquerra
     * @param mascota li assigna la Mascota
     * @return una nova Menjadora amb el seu Diposit, Motor i Sensor de pes.
     */
    public static Menjadora addMenjadora(boolean dreta, Mascota mascota){
        Diposit diposit = new Diposit().addDiposit();
        Actuador motor = new Actuador().addMotor();
        Sensor sensorPlat = new Sensor().addSensor(TIPUS_SENSOR,1);  
       
        return new Menjadora(dreta, diposit, motor, mascota, sensorPlat);
    }
    
    //FUNCIONS DE CÀLCUL
    
    /**
     * Recomana la quantitat i el nombre de raccions en funció de les característiques Mascota
     * @param gat booleà que ens indica si és gat o gos
     * @param edat edat en anys [integer]
     * @param pes  pes del animal en kg [double]
     */
    public void setDosisDiaria(boolean gat, int edat, double pes){
        limitDiari = 200;
        limitRaccionsDia= 9;
        double pesNormal = 15;
        if(!gat){
            limitDiari = limitDiari *2;
            limitRaccionsDia= 5;
        }
        if(edat < 3){
            limitDiari = limitDiari / 1.5;
        }else if(edat > 8){
            limitDiari = limitDiari / 1.3;
        }
        if(pes < pesNormal-KG_MARGE_PES){
            limitDiari = limitDiari * 1.2;
        }else if(pes > pesNormal+KG_MARGE_PES){
            limitDiari = limitDiari / 1.2;
        }
        calculaRaccio();
    }
    
    /**
     * En funció del limitDiari i les raccionsAlDia calcula els gramsRaccio i les horesEntreRaccions
     * Utilitza BigDecimal per arrodonir el decimal a 2 a fi de mostrar-lo a la GUI.
     * Comprova si l'usuari ha desactivat la Menjadora (posant els valors limit a 0)
     */    
    public void calculaRaccio(){
       //Si l'usuari posa a 0 aquests valors significa que DESACTIVA el funcionament/simulació d'aquella menjadora
       if(limitDiari == 0 ||  limitRaccionsDia == 0){
           //Desactivem menjadora, bloquem relé i posem tots els valors a zero per aturar el funcionament/simulació
            motorMenjadora.blocaRele();
            gramsRaccio = 0; 
            horesEntreRaccions = 0;
            limitDiari = 0;
            limitRaccionsDia = 0;
            
       }else{
            gramsRaccio = (double)limitDiari / limitRaccionsDia;
            horesEntreRaccions = (double)24 / limitRaccionsDia;
            gramsRaccio = new BigDecimal(gramsRaccio).setScale(2, RoundingMode.HALF_UP).doubleValue();
            horesEntreRaccions = new BigDecimal(horesEntreRaccions).setScale(2, RoundingMode.HALF_UP).doubleValue();    
       }
         
    }

    //                ------------  FUNCIONAMENT -----------
    /**
     * 
     * @param initialTime 
     */
    public void simulaFuncionament(long initialTime){
        if(this.raccionsAcumuladesAvui < this.limitRaccionsDia){
            //condicions per les quals activarem el procés de donar menjar
            
            Timestamp horaActual = new Timestamp(System.currentTimeMillis());
            Timestamp horesEntreRaccionsTs = new Timestamp((long)(horesEntreRaccions * HORA_SEGONS * 1000));
            long debugHrAct = horaActual.getTime();
            long debugHrUltRacc = horaUltimaRaccio.getTime();
            long debugHrEntreRacc = horesEntreRaccionsTs.getTime();
            if(horaActual.getTime() - horaUltimaRaccio.getTime() >= horesEntreRaccionsTs.getTime()){
                if(sensorPlat.getValor() < LIMIT_PES_PLAT){
                    activaMotor(sensorPlat.getValor(), this.gramsRaccio);
                    this.raccionsAcumuladesAvui++;
                    this.horaUltimaRaccio = horaActual;
                    this.gramsAcumulatAvui+= gramsRaccio;
                    System.out.println("\nHem servit "+ raccionsAcumuladesAvui + " raccions a " + mascota.getNom());
                }else{
                    System.out.println("\nTocaria racció a la "+ mascota.getNom() + " però té massa menjar al plat i no li hem donat" );
                }
            }else{
                System.out.println("\nNo és hora de servir racció a "+ mascota.getNom());
            }           
        }else{
            //Bloca el motor si ha assolit el limit de raccions;
            //this.motorMenjadora.blocaRele();
        }
    }
    
    /**
     * Quan el dia (simulat o no) arriba a 24h es posen a 0 els acumulatsAvui.
     */
    public void resetejaDia(){
        this.raccionsAcumuladesAvui = 0;
        this.gramsAcumulatAvui=0;
    }
    /**
     * Omple el plat.
     * Activa el Motor de la Menjadora en funció del contingut del plat.
     * Decrementa el valor del Diposit cada vegada que s'activa.
     * @param pesInicial
     * @param gramsRaccio 
     */
    public void activaMotor(double pesInicial, double gramsRaccio){
        double distanciaBuidatge = ritmeBuidatgeDiposit*(int)gramsRaccio;//Calculem que cada gram de menjar són 
       
        while(sensorPlat.getValor() < pesInicial + gramsRaccio){
            if(!diposit.estaBuit()){
               motorMenjadora.activaRele();
               sensorPlat.setValorSimulador(sensorPlat.getValor()+gramsRaccio);
               if(diposit.getSensorNivell().getValor()-distanciaBuidatge < 0){
                   diposit.getSensorNivell().setValorSimulador(0);
               }else{
                   diposit.getSensorNivell().setValorSimulador(diposit.getSensorNivell().getValor()-distanciaBuidatge);
               }
               diposit.setAlertaDiposit();
               
            }else{
                System.out.println("\nEl diposit està buit!");
                System.out.println("Carregant diposit...");
                diposit.getSensorNivell().setValorSimulador(50);
                System.out.println("Diposit carregat!");
            }           
        }
        System.out.println("Hem carregat "+ gramsRaccio + " grams al plat de " + mascota.getNom());
        System.out.println("La capacitat del diposit és del "+ diposit.getPercentatgeDiposit() + "%");
        System.out.println("El sensor del diposit està "+ diposit.getSensorNivell().getValor() + "cm. del pinso.Esta buit??" + diposit.estaBuit());
    }

    /**
     * Quan el botó Raccio Extra és premut, activa el motor passant-li la quantitat que ha de subministrar
     * @param quantitatRacio 
     */
    public void raccioExtra(double quantitatRacio){
        activaMotor(sensorPlat.getValor(), quantitatRacio);
    }
    
    /**
     * Desactiva el Motor si ha arribat a la consigna
     * @param pesInicial
     * @param gramsRaccio 
     */
    /*public void aturaMotorPerPes(double pesInicial, double gramsRaccio){
        //Atura el motor quan arriba al pes de la Raccio
        //sensorPlat.setValorSimulador(simulador.retornaPlat());
        if(sensorPlat.getValor() >= pesInicial + gramsRaccio){
            motorMenjadora.desactivaRele();
        }         
    }*/
    
    //ACCESSORS
        
    /**
     * Pantalla Configuracio permet canviar aquest paràmetre.
     * Controla que estigui dins del rang
     * @param limitDiari re-asigna el limit diari a la Menjadora
     */
    public void setLimitDiari(int limitDiari){
        //Control d'errors d'entrada
        if(limitDiari >= 0 && limitDiari < 1000){
            this.limitDiari = limitDiari;
        }else{
            limitDiari = 160;
        }
        //Quan setejem limitDiari re-calculem els gramsRaccio i les hores entre raccions
        calculaRaccio();
    }
    
    /**
     * Pantalla Configuracio permet canviar aquest paràmetre.
     * Controla que estigui dins del rang
     * @param limitRaccionsDia re-asigna el limit de raccions al dia a la Menjadora
     */
    public void setRaccionsAlDia(int limitRaccionsDia){
        //Control d'errors d'entrada
        if(limitRaccionsDia >= 0 && limitRaccionsDia < 48){
            this.limitRaccionsDia = limitRaccionsDia;
        }else{
            limitRaccionsDia = 9;
        }
        //Quan setejem limitRaccionsDia re-calculem els gramsRaccio
        calculaRaccio();
    }
    
    public boolean isDreta() {
        return dreta;
    }
    
    public Actuador getMotorMenjadora() {
        return motorMenjadora;
    }
    
    public int getLimitRaccionsDia() {
        return limitRaccionsDia;
    }

    public int getRaccionsAcumuladesAvui() {
        return raccionsAcumuladesAvui;
    }

    public double getGramsRaccio() {
        return gramsRaccio;
    }

    public double getHoresEntreRaccions() {
        return horesEntreRaccions;
    }

    public double getGramsAcumulatAvui() {
        return gramsAcumulatAvui;
    }

    public double getLimitDiari() {
        return limitDiari;
    }
    
    public Mascota getMascota() {
        return mascota;
    }

    public Diposit getDiposit() {
        return diposit;
    }

    public Sensor getSensorPlat() {
        return sensorPlat;
    }  
}
