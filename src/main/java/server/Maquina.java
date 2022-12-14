package server;

import gui.controller.Controlador_Principal;
import server.data.Dades;
import server.machine.Mascota;
import server.machine.Menjadora;
import server.machine.io.Simulador;


/**
 * Classe que crea la Maquina. És cridat per Servidor_Menjadora.
 * Reb el id del Servidor_Menjadora.
 * Retorna una Màquina amb dues Mascotes i dues Menjadores, Dades i Controladors amb Pantalles.
 * @author Oriol Coll Salvia
 */
public class Maquina{
    
    //VARIABLES
    private int id;
    private Mascota mascotaDreta, mascotaEsquerra;
    private Menjadora menjadoraDreta, menjadoraEsquerra;
    private Dades dades;
    private Controlador_Principal controlador;
    private Simulador simulador;
    
    //private static long initialTime;
    private int horesExecucio;
    
    //CONSTRUCTORS
    /**
     * Construeix una Maquina amb:
     * @param id
     * @param mascotaDreta les Mascotes
     * @param mascotaEsquerra
     * @param menjadoraDreta les Menjadores amb el seu Diposit
     * @param menjadoraEsquerra
     * @param dades una instància de Dades
     * @param controlador el Controlador que conté totes les Pantalles
     */
    public Maquina(int id, Mascota mascotaDreta, Mascota mascotaEsquerra, Menjadora menjadoraDreta, Menjadora menjadoraEsquerra, Dades dades, Controlador_Principal controlador){
        this.id=id;
        this.mascotaDreta=mascotaDreta;
        this.mascotaEsquerra=mascotaEsquerra;
        this.menjadoraDreta=menjadoraDreta;
        this.menjadoraEsquerra=menjadoraEsquerra;
        this.dades=dades;
        this.controlador=controlador;
        this.horesExecucio=horesExecucio;
        this.simulador = new Simulador (this.menjadoraDreta,this.menjadoraEsquerra,this.menjadoraEsquerra.getSensorPlat().getValor(), this.menjadoraDreta.getSensorPlat().getValor(), this.menjadoraEsquerra.getDiposit().getSensorNivell().getValor(), this.menjadoraDreta.getDiposit().getSensorNivell().getValor());
    }
    public Maquina(){    
    }
    
    //METODES
    
    /**
     * Afegeix una Maquina
     * @param id
     * @return una nova Maquina completa amb tots els seus objectes
     */
    public static Maquina addMaquina(int id){
        
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

        return new Maquina(id, mascotaDreta, mascotaEsquerra, menjadoraDreta, menjadoraEsquerra, dades, controlador);
    }
    
    /**
     * Per a cada execució del programa:
     * Crida el metode simula per a cada Menjadora i Mascota.
     * Escriu les dades de la Menjadora a Influx i també als arrays d'estadísitiques
     * @param horesExecucio per al funcionament de la Simulacio
     */
    public void funcionamentMaquina(int horesExecucio){
        menjadoraDreta.simulaFuncionament(horesExecucio);
        menjadoraEsquerra.simulaFuncionament(horesExecucio);

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
    
    /**
     * En acabar el dia, crida les funcions resetejaDia de les Menjadores que posen els Acumulats a 0 i desbloquen el motor.
     */
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
    
    //ACCESSORS
    public Controlador_Principal getControlador() {
        return controlador;
    }
    
    public Menjadora getMenjadoraDreta() {
        return menjadoraDreta;
        
    }

    public Menjadora getMenjadoraEsquerra() {
        return menjadoraEsquerra;
    }
   
}
