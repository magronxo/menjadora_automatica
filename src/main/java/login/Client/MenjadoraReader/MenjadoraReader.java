package login.Client.MenjadoraReader;

public class MenjadoraReader {
    double sensorPlat;
    double gramsAcumulatAvui;
    double raccionsAcumuladesAvui;
    double gramsRaccio;
    double limitDiari;
    double valorDiposit;
    double limitRaccionsDia;
    MascotaReader mascota;
    public MenjadoraReader() {
        this.mascota = new MascotaReader();
    }


    public void setSensorPlat(double sensorPlat) {
        this.sensorPlat = sensorPlat;
    }
    public void setGramsAcumulatAvui(double gramsAcumulatAvui) {
        this.gramsAcumulatAvui = gramsAcumulatAvui;
    }

    public void setRaccionsAcumuladesAvui(double raccionsAcumuladesAvui) {
        this.raccionsAcumuladesAvui = raccionsAcumuladesAvui;
    }

    public void setGramsRaccio(double gramsRaccio) {
        this.gramsRaccio = gramsRaccio;
    }

    public void setLimitDiari(double limitDiari) {
        this.limitDiari = limitDiari;
    }

    public void setValorDiposit(double valorDiposit) {
        this.valorDiposit = valorDiposit;
    }

    public void setLimitRaccionsDia(double limitRaccionsDia) {
        this.limitRaccionsDia = limitRaccionsDia;
    }
    public void setMascota(MascotaReader mascota) {
        this.mascota = mascota;
    }


    public MascotaReader getMascota() {
        return mascota;
    }
    public double getSensorPlat() {
        return sensorPlat;
    }

    public double getGramsAcumulatAvui() {
        return gramsAcumulatAvui;
    }

    public double getRaccionsAcumuladesAvui() {
        return raccionsAcumuladesAvui;
    }

    public double getGramsRaccio() {
        return gramsRaccio;
    }

    public double getLimitDiari() {
        return limitDiari;
    }

    public double getValorDiposit() {
        return valorDiposit;
    }

    public double getLimitRaccionsDia() {
        return limitRaccionsDia;
    }
}
