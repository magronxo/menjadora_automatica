package login.Server.MenjadoraReader;

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
        databaseManager.updateValueMaquina("nomMascota", EoD, nom);
    }

    public String getNom() {
        return nom;
    }
}
