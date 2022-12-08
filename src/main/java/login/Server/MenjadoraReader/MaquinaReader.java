package login.Server.MenjadoraReader;

import gui.controller.Controlador_Reader;

public class MaquinaReader {

    MenjadoraReader menjadoraEsquerra;
    MenjadoraReader menjadoraDreta;
    Controlador_Reader controlador = null;
    public MaquinaReader() {
        this.menjadoraDreta = new MenjadoraReader(1);
        this.menjadoraEsquerra = new MenjadoraReader(0);
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
}
