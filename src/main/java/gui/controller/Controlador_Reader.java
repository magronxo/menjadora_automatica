/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.controller;

import gui.view.Pantalla_Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import login.Client.MenjadoraReader.MenjadoraReader;
import server.Servidor_Menjadora;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


/**
 *
 * @author oriol
 */
public class Controlador_Reader implements ActionListener {

    private Pantalla_Principal principal = new Pantalla_Principal();
    private MenjadoraReader menjadoraDreta, menjadoraEsquerra;



    /*public Controlador_Reader(Principal principal, Menjadora menjadoraDreta, Menjadora menjadoraEsquerra){
        this.principal=principal;
        this.menjadoraDreta=menjadoraDreta;
        this.menjadoraEsquerra=menjadoraEsquerra;
        
    }*/
    public Controlador_Reader(Pantalla_Principal principal, MenjadoraReader menjadoraDreta, MenjadoraReader menjadoraEsquerra){
        this.principal=principal;
        this.menjadoraDreta=menjadoraDreta;
        this.menjadoraEsquerra=menjadoraEsquerra;
        principal.setVisible(true);
    }
    public Controlador_Reader(){
        this.principal=null;
        this.menjadoraDreta=null;
        this.menjadoraEsquerra=null;
    }
    public void escriuValorsGui (){
        //Arrodonim els sensors a 2 decimals:
        double gramsPlatDreta = new BigDecimal(this.menjadoraDreta.getSensorPlat()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double gramsPlatEsquerra = new BigDecimal(this.menjadoraEsquerra.getSensorPlat()).setScale(1, RoundingMode.HALF_UP).doubleValue();


        this.principal.getAcumulatGramsDreta().setText(String.valueOf((int)this.menjadoraDreta.getGramsAcumulatAvui()+" grams"));
        this.principal.getAcumulatGramsEsquerra().setText(String.valueOf((int)this.menjadoraEsquerra.getGramsAcumulatAvui()+" grams"));

        this.principal.getAcumulatRaccionsDreta().setText(String.valueOf(this.menjadoraDreta.getRaccionsAcumuladesAvui()+" de"));
        this.principal.getAcumulatRaccionsEsquerra().setText(String.valueOf(this.menjadoraEsquerra.getRaccionsAcumuladesAvui()+" de"));

        this.principal.getGramsPlatDreta().setText(String.valueOf(gramsPlatDreta +" g."));
        this.principal.getGramsPlatEsquerra().setText(String.valueOf(gramsPlatEsquerra +" g."));

        this.principal.getGramsRaccioDreta().setText(String.valueOf(this.menjadoraDreta.getGramsRaccio()+" grams"));
        this.principal.getGramsRaccioEsquerra().setText(String.valueOf(this.menjadoraEsquerra.getGramsRaccio()+" grams"));

        this.principal.getLimitDiariDreta().setText(String.valueOf((int)this.menjadoraDreta.getLimitDiari()+" grams"));
        this.principal.getLimitDiariEsquerra().setText(String.valueOf((int)this.menjadoraEsquerra.getLimitDiari()+" grams"));

        this.principal.getNivellDipositDreta().setText(String.valueOf(this.menjadoraDreta.getValorDiposit()+" cm"));
        this.principal.getNivellDipositEsquerra().setText(String.valueOf(this.menjadoraEsquerra.getValorDiposit()+" cm"));

        this.principal.getNomMascotaDreta().setText(this.menjadoraDreta.getMascota().getNom());
        this.principal.getNomMascotaEsquerra().setText(this.menjadoraEsquerra.getMascota().getNom());

        this.principal.getRaccionsDreta().setText(String.valueOf(this.menjadoraDreta.getLimitRaccionsDia()));
        this.principal.getRaccionsEsquerra().setText(String.valueOf(this.menjadoraEsquerra.getLimitRaccionsDia()));

        this.principal.getHoraPantalla().setText(String.valueOf(Servidor_Menjadora.getHoresExecucio())+" hores");
        this.principal.getDiaMesHoraPantalla().setText(retornaDia());

        this.principal.progressBarDreta.setValue(((int)this.menjadoraDreta.getGramsAcumulatAvui()*100)/(int)this.menjadoraDreta.getLimitDiari());
        this.principal.progressBarEsquerra.setValue(((int)this.menjadoraEsquerra.getGramsAcumulatAvui()*100)/(int)this.menjadoraEsquerra.getLimitDiari());

    }

    public String retornaDia(){


        //returns a Calendar object whose calendar fields have been initialized with the current date and time  
        Calendar cal = Calendar.getInstance();
        //creating a constructor of the SimpleDateFormat class  
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        //getting current date  
        //System.out.println("Today's date: "+sdf.format(cal.getTime()));  
        //creating a constructor of the Format class  
        //displaying full-day name  
        Format f = new SimpleDateFormat("EEEE");
        String dia = f.format(new Date());
        //prints day name  
        System.out.println("Day Name: "+dia);
        if(dia.equals("lunes"))dia="Dilluns";
        if(dia.equals("martes"))dia="Dimarts";
        if(dia.equals("miercoles"))dia="Dimecres";
        if(dia.equals("jueves"))dia="Dijous";
        if(dia.equals("viernes"))dia="Divendres";
        if(dia.equals("sabado"))dia="Dissabte";
        if(dia.equals("domingo"))dia="Diumenge";

        return dia+", "+sdf.format(cal.getTime());
    }


    /*public void setUp (){
        this.principal.setVisible(true);

        this.principal.raccioExtraDreta.addActionListener(this);
        
        
        //this.principal.nomMascotaDreta.enableInputMethods(true);
        //this.principal.nomMascotaDreta.getAccessibleContext();
        //this.principal.nomMascotaDreta.firePropertyChange("", 0, 0);
        //this.principal.nomMascotaDreta.addAncestorListener((AncestorListener) this);
        //this.principal.nomMascotaDreta.getAncestorListeners();
        
        //principal.limitDiariEsquerraPropertyChange(String.valueOf(this.menjadoraEsquerra.getLimitDiari()));
        //principal.limitDiariEsquerraPropertyChange("");
        
        
        //PropertyChangeListener[] listeners = new PropertyChangeListener[25];
        //listeners = principal.getPropertyChangeListeners();
        //System.out.println(listeners);
        
    }*/
    public static Controlador_Reader addControlador(MenjadoraReader menjadoraDreta, MenjadoraReader menjadoraEsquerra){
        //Principal principal ;
        //Menjadora menjadoraDreta, menjadoraEsquerra;
        Pantalla_Principal principal = new Pantalla_Principal();
        //principal.canviaL imitDiariEsquerra("");
        return new Controlador_Reader(principal, menjadoraDreta, menjadoraEsquerra);
        //principal.setVisible(true);
    }

    public void setMenjadoraDreta(MenjadoraReader menjadoraDreta) {
        this.menjadoraDreta = menjadoraDreta;
    }

    public void setMenjadoraEsquerra(MenjadoraReader menjadoraEsquerra) {
        this.menjadoraEsquerra = menjadoraEsquerra;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
