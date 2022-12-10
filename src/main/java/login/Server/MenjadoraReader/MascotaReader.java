package login.Server.MenjadoraReader;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.Server.DatabaseManager;

public class MascotaReader {

    String nom;
    int EoD;
    public MascotaReader (int EoD) {
        this.EoD = EoD;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setNom(String nom, DatabaseManager databaseManager) {
        this.nom = nom;
        try {
            databaseManager.updateValueMaquina("nomMascota", EoD, nom);
        } catch (SQLException ex) {
            Logger.getLogger(MascotaReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNom() {
        return nom;
    }

    public int getEdat() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public double getPes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean isGat() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setEdat(String readValueMaquina) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setGat(String readValueMaquina) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setPes(String readValueMaquina) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
