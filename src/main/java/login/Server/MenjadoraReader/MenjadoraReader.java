package login.Server.MenjadoraReader;

import login.Server.DatabaseManager;

public class MenjadoraReader {
    double sensorPlat;
    double gramsAcumulatAvui;
    double raccionsAcumuladesAvui;
    double gramsRaccio;
    double limitDiari;
    double valorDiposit;
    double limitRaccionsDia;

    int EoD; // 0 = Esquerra, 1 = Dreta
    DatabaseManager databaseManager;



    MascotaReader mascota;
    public MenjadoraReader(int EoD) {
        this.mascota = new MascotaReader(EoD);
        this.EoD = EoD;
    }

    public MascotaReader getMascota() {
        return mascota;
    }
    public void setSensorPlat(double sensorPlat, DatabaseManager databaseManager) {
        this.sensorPlat = sensorPlat;
        databaseManager.updateValueMaquina("sensorPlat", EoD, String.valueOf(sensorPlat));
    }
    public void setGramsAcumulatAvui(double gramsAcumulatAvui, DatabaseManager databaseManager) {
        this.gramsAcumulatAvui = gramsAcumulatAvui;
        databaseManager.updateValueMaquina("gramsAcumulatAvui", EoD, String.valueOf(gramsAcumulatAvui));
    }

    public void setRaccionsAcumuladesAvui(double raccionsAcumuladesAvui, DatabaseManager databaseManager) {
        this.raccionsAcumuladesAvui = raccionsAcumuladesAvui;
        databaseManager.updateValueMaquina("raccionsAcumuladesAvui", EoD, String.valueOf(raccionsAcumuladesAvui));
    }

    public void setGramsRaccio(double gramsRaccio, DatabaseManager databaseManager) {
        this.gramsRaccio = gramsRaccio;
        databaseManager.updateValueMaquina("gramsRaccio", EoD, String.valueOf(gramsRaccio));
    }

    public void setLimitDiari(double limitDiari, DatabaseManager databaseManager) {
        this.limitDiari = limitDiari;
        databaseManager.updateValueMaquina("limitDiari", EoD, String.valueOf(limitDiari));
    }

    public void setValorDiposit(double valorDiposit, DatabaseManager databaseManager) {
        this.valorDiposit = valorDiposit;
        databaseManager.updateValueMaquina("valorDiposit", EoD, String.valueOf(valorDiposit));
    }

    public void setLimitRaccionsDia(double limitRaccionsDia, DatabaseManager databaseManager) {
        this.limitRaccionsDia = limitRaccionsDia;
        databaseManager.updateValueMaquina("limitRaccionsDia", EoD, String.valueOf(limitRaccionsDia));
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
