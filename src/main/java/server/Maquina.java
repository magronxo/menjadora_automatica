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
    private Dades dades;
    private Controlador_Principal controlador;
    private Simulador simulador;
    
    private static long initialTime;
    
    //CONSTRUCTORS
    public Maquina(){    
    }
    public Maquina(int id, Mascota mascotaDreta, Mascota mascotaEsquerra, Menjadora menjadoraDreta, Menjadora menjadoraEsquerra, Dades dades, Controlador_Principal controlador, long initialTime){
        this.id=id;
        this.mascotaDreta=mascotaDreta;
        this.mascotaEsquerra=mascotaEsquerra;
        this.menjadoraDreta=menjadoraDreta;
        this.menjadoraEsquerra=menjadoraEsquerra;
        this.dades=dades;
        this.controlador=controlador;
        this.initialTime=initialTime;
        this.simulador = new Simulador (this.menjadoraDreta,this.menjadoraEsquerra,this.menjadoraEsquerra.getSensorPlat().getValor(), this.menjadoraDreta.getSensorPlat().getValor(), this.menjadoraEsquerra.getDiposit().getSensorNivell().getValor(), this.menjadoraDreta.getDiposit().getSensorNivell().getValor());
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
        Dades dades;
        Controlador_Principal controlador;
        
        mascotaDreta = Mascota.addMascota(true);        
        mascotaEsquerra = Mascota.addMascota(false);
        
        menjadoraDreta = Menjadora.addMenjadora(true, mascotaDreta);
        menjadoraEsquerra = Menjadora.addMenjadora(false, mascotaEsquerra);
        
        menjadoraDreta.setDosisDiaria(mascotaDreta.getGat(), mascotaDreta.getEdat(), mascotaDreta.getPesMascota());
        menjadoraEsquerra.setDosisDiaria(mascotaEsquerra.getGat(), mascotaEsquerra.getEdat(), mascotaEsquerra.getPesMascota());

        dades = new Dades();

        //Iniciem la Pantalla Principal
        controlador = new Controlador_Principal().addControlador(menjadoraDreta,menjadoraEsquerra,dades);

        return new Maquina(id, mascotaDreta, mascotaEsquerra, menjadoraDreta, menjadoraEsquerra, dades, controlador, initialTime);
    }
    

    public void funcionamentMaquina(){
        menjadoraDreta.simulaFuncionament(initialTime);
        menjadoraEsquerra.simulaFuncionament(initialTime);

        simulador.setSensorPlat_dreta(menjadoraDreta.getSensorPlat().getValor());
        simulador.setSensorPlat_esquerra(menjadoraEsquerra.getSensorPlat().getValor());
        simulador.simulaMascotes();

        menjadoraDreta.getSensorPlat().setValorSimulador(simulador.getSensorPlat_dreta());
        menjadoraEsquerra.getSensorPlat().setValorSimulador(simulador.getSensorPlat_esquerra());

        //ESCRIVIM DADES DE LA MENJADORA A INFLUX PER A CADA EXECUCIÓ DEL PROGRAMA (SIMULADOR)
        dades.recordMenjadoraDreta("gramsAcumulatsAvui",menjadoraDreta.getGramsAcumulatAvui());
        dades.recordMenjadoraEsquerra("gramsAcumulatsAvui",menjadoraEsquerra.getGramsAcumulatAvui());

        dades.recordMenjadoraDreta("sensorPlat",menjadoraDreta.getSensorPlat().getValor());
        dades.recordMenjadoraEsquerra("sensorPlat",menjadoraEsquerra.getSensorPlat().getValor());
        
        dades.recordMenjadoraDreta("limitDiari",menjadoraDreta.getLimitDiari());
        dades.recordMenjadoraEsquerra("limitDiari",menjadoraEsquerra.getLimitDiari());

        dades.recordMenjadoraDreta("gramsRaccio",menjadoraDreta.getGramsRaccio());
        dades.recordMenjadoraEsquerra("gramsRaccio",menjadoraEsquerra.getGramsRaccio());
        
        //DADES DIPOSIT
        dades.recordMenjadoraDreta("sensorNivell",menjadoraDreta.getDiposit().getSensorNivell().getValor());
        dades.recordMenjadoraEsquerra("sensorNivell",menjadoraEsquerra.getDiposit().getSensorNivell().getValor());
        
        dades.recordMenjadoraDreta("valorAlertaDiposit",menjadoraDreta.getDiposit().getValorAlertaDiposit());
        dades.recordMenjadoraEsquerra("valorAlertaDiposit",menjadoraEsquerra.getDiposit().getValorAlertaDiposit());

        dades.recordMenjadoraDreta("valorDipositBuit",menjadoraDreta.getDiposit().getDIPOSIT_BUIT());
        dades.recordMenjadoraEsquerra("valorDipositBuit",menjadoraEsquerra.getDiposit().getDIPOSIT_BUIT());

    }
    public void resetejaDia(){
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
