package login.Client.MenjadoraReader;

import gui.controller.Controlador_Reader;

public class MaquinaReader {

    MenjadoraReader menjadoraEsquerra;
    MenjadoraReader menjadoraDreta;

    Controlador_Reader controlador = null;
    public MaquinaReader() {
        this.menjadoraDreta = new MenjadoraReader();
        this.menjadoraEsquerra = new MenjadoraReader();
    }

    public void setMenjadoraEsquerra(MenjadoraReader menjadoraEsquerra) {
        this.menjadoraEsquerra = menjadoraEsquerra;
    }

    public void setMenjadoraDreta(MenjadoraReader menjadoraDreta) {
        this.menjadoraDreta = menjadoraDreta;
    }

    public MenjadoraReader getMenjadoraEsquerra() {
        return menjadoraEsquerra;
    }

    public MenjadoraReader getMenjadoraDreta() {
        return menjadoraDreta;
    }
    public Controlador_Reader getControlador() {
        if (controlador == null) {
            controlador = new Controlador_Reader().addControlador(menjadoraDreta, menjadoraEsquerra);
        }
        return controlador;
    }
}
