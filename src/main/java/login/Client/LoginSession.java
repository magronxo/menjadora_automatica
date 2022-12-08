package login.Client;

import login.Constants;
import login.Exceptions.ConnectionErrorException;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class LoginSession {
    private String token;

    public LoginSession(String usuari, String contrassenya) {
        try {
            login(usuari, contrassenya);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ConnectionErrorException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginSession(String token) {
        this.token = token;
    }

    public LoginSession(BufferedReader br) {
        String usuari, contrassenya;

        try {
            System.out.print("Introdueix l'usuari: ");
            usuari = br.readLine();
            System.out.print("\nIntrodueix la contrassenya: ");
            contrassenya = br.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        try {
            login(usuari, contrassenya);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ConnectionErrorException e) {
            throw new RuntimeException(e);
        }

    }

    private void login(String usuari, String contrassenya) throws IOException, ConnectionErrorException {
        Socket socket = new Socket(Constants.HOST, Constants.PORT);
        //Utilities.writeServerMessage(socket, "login", usuari, contrassenya);
        List<String> serverResponse = Utilities.writeAndReadServer(socket, "login", usuari, contrassenya);
        if (Boolean.parseBoolean(serverResponse.get(0))) {
            this.token = serverResponse.get(1);
            System.out.println("Login correcte! El token és: " + this.token);
        } else {
            System.out.println("Credencials incorrectes");
        }
    }

    public String getToken() {
        return this.token;
    }
}
