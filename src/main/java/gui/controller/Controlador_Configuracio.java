/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.controller;

import gui.view.Pantalla_Configuracio;
import server.machine.Mascota;
import server.machine.Menjadora;

/**
 *
 * @author oriol
 */
public class Controlador_Configuracio {
    
    private static Pantalla_Configuracio confScreen;
    private static Menjadora menjadoraDreta, menjadoraEsquerra;
    private static Mascota mascotaDreta =  new Mascota(true);
    private static Mascota mascotaEsquerra =  new Mascota(false);
    private static int raccioExtra = 5;
        
    public Controlador_Configuracio(Pantalla_Configuracio confScreen, Menjadora menjadoraDreta, Menjadora menjadoraEsquerra){
        this.confScreen=confScreen;
        this.menjadoraDreta=menjadoraDreta;
        this.menjadoraEsquerra=menjadoraEsquerra;
    }
    public Controlador_Configuracio(){
        this.confScreen=null;
        this.menjadoraDreta=null;
        this.menjadoraEsquerra=null;
    }
    
    //METODES
    public Controlador_Configuracio addControlador(Pantalla_Configuracio confScreen,Menjadora menjadoraDreta, Menjadora menjadoraEsquerra){
        
        return new Controlador_Configuracio(confScreen, menjadoraDreta, menjadoraEsquerra);
    }
    
    //Actualitza els valors de les Mascotes i Menjadores en obrir la Pantalla Configuració
    public void setejaPantallaConfiguracio(){
        
        //edat
        confScreen.getEdatMascotaDreta().setText(String.valueOf(menjadoraDreta.getMascota().getEdat()));
        confScreen.getEdatMascotaEsquerra().setText(String.valueOf(menjadoraEsquerra.getMascota().getEdat()));
        //pes
        confScreen.getPesMascotaDreta().setText(String.valueOf(menjadoraDreta.getMascota().getPesMascota()));
        confScreen.getPesMascotaEsquerra().setText(String.valueOf(menjadoraEsquerra.getMascota().getPesMascota()));
        //limits diaris i limits raccions
        confScreen.getLimitDiariDretaText().setText(String.valueOf(menjadoraDreta.getLimitDiari()));       
        confScreen.getLimitDiariEsquerraText().setText(String.valueOf(menjadoraEsquerra.getLimitDiari()));      
        confScreen.getRaccionsDiariesDretaText().setText(String.valueOf(menjadoraDreta.getLimitRaccionsDia()));
        confScreen.getRaccionsDiariesEquerraText().setText(String.valueOf(menjadoraEsquerra.getLimitRaccionsDia()));
        //Limit alerta diposit
        confScreen.getAlertaDipositDretaText().setText(String.valueOf(menjadoraDreta.getDiposit().getValorAlertaDiposit()));
        confScreen.getAlertaDipositEsquerraText().setText(String.valueOf(menjadoraEsquerra.getDiposit().getValorAlertaDiposit()));
        //Racció extra
        confScreen.getRaccioExtraText().setText(String.valueOf(raccioExtra));
    }
    
    //En premer el botó ACTUALITZA MASCOTES, edita les propietats de les Mascotes
    public void editaMascota(){

        String nomDreta,nomEsquerra;
        boolean gatDreta, gatEsquerra;
        int edatDreta, edatEsquerra;
        double pesDreta, pesEsquerra;

        nomDreta = mascotaDreta.getNom();
        nomEsquerra = mascotaEsquerra.getNom();
        
        gatDreta = mascotaDreta.getGat();
        gatEsquerra = mascotaEsquerra.getGat();
        
        edatDreta = mascotaDreta.getEdat();
        edatEsquerra = mascotaEsquerra.getEdat();
        
        pesDreta = mascotaDreta.getPesMascota();
        pesEsquerra = mascotaEsquerra.getPesMascota();
        //Actualitzem Mascotes
        menjadoraDreta.getMascota().actualitzaMascota(nomDreta, gatDreta, edatDreta, pesDreta);
        menjadoraEsquerra.getMascota().actualitzaMascota(nomEsquerra, gatEsquerra, edatEsquerra, pesEsquerra);
        //Re-calculem les dosis diaries        
        menjadoraDreta.setDosisDiaria(gatDreta, edatDreta, pesDreta);
        menjadoraEsquerra.setDosisDiaria(gatEsquerra, edatEsquerra, pesEsquerra);      
    }
    
    //En premer el botó ACTUALITZA LÍMITS, edita les propietats de les Menjadores
    public void setejaLimitsDiaris(){
        
        try{//filtrem els valors no numèrics
            //Limit diari i limit raccions
            int limitDreta = (int)Double.parseDouble(confScreen.getLimitDiariDretaText().getText());
            int limitEsquerra = (int)Double.parseDouble(confScreen.getLimitDiariEsquerraText().getText());
            int limitRaccionsDreta = Integer.valueOf(confScreen.getRaccionsDiariesDretaText().getText());
            int limitRaccionsEsquerra = Integer.valueOf(confScreen.getRaccionsDiariesEquerraText().getText());
            
            menjadoraDreta.setLimitDiari(limitDreta);
            menjadoraEsquerra.setLimitDiari(limitEsquerra);
        
            menjadoraDreta.setRaccionsAlDia(limitRaccionsDreta);
            menjadoraEsquerra.setRaccionsAlDia(limitRaccionsEsquerra);
            //Limit nivell alerta dipòsit
            menjadoraDreta.getDiposit().setValorAlertaDiposit((int)Double.parseDouble(confScreen.getAlertaDipositDretaText().getText()));
            menjadoraEsquerra.getDiposit().setValorAlertaDiposit((int)Double.parseDouble(confScreen.getAlertaDipositEsquerraText().getText()));
            //Raccio extra Menjadores
            raccioExtra = (int)Double.parseDouble(confScreen.getRaccioExtraText().getText());
            
        }catch(NumberFormatException ex){
            System.err.println("\t\t\t ERROR!! Límits Menjadora invàlids !!!!");
        }       
    }
    //Desde els botons de la Pantalla Principal cridem aquesta funció i guardem la variable raccioExtra en aquesta mateixa classe
    //Activa el motor de la Menjadora passant-li els grams de la raccioExtra
    public void donaRaccioExtra(boolean dreta, int raccioExtra){
        if(dreta){
            menjadoraDreta.activaMotor(0, raccioExtra);
        }else{
            menjadoraEsquerra.activaMotor(0, raccioExtra);
        }
    }
    
    //ACCESSORS
    public static Pantalla_Configuracio getConfScreen() {
        return confScreen;
    }

    public static Menjadora getMenjadoraDreta() {
        return menjadoraDreta;
    }

    public static Menjadora getMenjadoraEsquerra() {
        return menjadoraEsquerra;
    }

    public Mascota getMascotaDreta() {
        return mascotaDreta;
    }

    public Mascota getMascotaEsquerra() {
        return mascotaEsquerra;
    }

    public static void setConfScreen(Pantalla_Configuracio confScreen) {
        Controlador_Configuracio.confScreen = confScreen;
    }

    public static void setMenjadoraDreta(Menjadora menjadoraDreta) {
        Controlador_Configuracio.menjadoraDreta = menjadoraDreta;
    }

    public static void setMenjadoraEsquerra(Menjadora menjadoraEsquerra) {
        Controlador_Configuracio.menjadoraEsquerra = menjadoraEsquerra;
    }

    
    
}
