package login.Client.MenjadoraReader;

public class MascotaReader {

    String nom;
    int edat;
    double pesMascota;
    boolean gat;
    boolean dreta;
    
    public MascotaReader () {

    }
    
    
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setEdat(int edat) {
        this.edat = edat;
    }

    public void setPesMascota(double pesMascota) {
        this.pesMascota = pesMascota;
    }

    public void setGat(boolean gat) {
        this.gat = gat;
    }

    public void setDreta(boolean dreta) {
        this.dreta = dreta;
    }

    public int getEdat() {
        return edat;
    }

    public double getPesMascota() {
        return pesMascota;
    }

    public boolean isGat() {
        return gat;
    }

    public boolean isDreta() {
        return dreta;
    }

    public String getNom() {
        return nom;
    }
}
