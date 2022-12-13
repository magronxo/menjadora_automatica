/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import gui.controller.Controlador_Principal;
import server.data.Dades;
import server.machine.Mascota;
import server.machine.Menjadora;
import server.machine.io.Simulador;


/**
 *
 * @author oriol
 * 
 * Classe que crea la Maquina. És cridat per Servidor_Menjadora.
 * Reb el id del Servidor_Menjadora.
 * Retorna una Màquina amb dues Mascotes i dues Menjadores.
 * L'atribut raccioExtra és comú per a tota la Maquina.
 */
public class Maquina{
    
    //VARIABLES
    private int id;
    //private boolean dreta;
    private Mascota mascotaDreta, mascotaEsquerra;
    private Menjadora menjadoraDreta, menjadoraEsquerra;
    private Dades dadesDreta, dadesEsquerra;
    private Controlador_Principal controlador;
    private Simulador simulador;
    
    private static long initialTime;
    
    //CONSTRUCTORS
    public Maquina(){    
    }
    public Maquina(int id, Mascota mascotaDreta, Mascota mascotaEsquerra, Menjadora menjadoraDreta, Menjadora menjadoraEsquerra, Dades dadesDreta, Dades dadesEsquerra, Controlador_Principal controlador, long initialTime){
        this.id=id;
        this.mascotaDreta=mascotaDreta;
        this.mascotaEsquerra=mascotaEsquerra;
        this.menjadoraDreta=menjadoraDreta;
        this.menjadoraEsquerra=menjadoraEsquerra;
        this.dadesDreta=dadesDreta;
        this.dadesEsquerra=dadesEsquerra;
        this.controlador=controlador;
        this.initialTime=initialTime;
        this.simulador = new Simulador (this.menjadoraEsquerra.getSensorPlat().getValor(), this.menjadoraDreta.getSensorPlat().getValor(), this.menjadoraEsquerra.getDiposit().getSensorNivell().getValor(), this.menjadoraDreta.getDiposit().getSensorNivell().getValor());
    }

    public Menjadora getMenjadoraDreta() {
        return menjadoraDreta;
        
    }

    public Menjadora getMenjadoraEsquerra() {
        return menjadoraEsquerra;
    }
    
    
    //ACCESSORS
    public void setRaccioExtra(int raccioExtra) {
        //Pantalla Principal permet canviar aquest paràmetre
        raccioExtra = raccioExtra;
    }

    public Controlador_Principal getControlador() {
        return controlador;
    }
    

    //METODES
    public static Maquina addMaquina(int id, long initialTime){
        
        Mascota mascotaDreta, mascotaEsquerra;
        Menjadora menjadoraDreta, menjadoraEsquerra;
        Dades dadesDreta, dadesEsquerra;
        Controlador_Principal controlador;
        
        mascotaDreta = Mascota.addMascota(true);        
        mascotaEsquerra = Mascota.addMascota(false);
        
        menjadoraDreta = Menjadora.addMenjadora(true, mascotaDreta);
        menjadoraEsquerra = Menjadora.addMenjadora(false, mascotaEsquerra);
        
        menjadoraDreta.setDosisDiaria(mascotaDreta.getGat(), mascotaDreta.getEdat(), mascotaDreta.getPesMascota());
        menjadoraEsquerra.setDosisDiaria(mascotaEsquerra.getGat(), mascotaEsquerra.getEdat(), mascotaEsquerra.getPesMascota());

        dadesDreta = new Dades().addDades(menjadoraDreta);        
        dadesEsquerra = new Dades().addDades(menjadoraEsquerra);

        //Iniciem la Pantalla Principal
        controlador = new Controlador_Principal().addControlador(menjadoraDreta, menjadoraEsquerra,dadesDreta,dadesEsquerra);

        return new Maquina(id, mascotaDreta, mascotaEsquerra, menjadoraDreta, menjadoraEsquerra, dadesDreta, dadesEsquerra, controlador, initialTime);
    }
    

    public void funcionamentMaquina(){

        menjadoraDreta.simulaFuncionament(initialTime);
        menjadoraEsquerra.simulaFuncionament(initialTime);
        simulador.setSensorPlat_dreta(menjadoraDreta.getSensorPlat().getValor());
        simulador.setSensorPlat_esquerra(menjadoraEsquerra.getSensorPlat().getValor());
        simulador.simulaMascotes();
        menjadoraDreta.getSensorPlat().setValorSimulador(simulador.getSensorPlat_dreta());
        menjadoraEsquerra.getSensorPlat().setValorSimulador(simulador.getSensorPlat_esquerra());
        
    }
    public void resetejaDia(){
        try{
        //ESCRIVIM LES DADES DE LA MENJADORA
        dadesDreta.recordMenjadoraDreta("limitDiari",menjadoraDreta.getLimitDiari());
        dadesEsquerra.recordMenjadoraEsquerra("limitDiari",menjadoraEsquerra.getLimitDiari());
        
        //L'acumulat avui el voldria escriure cada execució 60s
        //dadesDreta.recordMenjadoraDreta("gramsAcumulatsAvui",menjadoraDreta.getGramsAcumulatAvui());
        dadesEsquerra.recordMenjadoraEsquerra("gramsAcumulatsAvui",menjadoraEsquerra.getGramsAcumulatAvui());

        //dadesDreta.recordMenjadoraDreta("gramsRaccio",menjadoraDreta.getGramsRaccio());
        //dadesEsquerra.recordMenjadoraEsquerra("gramsRaccio",menjadoraEsquerra.getGramsRaccio());
        
        //Sensor Plat el voldria escriure cada mitja execució (30s)
        //dadesDreta.recordMenjadoraDreta("sensorPlat",menjadoraDreta.getSensorPlat().getValor());
        //dadesEsquerra.recordMenjadoraEsquerra("sensorPlat",menjadoraEsquerra.getSensorPlat().getValor());
        
        //ESCRIVIM LES DADES DEL DIPOSIT
        //dadesDreta.recordDipositDreta("sensorNivell",menjadoraDreta.getDiposit().getSensorNivell().getValor());
        //dadesEsquerra.recordDipositEsquerra("sensorNivell",menjadoraEsquerra.getDiposit().getSensorNivell().getValor());
        
        //dadesDreta.recordDipositDreta("valorAlertaDiposit",menjadoraDreta.getDiposit().getValorAlertaDiposit());
        //dadesEsquerra.recordDipositEsquerra("valorAlertaDiposit",menjadoraEsquerra.getDiposit().getValorAlertaDiposit());
        
        //dadesDreta.recordDipositDreta("valorDipositBuit",menjadoraDreta.getDiposit().getDIPOSIT_BUIT());
        //dadesEsquerra.recordDipositEsquerra("valorDipositBuit",menjadoraEsquerra.getDiposit().getDIPOSIT_BUIT());
        //dadesDreta.llegeixDadesMenjadora();
        //dadesEsquerra.llegeixDadesMenjadora();
        
        //dadesDreta.llegeixDadesDiposit();
        //dadesEsquerra.llegeixDadesDiposit();
        }catch(Exception e){
             System.out.println("Influx Error");     
        }

        menjadoraDreta.resetejaDia();
        menjadoraEsquerra.resetejaDia();

        //DESBLOCA EL MOTOR SI EL DIPÒSTI NO ESTA BUIT
        if(!menjadoraDreta.getDiposit().estaBuit() && !menjadoraDreta.getMotorMenjadora().isBlock()){
            menjadoraDreta.getMotorMenjadora().desblocaRele();
        }
        if(!menjadoraEsquerra.getDiposit().estaBuit() && !menjadoraEsquerra.getMotorMenjadora().isBlock()){
            menjadoraEsquerra.getMotorMenjadora().desblocaRele();
        }
    }
}
