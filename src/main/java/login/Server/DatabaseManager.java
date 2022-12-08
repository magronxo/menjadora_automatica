package login.Server;

import login.Server.MenjadoraReader.MaquinaReader;
import login.Server.MenjadoraReader.MenjadoraReader;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private MaquinaReader maquinaReader;
    private MaquinaReader oldMaquinaReader;
    private String maquinaID;
    public DatabaseManager(String maquinaID) {
        this.maquinaReader = new MaquinaReader();
        this.oldMaquinaReader = maquinaReader;
        this.maquinaID = maquinaID;
        readAllSQL();
    }


    public String readValueMaquina(String nom, int EoD) {
        // nom: nom de la dada, per exmple: "sensorPlat"
        // EoD: E (esquerra) = 0, D (dreta) = 1
        switch (nom) {

            // TODO canviar els null per la dada llegida

            case "sensorPlat":
                // TODO Llegir la dada de la SQL.
                return null;
            case "gramsAcumulatAvui":
                // TODO Llegir la dada de la SQL.
                return null;
            case "raccionsAcumuladesAvui":
                // TODO Llegir la dada de la SQL.
                return null;
            case "gramsRaccio":
                // TODO Llegir la dada de la SQL.
                return null;
            case "limitDiari":
                // TODO Llegir la dada de la SQL.
                return null;
            case "valorDiposit":
                // TODO Llegir la dada de la SQL.
                return null;
            case "limitRaccionsDia":
                // TODO Llegir la dada de la SQL.
                return null;
            case "nomMascota":
                // TODO Llegir la dada de la SQL.
                return null;
            default:
                System.out.println("ERROR: Valor \"" + nom + "\" no trobat.");
                return null;
        }
    }
    public void updateValueMaquina(String nom, int EoD, String valor) {
        // nom: nom de la dada, per exmple: "sensorPlat"
        // valor: valor de la dada, per exemple: "2.0" o "Bonnie"
        // EoD: E (esquerra) = 0, D (dreta) = 1
        if (EoD != 0 && EoD != 1) {
            return;
        }
        switch (nom) {
            case "sensorPlat":
                // TODO Actualitzar la dada a la SQL.
                break;
            case "gramsAcumulatAvui":
                // TODO Actualitzar la dada a la SQL.
                break;
            case "raccionsAcumuladesAvui":
                // TODO Actualitzar la dada a la SQL.
                break;
            case "gramsRaccio":
                // TODO Actualitzar la dada a la SQL.
                break;
            case "limitDiari":
                // TODO Actualitzar la dada a la SQL.
                break;
            case "valorDiposit":
                // TODO Actualitzar la dada a la SQL.
                break;
            case "limitRaccionsDia":
                // TODO Actualitzar la dada a la SQL.
                break;
            case "nomMascota":
                // TODO Actualitzar la dada a la SQL.
                break;
            default:
                System.out.println("ERROR: dada \"" + nom + "\" no trobada.");
                break;
        }
    }
    private void readAllSQL() {
        // Aquesta funció cridarà a llegir els valors un per un a la funció "readValueMaquina". No se li ha de fer res més
        MenjadoraReader menjadora = maquinaReader.getMenjadoraEsquerra();
        for (int i = 0; i <= 1; i++) {
            menjadora.setSensorPlat(Double.parseDouble(readValueMaquina("sensorPlat", i)));
            menjadora.setGramsAcumulatAvui(Double.parseDouble(readValueMaquina("gramsAcumulatAvui", i)));
            menjadora.setRaccionsAcumuladesAvui(Double.parseDouble(readValueMaquina("raccionsAcumuladesAvui", i)));
            menjadora.setGramsRaccio(Double.parseDouble(readValueMaquina("gramsRaccio", i)));
            menjadora.setLimitDiari(Double.parseDouble(readValueMaquina("limitDiari", i)));
            menjadora.setValorDiposit(Double.parseDouble(readValueMaquina("valorDiposit", i)));
            menjadora.setLimitRaccionsDia(Double.parseDouble(readValueMaquina("limitRaccionsDia", i)));

            menjadora.getMascota().setNom(readValueMaquina("nomMascota", 0));
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
            if (currentMenjadora.getGramsRaccio() != currentOldMenjadora.getGramsRaccio()) {
                defineChange("gramsRaccio", valorsALlegir, i);
            }
            if (currentMenjadora.getLimitDiari() != currentOldMenjadora.getLimitDiari()) {
                defineChange("limitDiari", valorsALlegir, i);
            }
            if (currentMenjadora.getValorDiposit() != currentOldMenjadora.getValorDiposit()) {
                defineChange("valorDiposit", valorsALlegir, i);
            }
            if (currentMenjadora.getLimitRaccionsDia() != currentOldMenjadora.getLimitRaccionsDia()) {
                defineChange("limitRaccionsDia", valorsALlegir, i);
            }
            if (!currentMenjadora.getMascota().getNom().equals(currentOldMenjadora.getMascota().getNom())) {
                defineChange("nomMascota", valorsALlegir, i);
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
}
