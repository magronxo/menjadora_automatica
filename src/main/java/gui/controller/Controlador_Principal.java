/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.controller;

import gui.view.Pantalla_Configuracio;
import gui.view.Pantalla_Principal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import server.machine.Menjadora;
import server.Servidor_Menjadora;
import java.text.Format;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;  


/**
 *
 * @author oriol
 */
public class Controlador_Principal {
    
    private static Pantalla_Principal principal;
    private static Controlador_Configuracio confControl;
    private static Menjadora menjadoraDreta, menjadoraEsquerra;
    
    //CONSTRUCTORS
    public Controlador_Principal(Pantalla_Principal principal,Controlador_Configuracio configuracio, Menjadora menjadoraDreta, Menjadora menjadoraEsquerra){
        this.principal=principal;
        this.confControl=confControl;
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
    public static Controlador_Principal addControlador(Menjadora menjadoraDreta, Menjadora menjadoraEsquerra){

        Pantalla_Configuracio confScreen = new Pantalla_Configuracio();      
        Pantalla_Principal principal = new Pantalla_Principal(confScreen);
        
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
        if(menjadoraDreta.getDiposit().isAlertaDiposit()){principal.getAlertaDipositDretaIcon().setVisible(true);}
        if(menjadoraEsquerra.getDiposit().isAlertaDiposit()){principal.getAlertaDipositEsquerraIcon().setVisible(true);}
        
        //Icona rotacio Motor
        principal.getSimulaMotorDretaIcon().setVisible(false);
        principal.getSimulaMotorEsquerraIcon().setVisible(false);
        //La pintem si el interruptor del motor està accionat
        if(menjadoraDreta.getMotorMenjadora().isInterruptor()){principal.getSimulaMotorDretaIcon().setVisible(true);}
        if(menjadoraEsquerra.getMotorMenjadora().isInterruptor()){principal.getSimulaMotorEsquerraIcon().setVisible(true);}
        
        //Icona Block Motor
        principal.getBlockMotorDretaIcon().setVisible(false);
        principal.getBlockMotorEsquerraIcon().setVisible(false);
        //La pintem si el motor de la Menjadora esta bloquejat. El diposit estaBuit() també  el bloqueja
        if(menjadoraDreta.getMotorMenjadora().isBlock()){principal.getBlockMotorDretaIcon().setVisible(true);}
        if(menjadoraEsquerra.getMotorMenjadora().isBlock()){principal.getBlockMotorEsquerraIcon().setVisible(true);}
        
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
        this.principal.getDiaMesHoraPantalla().setText(retornaDia());
        
        this.principal.progressBarDreta.setValue(((int)this.menjadoraDreta.getGramsAcumulatAvui()*100)/(int)this.menjadoraDreta.getLimitDiari());
        this.principal.progressBarEsquerra.setValue(((int)this.menjadoraEsquerra.getGramsAcumulatAvui()*100)/(int)this.menjadoraEsquerra.getLimitDiari());
        
    }
    
    public String retornaDia(){
        //returns a Calendar object whose calendar fields have been initialized with the current date and time  
        Calendar cal = Calendar.getInstance();  
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");  
        Format f = new SimpleDateFormat("EEEE");  
        String dia = f.format(new Date());  
        if(dia.equals("lunes"))dia="Dilluns";
        if(dia.equals("martes"))dia="Dimarts";
        if(dia.equals("miercoles"))dia="Dimecres";
        if(dia.equals("jueves"))dia="Dijous";
        if(dia.equals("viernes"))dia="Divendres";
        if(dia.equals("sabado"))dia="Dissabte";
        if(dia.equals("domingo"))dia="Diumenge";   
        return dia+", "+sdf.format(cal.getTime());
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
