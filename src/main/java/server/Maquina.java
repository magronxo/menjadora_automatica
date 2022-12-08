/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import gui.controller.Controlador_Principal;
import server.data.Dades;
import server.machine.Mascota;
import server.machine.Menjadora;


/**
 *
 * @author oriol
 * 
 * Classe que crea la Maquina. És cridat per Servidor_Menjadora.
 * Reb el id del Servidor_Menjadora.
 * Retorna una Màquina amb dues Mascotes i dues Menjadores.
 * L'atribut raccioExtra és comú per a tota la Maquina.
 */
public class Maquina {
    
    //VARIABLES
    private int id;
    //private boolean dreta;
    private Mascota mascotaDreta, mascotaEsquerra;
    private Menjadora menjadoraDreta, menjadoraEsquerra;
    private Dades dadesDreta, dadesEsquerra;
    private Controlador_Principal controlador;
    
    //CONSTRUCTORS
    public Maquina(){    
    }
    public Maquina(int id, Mascota mascotaDreta, Mascota mascotaEsquerra, Menjadora menjadoraDreta, Menjadora menjadoraEsquerra, Dades dadesDreta, Dades dadesEsquerra, Controlador_Principal controlador){
        this.id=id;
        this.mascotaDreta=mascotaDreta;
        this.mascotaEsquerra=mascotaEsquerra;
        this.menjadoraDreta=menjadoraDreta;
        this.menjadoraEsquerra=menjadoraEsquerra;
        this.dadesDreta=dadesDreta;
        this.dadesEsquerra=dadesEsquerra;
        this.controlador=controlador;
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
    public static Maquina addMaquina(int id){
        
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
        controlador = new Controlador_Principal().addControlador(menjadoraDreta, menjadoraEsquerra);
        //crea org i bucket influx
        
        return new Maquina(id, mascotaDreta, mascotaEsquerra, menjadoraDreta, menjadoraEsquerra, dadesDreta, dadesEsquerra, controlador);
    }
    
    public void funcionamentMaquina(){
        menjadoraDreta.simulaFuncionament();
        menjadoraEsquerra.simulaFuncionament();
    }
    public void resetejaDia(){
        //Creem DataBase (Org amb 2 buckets) quan l'usuari inicia sessio per primer cop
        //dadesDreta.creaDataBase();
        
        //dadesDreta.escriuDades(menjadoraDreta.getGramsAcumulatAvui());
        //dadesEsquerra.escriuDades(menjadoraEsquerra.getGramsAcumulatAvui());
        
        //dadesDreta.llegeixDades();
        //dadesEsquerra.llegeixDades();
        
        menjadoraDreta.resetejaDia();
        menjadoraEsquerra.resetejaDia();
        
        
        //controlador.escriuValorsGui();
        
        //DESBLOCA EL MOTOR SI EL DIPÒSTI NO ESTA BUIT
        if(!menjadoraDreta.getDiposit().estaBuit()){
            menjadoraDreta.getMotorMenjadora().desblocaRele();
        }
        if(!menjadoraEsquerra.getDiposit().estaBuit()){
            menjadoraEsquerra.getMotorMenjadora().desblocaRele();
        }
    }
}
