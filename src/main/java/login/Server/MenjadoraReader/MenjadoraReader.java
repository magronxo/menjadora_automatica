package login.Server.MenjadoraReader;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            databaseManager.updateValueMaquina("sensorPlat", EoD, String.valueOf(sensorPlat));
        } catch (SQLException ex) {
            Logger.getLogger(MenjadoraReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setGramsAcumulatAvui(double gramsAcumulatAvui, DatabaseManager databaseManager) {
        this.gramsAcumulatAvui = gramsAcumulatAvui;
        try {
            databaseManager.updateValueMaquina("gramsAcumulatAvui", EoD, String.valueOf(gramsAcumulatAvui));
        } catch (SQLException ex) {
            Logger.getLogger(MenjadoraReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setRaccionsAcumuladesAvui(double raccionsAcumuladesAvui, DatabaseManager databaseManager) {
        this.raccionsAcumuladesAvui = raccionsAcumuladesAvui;
        try {
            databaseManager.updateValueMaquina("raccionsAcumuladesAvui", EoD, String.valueOf(raccionsAcumuladesAvui));
        } catch (SQLException ex) {
            Logger.getLogger(MenjadoraReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setGramsRaccio(double gramsRaccio, DatabaseManager databaseManager) {
        this.gramsRaccio = gramsRaccio;
        try {
            databaseManager.updateValueMaquina("gramsRaccio", EoD, String.valueOf(gramsRaccio));
        } catch (SQLException ex) {
            Logger.getLogger(MenjadoraReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setLimitDiari(double limitDiari, DatabaseManager databaseManager) {
        this.limitDiari = limitDiari;
        try {
            databaseManager.updateValueMaquina("limitDiari", EoD, String.valueOf(limitDiari));
        } catch (SQLException ex) {
            Logger.getLogger(MenjadoraReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setValorDiposit(double valorDiposit, DatabaseManager databaseManager) {
        this.valorDiposit = valorDiposit;
        try {
            databaseManager.updateValueMaquina("valorDiposit", EoD, String.valueOf(valorDiposit));
        } catch (SQLException ex) {
            Logger.getLogger(MenjadoraReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setLimitRaccionsDia(double limitRaccionsDia, DatabaseManager databaseManager) {
        this.limitRaccionsDia = limitRaccionsDia;
        try {
            databaseManager.updateValueMaquina("limitRaccionsDia", EoD, String.valueOf(limitRaccionsDia));
        } catch (SQLException ex) {
            Logger.getLogger(MenjadoraReader.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public double getValorAlertaDiposit() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setValorAlertaDiposit(double parseDouble) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
