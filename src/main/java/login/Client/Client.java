package login.Client;

import login.Exceptions.ConnectionErrorException;

import java.io.*;
import java.util.List;

public class Client {

    public static void main(String[] args) {

        String comanda;
        LoginSession loginSession = null;
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.print("Comanda a realitzar (login/restore/check/logout/menjadora): ");
                comanda = br.readLine();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return;
            }
            switch (comanda) {
                case "login":
                    loginSession = new LoginSession(br);
                    break;
                case "restore":
                    try {
                        System.out.print("Introdueix el token: ");
                        String userToken = br.readLine();
                        loginSession = new LoginSession(userToken);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "check":
                    if (loginSession == null) {
                        System.out.println("Encara no has iniciat sessió!");
                    } else {
                        try {
                            List<String> resultat = Utilities.writeAndReadServer("check", loginSession.getToken());
                            System.out.println("L'usuari actual és: " + resultat.get(0));
                        } catch (ConnectionErrorException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case "menjadora":
                    if (loginSession == null) {
                        System.out.println("Encara no has iniciat sessió!");
                    } else {
                        CMenjadora cMenjadora = new CMenjadora(loginSession);
                        try {
                            cMenjadora.runMenjadora();
                        } catch (ConnectionErrorException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case "logout":
                    if (loginSession == null) {
                        System.out.println("Encara no has iniciat sessió!");
                    } else {
                        try {
                            List<String> resultat = Utilities.writeAndReadServer("logout", loginSession.getToken());
                            if (Boolean.parseBoolean(resultat.get(0))) {
                                System.out.println("Sessió tancada correctament.");
                                loginSession = null;
                            } else {
                                System.out.println("Error al tancar la sessió!");
                            }
                        } catch (ConnectionErrorException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
        }
    }

}
