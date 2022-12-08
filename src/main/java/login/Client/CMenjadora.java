package login.Client;

import login.Client.MenjadoraReader.MaquinaReader;
import login.Client.MenjadoraReader.MascotaReader;
import login.Client.MenjadoraReader.MenjadoraReader;
import login.Exceptions.ConnectionErrorException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CMenjadora {
    LoginSession loginSession;

    public CMenjadora(LoginSession loginSession) {
        this.loginSession = loginSession;
    }

    public void runMenjadora() throws ConnectionErrorException {
        ScheduledExecutorService schExService = Executors.newSingleThreadScheduledExecutor();
        Runnable worker = new MenjadoraThread(loginSession);

        schExService.scheduleWithFixedDelay(worker, 0, 1, TimeUnit.SECONDS);

    }

    class MenjadoraThread implements Runnable {

        LoginSession loginSession;
        MaquinaReader maquina = null;

        public MenjadoraThread(LoginSession loginSession) {
            this.loginSession = loginSession;
        }

        @Override
        public void run() {
            List<String> resultat;
            try {
                String command;
                if (maquina == null) {
                    command = "uf";
                } else {
                    command = "u";
                }
                resultat = Utilities.writeAndReadServer(command, loginSession.getToken());
            } catch (ConnectionErrorException e) {
                throw new RuntimeException(e);
            }


            if (resultat.get(0).equals("nu") || resultat.get(0).equals("null") ) {
                System.out.println("No update");
            } else {
                JSONObject maquinaJSON = new JSONObject((resultat.get(0)));
                System.out.println(maquinaJSON.toString(2));
                maquina = parseMaquina(maquina, maquinaJSON);
                maquina.getControlador().escriuValorsGui();
            }
        }

        private static MaquinaReader parseMaquina(MaquinaReader maquina, JSONObject maquinaJSON) {
            if (maquina == null) {
                maquina = new MaquinaReader();
            }
            maquina.setMenjadoraDreta(parseMenjadora(maquina.getMenjadoraDreta(), new JSONObject(maquinaJSON.get("menjadoraDreta").toString())));
            maquina.setMenjadoraEsquerra(parseMenjadora(maquina.getMenjadoraDreta(), new JSONObject(maquinaJSON.get("menjadoraEsquerra").toString())));

            return maquina;
        }

        private static MenjadoraReader parseMenjadora(MenjadoraReader menjadora, JSONObject menjadoraJSON) {
            if (menjadoraJSON.has("sensorPlat")) {
                menjadora.setSensorPlat(menjadoraJSON.getDouble("sensorPlat"));
            }
            if (menjadoraJSON.has("sensorPlat")) {
                menjadora.setGramsAcumulatAvui(menjadoraJSON.getDouble("gramsAcumulatAvui"));
            }
            if (menjadoraJSON.has("sensorPlat")) {
                menjadora.setRaccionsAcumuladesAvui(menjadoraJSON.getDouble("raccionsAcumuladesAvui"));
            }
            if (menjadoraJSON.has("sensorPlat")) {
                menjadora.setGramsRaccio(menjadoraJSON.getDouble("gramsRaccio"));
            }
            if (menjadoraJSON.has("sensorPlat")) {
                menjadora.setLimitDiari(menjadoraJSON.getDouble("limitDiari"));
            }
            if (menjadoraJSON.has("sensorPlat")) {
                menjadora.setValorDiposit(menjadoraJSON.getDouble("valorDiposit"));
            }
            if (menjadoraJSON.has("sensorPlat")) {
                menjadora.setLimitRaccionsDia(menjadoraJSON.getDouble("limitRaccionsDia"));
            }

            if (menjadoraJSON.has("mascota")) {
                menjadora.setMascota(parseMascota(menjadora.getMascota(), new JSONObject(menjadoraJSON.get("mascota").toString())));
            }
            return menjadora;
        }

        private static MascotaReader parseMascota(MascotaReader mascotaReader, JSONObject mascotaJSON) {
            if (mascotaJSON.has("nom")) {
                mascotaReader.setNom(mascotaJSON.get("nom").toString());
            }
            return mascotaReader;
        }
    }
}
