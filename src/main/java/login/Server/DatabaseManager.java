package login.Server;

import login.Server.MenjadoraReader.MaquinaReader;
import login.Server.MenjadoraReader.MenjadoraReader;

import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
     
    public static final String HOST = "jdbc:postgresql://localhost:5432/";
    public static final String DB_NAME = "menjadora";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "postgres";
    
    public static Connection conn;
    
    
    private MaquinaReader maquinaReader;
    private MaquinaReader oldMaquinaReader;
    private String maquinaID;
    public DatabaseManager(String maquinaID) throws SQLException {
        this.maquinaReader = new MaquinaReader();
        this.oldMaquinaReader = maquinaReader;
        this.maquinaID = maquinaID;
        getConn();
        insertAll();
        updateAllSQL();
        readAllSQL();
    }

    public void getConn(){
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                    HOST+DB_NAME,
                    USERNAME,
                    PASSWORD);
            //Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres/postgres/root");
             
            System.out.println("DB connected");
             
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertAll() throws SQLException {
        
        MenjadoraReader menjadoraDreta = maquinaReader.getMenjadoraDreta();
        MenjadoraReader menjadoraEsquerra = maquinaReader.getMenjadoraEsquerra();

        //INSERIM LA MÀQUINA
        int idMaquina=Integer.valueOf(maquinaID);
        String usuari = "usuari1";//Recollir el mail d'usuari
        String contrassenya = "password1";//recollir la contrassenya
        String queryMaquina = String.format("INSERT INTO maquina VALUES(?,?,?)");
        try(PreparedStatement pstm = conn.prepareStatement(queryMaquina)) {
            pstm.setInt(1, idMaquina);
            pstm.setString(2, usuari);
            pstm.setString(3, contrassenya);
            
            pstm.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Error inserint maquina");
        }
        
        //INSERIM LES MASCOTES
        String nomMascotaDreta = menjadoraDreta.getMascota().getNom();
        int edatMascotaDreta = menjadoraDreta.getMascota().getEdat();
        double pesMascotaDreta = menjadoraDreta.getMascota().getPes();
        boolean isGatDreta = menjadoraDreta.getMascota().isGat();
        boolean dreta =  true;
        String queryMascotaDreta = String.format("INSERT INTO mascota VALUES(?,?,?,?,?)");
        try(PreparedStatement pstm = conn.prepareStatement(queryMascotaDreta)) {
            pstm.setString(1, nomMascotaDreta);
            pstm.setInt(2, edatMascotaDreta);
            pstm.setDouble(3, pesMascotaDreta);
            pstm.setBoolean(4, isGatDreta);
            pstm.setBoolean(5, dreta);
            
            pstm.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Error inserint mascota dreta");
        }
        
        String nomMascotaEsquerra = menjadoraEsquerra.getMascota().getNom();
        int edatMascotaEsquerra = menjadoraEsquerra.getMascota().getEdat();
        double pesMascotaEsquerra = menjadoraEsquerra.getMascota().getPes();
        boolean isGatEsquerra = menjadoraEsquerra.getMascota().isGat();
        dreta =  false;
        String queryMascotaEsquerra = String.format("INSERT INTO mascota VALUES(?,?,?,?,?)");
        try(PreparedStatement pstm = conn.prepareStatement(queryMascotaEsquerra)) {
            pstm.setString(1, nomMascotaEsquerra);
            pstm.setInt(2, edatMascotaEsquerra);
            pstm.setDouble(3, pesMascotaEsquerra);
            pstm.setBoolean(4, isGatEsquerra);
            pstm.setBoolean(5, dreta);
            
            pstm.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Error inserint mascota esquerra");
        }
        
        //INSERIM LES MENJADORES
        double sensorPlatDreta = menjadoraDreta.getSensorPlat();
        double sensorDipositDreta = menjadoraDreta.getValorDiposit();
        double alertaDipositDreta = menjadoraDreta.getValorAlertaDiposit();
        double gramsAcumulatDreta = menjadoraDreta.getGramsAcumulatAvui();
        int raccionsAcumuladesDreta = (int)menjadoraDreta.getRaccionsAcumuladesAvui();
        int limitDiariDreta = (int)menjadoraDreta.getLimitDiari();
        int limitRaccionsDiaDreta = (int)menjadoraDreta.getLimitRaccionsDia();
        dreta = true;
        
        String queryMenjadoraDreta = String.format("INSERT INTO menjadora VALUES(?,?,?,?,?,?,?,?)");
        try(PreparedStatement pstm = conn.prepareStatement(queryMenjadoraDreta)) {
            pstm.setDouble(1, sensorPlatDreta);
            pstm.setDouble(2, sensorDipositDreta);
            pstm.setDouble(3, alertaDipositDreta);
            pstm.setDouble(4, gramsAcumulatDreta);
            pstm.setInt(5, raccionsAcumuladesDreta);
            pstm.setBoolean(6, dreta);
            pstm.setInt(7, limitDiariDreta);
            pstm.setInt(8, limitRaccionsDiaDreta);

            pstm.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Error inserint menjadora dreta");
        }
        
        double sensorPlatEsquerra = menjadoraEsquerra.getSensorPlat();
        double sensorDipositEsquerra = menjadoraEsquerra.getValorDiposit();
        double alertaDipositEsquerra = menjadoraEsquerra.getValorAlertaDiposit();
        double gramsAcumulatEsquerra = menjadoraEsquerra.getGramsAcumulatAvui();
        int raccionsAcumuladesEsquerra = (int)menjadoraEsquerra.getRaccionsAcumuladesAvui();
        int limitDiariEsquerra = (int)menjadoraEsquerra.getLimitDiari();
        int limitRaccionsDiaEsquerra = (int)menjadoraEsquerra.getLimitRaccionsDia();
        dreta = false;
        
        String queryMenjadoraEsquerra = String.format("INSERT INTO menjadora VALUES(?,?,?,?,?,?,?,?)");
        try(PreparedStatement pstm = conn.prepareStatement(queryMenjadoraEsquerra)) {
            pstm.setDouble(1, sensorPlatEsquerra);
            pstm.setDouble(2, sensorDipositEsquerra);
            pstm.setDouble(3, alertaDipositEsquerra);
            pstm.setDouble(4, gramsAcumulatEsquerra);
            pstm.setInt(5, raccionsAcumuladesEsquerra);
            pstm.setBoolean(6, dreta);
            pstm.setInt(7, limitDiariEsquerra);
            pstm.setInt(8, limitRaccionsDiaEsquerra);

            pstm.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Error inserint menjadora esquerra");
        }
        
      }

    public static String readValueMaquina(String nom, int EoD) throws SQLException {
        
        // nom: nom de la dada, per exmple: "sensorPlat"
        // EoD: E (esquerra) = 0, D (dreta) = 1
        boolean dreta=false;
        if(EoD==0){
            dreta=false;
        }else if (EoD==1) dreta=true;
        
        Statement stmt = conn.createStatement();
        ResultSet rs;
        
        switch (nom) {

            //CONSULTEM LA MAQUINA
            case "idMaquina":
                rs = stmt.executeQuery("SELECT id_maquina FROM maquina");
                rs.next();
                return rs.getString(1);      
            case "usuari":
                rs = stmt.executeQuery("SELECT usuari FROM maquina");
                rs.next();
                return rs.getString(1);
            case "contrassenya":
                rs = stmt.executeQuery("SELECT contrassenya FROM maquina");
                rs.next();
                return rs.getString(1);
                
                
                
            //CONSULTEM LES MASCOTES   
            case "nomMascota":
                rs = stmt.executeQuery("SELECT nom FROM mascota WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);
            case "pesMascota":
                rs = stmt.executeQuery("SELECT pes FROM mascota WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);
            case "edatMascota":
                rs = stmt.executeQuery("SELECT edat FROM mascota WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);
            case "isGat":
                rs = stmt.executeQuery("SELECT is_gat FROM mascota WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);
            
            //CONSULTEM LES MENJADORES    
            case "sensorPlat":
                rs = stmt.executeQuery("SELECT sensor_plat FROM menjadora WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);
            case "sensorNivellDiposit":
                rs = stmt.executeQuery("SELECT sensor_nivell_diposit FROM menjadora WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);
            case "valorAlertaDiposit":
                rs = stmt.executeQuery("SELECT valor_alerta_diposit FROM menjadora WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);
            case "gramsAcumulatAvui":
                rs = stmt.executeQuery("SELECT grams_acumulat_avui FROM menjadora WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);
            case "raccionsAcumuladesAvui":
                rs = stmt.executeQuery("SELECT raccions_acumulades_avui FROM menjadora WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);
            case "limitDiari":
                rs = stmt.executeQuery("SELECT limit_diari FROM menjadora WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);
            case "limitRaccionsDia":
                rs = stmt.executeQuery("SELECT limit_raccions_dia FROM menjadora WHERE is_dreta = "+dreta);
                rs.next();
                return rs.getString(1);


            default:
                System.out.println("ERROR: Valor \"" + nom + "\" no trobat.");
                return null;
        }
        
    }
    public static void updateValueMaquina(String nom, int EoD, String valor) throws SQLException {
        // nom: nom de la dada, per exmple: "sensorPlat"
        // valor: valor de la dada, per exemple: "2.0" o "Bonnie"
        // EoD: E (esquerra) = 0, D (dreta) = 1
        boolean dreta=false;
        switch (EoD) {
            case 0 -> dreta=false;
            case 1 -> dreta=true;
            default -> {
                return;
            }
        }
        
        Statement stmt = conn.createStatement();
        
        switch (nom) {
            //ACTUALITZEM LA MAQUINA
            case "idMaquina":
                stmt.executeUpdate("UPDATE maquina SET id_maquina = "+valor+" WHERE 1=1 ");
                stmt.close();
                break;

            case "usuari":
                stmt.executeUpdate("UPDATE maquina SET usuari = "+valor+" WHERE 1=1 ");
                stmt.close();
                break;

            case "contrassenya":
                stmt.executeUpdate("UPDATE maquina SET contrassenya = "+valor+" WHERE 1=1 ");
                stmt.close();
                break;
                               
            //ACTUALITZEM LES MASCOTES   
            case "nomMascota":
                stmt.executeUpdate("UPDATE mascota SET nom = "+valor+" WHERE is_dreta = "+dreta);
                break;
            case "pesMascota":
                stmt.executeUpdate("UPDATE mascota SET pes = "+valor+" WHERE is_dreta = "+dreta);
                break;
            case "edatMascota":
                stmt.executeUpdate("UPDATE mascota SET edat = "+valor+" WHERE is_dreta = "+dreta);
                break;
            case "isGat":
                stmt.executeUpdate("UPDATE mascota SET is_gat = "+valor+" WHERE is_dreta = "+dreta);
                break;
            
            //ACTUALITZEM LES MENJADORES    
            case "sensorPlat":
                stmt.executeUpdate("UPDATE menjadora SET sensor_plat = "+valor+" WHERE is_dreta = "+dreta);
                break;
            case "sensorNivellDiposit":
                stmt.executeUpdate("UPDATE menjadora SET sensor_nivell_diposit = "+valor+" WHERE is_dreta = "+dreta);
                break;
            case "valorAlertaDiposit":
                stmt.executeUpdate("UPDATE menjadora SET valor_alerta_diposit = "+valor+" WHERE is_dreta = "+dreta);
                break;
            case "gramsAcumulatAvui":
                stmt.executeUpdate("UPDATE menjadora SET grams_acumulat_avui = "+valor+" WHERE is_dreta = "+dreta);
                break;
            case "raccionsAcumuladesAvui":
                stmt.executeUpdate("UPDATE menjadora SET raccions_acumulades_avui = "+valor+" WHERE is_dreta = "+dreta);
                break;
            case "limitDiari":
                stmt.executeUpdate("UPDATE menjadora SET limit_diari = "+valor+" WHERE is_dreta = "+dreta);
                break;
            case "limitRaccionsDia":
                stmt.executeUpdate("UPDATE menjadora SET limit_raccions_dia = "+valor+" WHERE is_dreta = "+dreta);
                break;    
                
                
            default:
                System.out.println("ERROR: dada \"" + nom + "\" no trobada.");
                break;
        }
    }
    
    
    private void readAllSQL() throws SQLException {
        // Aquesta funció cridarà a llegir els valors un per un a la funció "readValueMaquina". No se li ha de fer res més
        MenjadoraReader menjadora = maquinaReader.getMenjadoraEsquerra();
        for (int i = 0; i <= 1; i++) {
            menjadora.setSensorPlat(Double.parseDouble(readValueMaquina("sensorPlat", i)));
            menjadora.setGramsAcumulatAvui(Double.parseDouble(readValueMaquina("gramsAcumulatAvui", i)));
            menjadora.setRaccionsAcumuladesAvui(Double.parseDouble(readValueMaquina("raccionsAcumuladesAvui", i)));
            menjadora.setLimitDiari(Double.parseDouble(readValueMaquina("limitDiari", i)));
            menjadora.setValorDiposit(Double.parseDouble(readValueMaquina("sensorNivellDiposit", i)));
            menjadora.setLimitRaccionsDia(Double.parseDouble(readValueMaquina("limitRaccionsDia", i)));
            menjadora.setValorAlertaDiposit(Double.parseDouble(readValueMaquina("valorAlertaDiposit", i)));//Afegit

            menjadora.getMascota().setNom(readValueMaquina("nomMascota", i));
            menjadora.getMascota().setEdat(readValueMaquina("edatMascota", i));//Afegit
            menjadora.getMascota().setPes(readValueMaquina("pesMascota", i));//Afegit
            menjadora.getMascota().setGat(readValueMaquina("isGat", i));//Afegit
            
            menjadora = maquinaReader.getMenjadoraDreta();
        }
    }
    public Map<String, Integer> compareChanges() {
        return compareChanges(false);
    }
    public Map<String, Integer> compareChanges(Boolean getAllFields) {
        MaquinaReader oldMaquinaReader;
        if (getAllFields) {
            oldMaquinaReader = new MaquinaReader();
        } else {
            oldMaquinaReader = this.oldMaquinaReader;
        }

        MenjadoraReader currentMenjadora = maquinaReader.getMenjadoraEsquerra();
        MenjadoraReader currentOldMenjadora = oldMaquinaReader.getMenjadoraEsquerra();

        Map<String, Integer> valorsALlegir = new HashMap<String, Integer>();

        for (int i = 0; i <= 1; i++) {
            if (currentMenjadora.getSensorPlat() != currentOldMenjadora.getSensorPlat()) {
                defineChange("sensorPlat", valorsALlegir, i);
            }
            if (currentMenjadora.getGramsAcumulatAvui() != currentOldMenjadora.getGramsAcumulatAvui()) {
                defineChange("gramsAcumulatAvui", valorsALlegir, i);
            }
            if (currentMenjadora.getRaccionsAcumuladesAvui() != currentOldMenjadora.getRaccionsAcumuladesAvui()) {
                defineChange("raccionsAcumuladesAvui", valorsALlegir, i);
            }
            if (currentMenjadora.getLimitDiari() != currentOldMenjadora.getLimitDiari()) {
                defineChange("limitDiari", valorsALlegir, i);
            }
            if (currentMenjadora.getValorDiposit() != currentOldMenjadora.getValorDiposit()) {
                defineChange("valorDiposit", valorsALlegir, i);
            }
            //Afegit
            if (currentMenjadora.getValorAlertaDiposit() != currentOldMenjadora.getValorAlertaDiposit()) {
                defineChange("valorAlertaDiposit", valorsALlegir, i);
            }
            if (currentMenjadora.getLimitRaccionsDia() != currentOldMenjadora.getLimitRaccionsDia()) {
                defineChange("limitRaccionsDia", valorsALlegir, i);
            }
            if (!currentMenjadora.getMascota().getNom().equals(currentOldMenjadora.getMascota().getNom())) {
                defineChange("nomMascota", valorsALlegir, i);
            }
            //Afegits
            if (currentMenjadora.getMascota().getEdat() != currentOldMenjadora.getMascota().getEdat()) {
                defineChange("edatMascota", valorsALlegir, i);
            }
            if (currentMenjadora.getMascota().getPes() != currentOldMenjadora.getMascota().getPes()) {
                defineChange("pesMascota", valorsALlegir, i);
            }
            if (currentMenjadora.getMascota().isGat() != (currentOldMenjadora.getMascota().isGat())) {
                defineChange("isGat", valorsALlegir, i);
            }


            currentMenjadora = maquinaReader.getMenjadoraDreta();
            currentOldMenjadora = oldMaquinaReader.getMenjadoraDreta();
        }
        return valorsALlegir;
    }
    private void defineChange(String dada, Map<String, Integer> valorsALlegir, int EoD) {
        if (valorsALlegir.containsKey(dada)) {
            valorsALlegir.replace(dada, 2);
        } else {
            valorsALlegir.put(dada, EoD);
        }
    }
    
    private void updateAllSQL() throws SQLException {
        MenjadoraReader menjadoraDreta = maquinaReader.getMenjadoraDreta();
        MenjadoraReader menjadoraEsquerra = maquinaReader.getMenjadoraEsquerra();

        //ACTUALITZEM LA MÀQUINA
        //updateValueMaquina("idMaquina",0,"22");
        //updateValueMaquina("usuari",0,"'usuari22'");
        //updateValueMaquina("contrassenya",0,"'password24'");

        //ACTUALITZEM MASCOTA DRETA
        //updateValueMaquina("nomMascota",0,"'duna'");//Amb cometes simples!!
        updateValueMaquina("nomMascota",0,menjadoraDreta.getMascota().getNom());
        updateValueMaquina("pesMascota",0,String.valueOf(menjadoraDreta.getMascota().getPes()));
        updateValueMaquina("edatMascota",0,String.valueOf(menjadoraDreta.getMascota().getEdat()));
        updateValueMaquina("isGat",0,String.valueOf(menjadoraDreta.getMascota().isGat()));

        //ACTUALITZEM MENJADORA DRETA
        updateValueMaquina("sensorPlat",0,String.valueOf(menjadoraDreta.getSensorPlat()));
        updateValueMaquina("sensorNivellDiposit",0,String.valueOf(menjadoraDreta.getValorDiposit()));
        updateValueMaquina("valorAlertaDiposit",0,String.valueOf(menjadoraDreta.getValorAlertaDiposit()));
        updateValueMaquina("gramsAcumulatAvui",0,String.valueOf(menjadoraDreta.getGramsAcumulatAvui()));
        updateValueMaquina("raccionsAcumuladesAvui",0,String.valueOf(menjadoraDreta.getRaccionsAcumuladesAvui()));
        updateValueMaquina("limitDiari",0,String.valueOf(menjadoraDreta.getLimitDiari()));
        updateValueMaquina("limitRaccionsDia",0,String.valueOf(menjadoraDreta.getLimitRaccionsDia()));

        //ACTUALITZEM MASCOTA ESQUERRA
        updateValueMaquina("nomMascota",1,menjadoraEsquerra.getMascota().getNom());//Amb cometes simples!!
        updateValueMaquina("pesMascota",1,String.valueOf(menjadoraEsquerra.getMascota().getPes()));
        updateValueMaquina("edatMascota",1,String.valueOf(menjadoraEsquerra.getMascota().getEdat()));
        updateValueMaquina("isGat",1,String.valueOf(menjadoraEsquerra.getMascota().isGat()));

        //ACTUALITZEM MENJADORA ESQUERRA
        updateValueMaquina("sensorPlat",1,String.valueOf(menjadoraEsquerra.getSensorPlat()));
        updateValueMaquina("sensorNivellDiposit",1,String.valueOf(menjadoraEsquerra.getValorDiposit()));
        updateValueMaquina("valorAlertaDiposit",1,String.valueOf(menjadoraEsquerra.getValorAlertaDiposit()));
        updateValueMaquina("gramsAcumulatAvui",1,String.valueOf(menjadoraEsquerra.getGramsAcumulatAvui()));
        updateValueMaquina("raccionsAcumuladesAvui",1,String.valueOf(menjadoraEsquerra.getRaccionsAcumuladesAvui()));
        updateValueMaquina("limitDiari",1,String.valueOf(menjadoraEsquerra.getLimitDiari()));
        updateValueMaquina("limitRaccionsDia",1,String.valueOf(menjadoraEsquerra.getLimitRaccionsDia()));

    }
}
