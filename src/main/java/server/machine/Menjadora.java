/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.machine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import server.machine.io.Simulador;
import server.machine.io.Actuador;
import server.machine.io.Sensor;

/**
 *
 * @author oriol
 * Classe que crea les Menjadores. És cridat per Mascota.
 * Reb el id i els paràmetres de la Mascota.
 * Retorna la Menjadora amb el seu Diposit, el seu Motor i el seu Sensor del plat.
 * Retorna la Menjadora amb les Raccions definides segons les característiques de la Mascota.
 * 
 */
public class Menjadora {
    
    private static final int TIPUS_SENSOR = 1;
    private static final int KG_MARGE_PES = 1;

    //VARIABLES
    private int limitRaccionsDia, percentatgeAvui, raccionsAcumuladesAvui;
    private boolean dreta;
    private double gramsRaccio,horesEntreRaccions, gramsAcumulatAvui, limitDiari;
    private Timestamp horaUltimaRaccio;
    
    private Mascota mascota;
    private Diposit diposit;
    private Actuador motorMenjadora;
    private Sensor sensorPlat;
    private Simulador simulador;


    //CONSTRUCTORS
    public Menjadora(boolean dreta, Diposit diposit, Actuador motorMenjadora, Mascota mascota, Sensor sensorPlat, Simulador simulador){
        this.dreta=dreta;
        this.diposit=diposit;
        this.motorMenjadora=motorMenjadora;
        this.sensorPlat=sensorPlat;
        this.raccionsAcumuladesAvui=0;
        this.mascota = mascota;
        this.simulador=simulador;
        this.horaUltimaRaccio = new Timestamp(System.currentTimeMillis());
    }
    
    public Menjadora(boolean dreta){
         this.dreta=dreta;
    }
    public Menjadora(){
    }


    //ACCESSORS
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
     
    
    //Pantalla Configuracio permet canviar aquest paràmetre
    public void setLimitDiari(int limitDiari){
        //Control d'errors d'entrada
        if(limitDiari > 0 && limitDiari < 1000){
            this.limitDiari = limitDiari;
        }else{
            limitDiari = 160;
        }
        //Quan setejem limitDiari re-calculem els gramsRaccio i les hores entre raccions
        calculaRaccio();
    }
    
    //Pantalla Configuracio permet canviar aquest paràmetre
    public void setRaccionsAlDia(int limitRaccionsDia){
        //Control d'errors d'entrada
        if(limitRaccionsDia > 0 && limitRaccionsDia < 48){
            this.limitRaccionsDia = limitRaccionsDia;
        }else{
            limitRaccionsDia = 9;
        }
        //Quan setejem limitRaccionsDia re-calculem els gramsRaccio
        calculaRaccio();
    }
    
    //MÈTODES
    public static Menjadora addMenjadora(boolean dreta, Mascota mascota){
        Simulador simulador = new Simulador(dreta);
        Diposit diposit = new Diposit().addDiposit(simulador.retornaNivell());
        Actuador motor = new Actuador().addMotor();
        Sensor sensorPlat = new Sensor().addSensor(TIPUS_SENSOR,1);  
       
        return new Menjadora(dreta, diposit, motor, mascota, sensorPlat, simulador);
    }
    
    //FUNCIONS DE CÀLCUL
    
    //Recomana la quantitat i el nombre de raccions en funció de les característiques Mascota
    public void setDosisDiaria(boolean gat, int edat, double pes){
        limitDiari = 200;
        limitRaccionsDia= 9;
        double pesNormal = 15;//Obtenir de taula veterinària
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
    
    //En funció del limitDiari i les raccionsAlDia calcula els gramsRaccio i les horesEntreRaccions    
   public void calculaRaccio(){
        gramsRaccio = (double)limitDiari / limitRaccionsDia;
        horesEntreRaccions = (double)24 / limitRaccionsDia;
        gramsRaccio = new BigDecimal(gramsRaccio).setScale(2, RoundingMode.HALF_UP).doubleValue();
        horesEntreRaccions = new BigDecimal(horesEntreRaccions).setScale(2, RoundingMode.HALF_UP).doubleValue();  
    }

    //------------  FUNCIONAMENT -----------
    
    public void simulaFuncionament(){
        if(this.raccionsAcumuladesAvui < this.limitRaccionsDia){
            //condicions per les quals activarem el procés de donar menjar
            int horaSegons = 1; ///Important! Aquí definim quants segons dura una hora a la simulació. Al programa final posar 3600. 1/HORES_PER_SEGON
            Timestamp horaActual = new Timestamp(System.currentTimeMillis());
            Timestamp horesEntreRaccionsTs = new Timestamp((long)(horesEntreRaccions * horaSegons * 1000));
            long debugHrAct = horaActual.getTime();
            long debugHrUltRacc = horaUltimaRaccio.getTime();
            long debugHrEntreRacc = horesEntreRaccionsTs.getTime();
            if(horaActual.getTime() - horaUltimaRaccio.getTime() >= horesEntreRaccionsTs.getTime()){
                activaMotor(sensorPlat.getValor(), this.gramsRaccio);
                this.raccionsAcumuladesAvui++;
                this.horaUltimaRaccio = horaActual;
                System.out.println("\nHem servit "+ raccionsAcumuladesAvui + " raccions a " + mascota.getNom());
            }else{
                System.out.println("\nNo és hora de servir racció a "+ mascota.getNom());
            }       
            
            
            
        }else{
            //Bloca el motor si ha assolit el limit de raccions;
            this.motorMenjadora.blocaRele();
        }
        simulador.mascotaMenja(sensorPlat, mascota.getNom());
        this.gramsAcumulatAvui+= gramsRaccio;
        
        //Comprova l'estat del dipòsit per actualitzar els nivells d'alerta i buit
        //diposit.comprovaDiposit();
        
        //BLOCA-DESBLOCA MOTOR EN FUNCIÓ DEL NIVELL DEL DIPÒSIT
        if(diposit.estaBuit()){
            motorMenjadora.blocaRele();
        }else if(simulador.carregaDiposit(dreta)){
            System.out.println("El dipòsit de la "+diposit.stringDreta()+" ha estat emplenat");
            motorMenjadora.desblocaRele();
        }
    }
    public void resetejaDia(){
        this.raccionsAcumuladesAvui = 0;
        this.gramsAcumulatAvui=0;
    }
    public void activaMotor(double pesInicial, double gramsRaccio){
        //Transforma gramsRacció en un tems límit que pot estar engegat
        int segonsActivat = (int)gramsRaccio / 2;
        int distanciaBuidatge = 1;
       
        //sensorPlat.setValorSimulador(simulador.retornaPlat());
        while(sensorPlat.getValor() < pesInicial + gramsRaccio){
            if(!diposit.estaBuit()){
               motorMenjadora.activaRele();
               sensorPlat.setValorSimulador(sensorPlat.getValor()+gramsRaccio);
               diposit.getSensorNivell().setValorSimulador(diposit.getSensorNivell().getValor()+distanciaBuidatge); 
            }else{
                System.out.println("\nEl diposit està buit!");
                System.out.println("Carregant diposit...");
                diposit.getSensorNivell().setValorSimulador(0);
                System.out.println("Diposit carregat!");
            }
            
        }
        System.out.println("Hem carregat "+ gramsRaccio + " grams al plat de " + mascota.getNom());
        System.out.println("La capacitat del diposit és del "+ diposit.getPercentatgeDiposit() + "%");
        System.out.println("El sensor del diposit està "+ diposit.getSensorNivell().getValor() + "cm. del pinso.Esta buit??" + diposit.estaBuit());
    }
    

    
    public void aturaMotorPerPes(double pesInicial, double gramsRaccio){
        //Atura el motor quan arriba al pes de la Raccio
        //sensorPlat.setValorSimulador(simulador.retornaPlat());
        if(sensorPlat.getValor() >= pesInicial + gramsRaccio){
            motorMenjadora.desactivaRele();
        }         
    }
    
    public void raccioExtra(double quantitatRacio){
        activaMotor(sensorPlat.getValor(), quantitatRacio);
    }
    
}
