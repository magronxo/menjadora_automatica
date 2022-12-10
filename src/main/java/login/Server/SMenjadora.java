package login.Server;

import com.influxdb.client.JSON;
import java.sql.SQLException;
import login.Server.MenjadoraReader.MenjadoraReader;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMenjadora {
    DatabaseManager databaseManager;
    public SMenjadora() {
        try {
            databaseManager = new DatabaseManager("idMaquina1"); /* ID provisional, cada màquina
            /* tindrà un ID únic.           */
        } catch (SQLException ex) {
            Logger.getLogger(SMenjadora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String firstUpdate(){
        System.out.println("FIRSTUpdate");
        return generateJson(databaseManager.compareChanges(true)).toString();
    }
    public String update(){
        System.out.println("Update");
        JSONObject resultat = generateJson(databaseManager.compareChanges());
        if (resultat.isEmpty()) {
            return "nu";
        } else {
            return resultat.toString();
        }
    }

    private JSONObject generateJson(Map<String, Integer> valorsALlegir) {

        JSONObject maquinaJSON = new JSONObject();
        JSONObject menjadoraEsquerra = new JSONObject();
        JSONObject menjadoraDreta = new JSONObject();

        for (String i : valorsALlegir.keySet()) {
            int valorEoD = valorsALlegir.get(i);
            try{                           
                switch (valorEoD) {
                    case 0:

                    menjadoraEsquerra.put(i, databaseManager.readValueMaquina(i, 0));   

                        break;

                    case 1:
                        menjadoraDreta.put(i, databaseManager.readValueMaquina(i, 1));
                        break;
                    case 2:
                        menjadoraEsquerra.put(i, databaseManager.readValueMaquina(i, 0));
                        menjadoraDreta.put(i, databaseManager.readValueMaquina(i, 1));
                        break;
                }
            } catch (SQLException ex) {
                        Logger.getLogger(SMenjadora.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }

        // Inici codi provisional per la mascota
        if (menjadoraDreta.has("nomMascota")) {
            JSONObject mascota = new JSONObject();
            mascota.put("nom", "Duna");
            menjadoraDreta.put("mascota", mascota);
            menjadoraDreta.remove("nomMascota");
        }
        if (menjadoraEsquerra.has("nomMascota")) {
            JSONObject mascota = new JSONObject();
            mascota.put("nom", "Duna");
            menjadoraEsquerra.put("mascota", mascota);
            menjadoraEsquerra.remove("nomMascota");
        }
        // Fi codi provisional per la mascota

        if (!menjadoraEsquerra.isEmpty()) {
            maquinaJSON.put("menjadoraEsquerra", menjadoraEsquerra);
        }if (!menjadoraDreta.isEmpty()) {
            maquinaJSON.put("menjadoraDreta", menjadoraDreta);
        }

        return maquinaJSON;
    }
}
