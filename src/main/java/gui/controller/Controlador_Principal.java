/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.controller;

import gui.view.Pantalla_Configuracio;
import gui.view.Pantalla_Estadistiques;
import gui.view.Pantalla_Principal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import server.machine.Menjadora;
import server.Servidor_Menjadora;
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.data.Dades;


/**
 *
 * @author oriol
 */
public class Controlador_Principal {
    
    private static Pantalla_Principal principal;
    private static Controlador_Configuracio confControl;
    private static Menjadora menjadoraDreta, menjadoraEsquerra;
    
    private Calendar cal;
    
    //CONSTRUCTORS
    public Controlador_Principal(Pantalla_Principal principal,Controlador_Configuracio configuracio, Menjadora menjadoraDreta, Menjadora menjadoraEsquerra){
        this.principal=principal;
        //this.confControl=confControl;
        this.menjadoraDreta=menjadoraDreta;
        this.menjadoraEsquerra=menjadoraEsquerra;
        principal.setVisible(true);
    }
    public Controlador_Principal(){
        this.principal=null;
        this.confControl=null;
        this.menjadoraDreta=null;
        this.menjadoraEsquerra=null;
    }
    
    //METODES
    public static Controlador_Principal addControlador(Menjadora menjadoraDreta, Menjadora menjadoraEsquerra, Dades dades){

        Pantalla_Configuracio confScreen = new Pantalla_Configuracio();
        Pantalla_Estadistiques chartScreen = new Pantalla_Estadistiques(dades);
        
        Pantalla_Principal principal = new Pantalla_Principal(confScreen,chartScreen);
        
        confControl = new Controlador_Configuracio();
        confControl.addControlador(confScreen, menjadoraDreta, menjadoraEsquerra);

        return new Controlador_Principal(principal,confControl, menjadoraDreta, menjadoraEsquerra);
    }

    //FUNCIONS
    public void escriuValorsGui (){
        
        //Nivell alerta esta amagat
        principal.getAlertaDipositDretaIcon().setVisible(false);
        principal.getAlertaDipositEsquerraIcon().setVisible(false);
        //El pintem si el dipòsit marca Alerta 
        if(menjadoraDreta.getDiposit().isAlertaDiposit()){
            principal.getAlertaDipositDretaIcon().setVisible(true);
        }
        if(menjadoraEsquerra.getDiposit().isAlertaDiposit()){
            principal.getAlertaDipositEsquerraIcon().setVisible(true);
        }
        
        //Icona rotacio Motor
        //La pintem si el interruptor del motor està accionat
        if(menjadoraDreta.getMotorMenjadora().isInterruptor()){
            principal.getSimulaMotorDretaIcon().setVisible(true);
            try {
                    TimeUnit.SECONDS.sleep(1);//Important! Definim el temps entre execucions del programa
                    principal.getSimulaMotorDretaIcon().setVisible(false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Servidor_Menjadora.class.getName()).log(Level.SEVERE, null, ex);
                }
            menjadoraDreta.getMotorMenjadora().desactivaRele();
        }else{
            principal.getSimulaMotorDretaIcon().setVisible(false);
        }
        if(menjadoraEsquerra.getMotorMenjadora().isInterruptor()){
            principal.getSimulaMotorEsquerraIcon().setVisible(true);
            try {
                    TimeUnit.SECONDS.sleep(1);//Important! Definim el temps entre execucions del programa
                    principal.getSimulaMotorEsquerraIcon().setVisible(false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Servidor_Menjadora.class.getName()).log(Level.SEVERE, null, ex);
                }
            menjadoraEsquerra.getMotorMenjadora().desactivaRele();
        }else{
            principal.getSimulaMotorEsquerraIcon().setVisible(false);
        }
        
        //Icona Block Motor
        if(menjadoraDreta.getMotorMenjadora().isBlock()){
            principal.getBlockMotorDretaIcon().setVisible(true);
        }else{
            principal.getBlockMotorDretaIcon().setVisible(false);
        }
        
        if(menjadoraEsquerra.getMotorMenjadora().isBlock()){
            principal.getBlockMotorEsquerraIcon().setVisible(true);
        }else{
            principal.getBlockMotorEsquerraIcon().setVisible(false);
        }
 
        //PINTEM LA RESTA DE VALORS DE LA PANTALLA PRINCIPAL
        //Arrodonim els sensors de pes a 2 decimals:
        double gramsPlatDreta = new BigDecimal(this.menjadoraDreta.getSensorPlat().getValor()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double gramsPlatEsquerra = new BigDecimal(this.menjadoraEsquerra.getSensorPlat().getValor()).setScale(1, RoundingMode.HALF_UP).doubleValue();
               
        this.principal.getAcumulatGramsDreta().setText(String.valueOf((int)this.menjadoraDreta.getGramsAcumulatAvui()+" grams"));
        this.principal.getAcumulatGramsEsquerra().setText(String.valueOf((int)this.menjadoraEsquerra.getGramsAcumulatAvui()+" grams"));
        
        this.principal.getAcumulatRaccionsDreta().setText(String.valueOf(this.menjadoraDreta.getRaccionsAcumuladesAvui()+" de"));
        this.principal.getAcumulatRaccionsEsquerra().setText(String.valueOf(this.menjadoraEsquerra.getRaccionsAcumuladesAvui()+" de"));
        
        this.principal.getGramsPlatDreta().setText(String.valueOf(gramsPlatDreta +" g."));
        this.principal.getGramsPlatEsquerra().setText(String.valueOf(gramsPlatEsquerra +" g."));
        
        this.principal.getGramsRaccioDreta().setText(String.valueOf(this.menjadoraDreta.getGramsRaccio()+" grams"));
        this.principal.getGramsRaccioEsquerra().setText(String.valueOf(this.menjadoraEsquerra.getGramsRaccio()+" grams"));
        
        this.principal.getHoresEntreRaccionsDreta().setText(String.valueOf(this.menjadoraDreta.getHoresEntreRaccions()));
        this.principal.getHoresEntreRaccionsEsquerra().setText(String.valueOf(this.menjadoraEsquerra.getHoresEntreRaccions()));
        
        this.principal.getLimitDiariDreta().setText(String.valueOf((int)this.menjadoraDreta.getLimitDiari()+" grams"));
        this.principal.getLimitDiariEsquerra().setText(String.valueOf((int)this.menjadoraEsquerra.getLimitDiari()+" grams"));
        
        this.principal.getNivellDipositDreta().setText(String.valueOf(this.menjadoraDreta.getDiposit().getSensorNivell().getValor()+" cm"));
        this.principal.getNivellDipositEsquerra().setText(String.valueOf(this.menjadoraEsquerra.getDiposit().getSensorNivell().getValor()+" cm"));
        
        this.principal.getNomMascotaDreta().setText(this.menjadoraDreta.getMascota().getNom());
        this.principal.getNomMascotaEsquerra().setText(this.menjadoraEsquerra.getMascota().getNom());
        
        this.principal.getRaccionsDreta().setText(String.valueOf(this.menjadoraDreta.getLimitRaccionsDia()));
        this.principal.getRaccionsEsquerra().setText(String.valueOf(this.menjadoraEsquerra.getLimitRaccionsDia()));
        
        this.principal.getHoraPantalla().setText(String.valueOf((int)Servidor_Menjadora.getHoresExecucio())+" hores");
        
        if(!menjadoraDreta.getMotorMenjadora().isBlock()){
            this.principal.progressBarDreta.setValue(((int)this.menjadoraDreta.getGramsAcumulatAvui()*100)/(int)this.menjadoraDreta.getLimitDiari());
        }
        while(!menjadoraEsquerra.getMotorMenjadora().isBlock()){
            this.principal.progressBarEsquerra.setValue(((int)this.menjadoraEsquerra.getGramsAcumulatAvui()*100)/(int)this.menjadoraEsquerra.getLimitDiari());
            return;
        }
    }
    public void sumaDia(int diesSimulats){
        //int i=1;
        if(diesSimulats==0){
            this.principal.getDiaMesHoraPantalla().setText(retornaDia());
        }else{
            cal = Calendar.getInstance();
            Timestamp date = new Timestamp(System.currentTimeMillis());
            cal.setTime(date);
            cal.add(Calendar.DATE, diesSimulats); //minus number would decrement the days
            SimpleDateFormat sdf = new SimpleDateFormat("EEEEEEEEE,dd-MM-yyyy", new Locale ("ca","CA"));
            String diaSumat = sdf.format(cal.getTime());
            this.principal.getDiaMesHoraPantalla().setText(diaSumat);
        }
        
    }

    public String retornaDia(){
        Calendar cal = Calendar.getInstance();  
        SimpleDateFormat sdf = new SimpleDateFormat("EEEEEEEEE,dd-MM-yyyy", new Locale ("ca","CA"));  
        return sdf.format(cal.getTime());
    }
    
    //ACCESSORS
    public static Controlador_Configuracio getConfControl() {
        return confControl;
    }

    public Pantalla_Principal getPrincipal() {
        return principal;
    }

    public void setMenjadoraDreta(Menjadora menjadoraDreta) {
        this.menjadoraDreta = menjadoraDreta;
    }

    public void setMenjadoraEsquerra(Menjadora menjadoraEsquerra) {
        this.menjadoraEsquerra = menjadoraEsquerra;
    }   
}
