package login.Server;

import login.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Server {
    protected Socket socket;
    Map<String, String> loggedClients = new HashMap<>(); // Registre de tokens per usuari
    Map<String, Long> caducitatClients = new HashMap<>(); // Registre de data de login
    Map<String, String> usuaris = Map.of(     // Provisional, s'haurà de llegir d'una base de dades.
            "usuari1", "contrassenya1",
            "usuari2", "contrassenya2",
            "usuari3", "contrassenya3"
    );


    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    SMenjadora sMenjadora = null;

    public static void main(String[] args) throws Exception {

        Server servidor = new Server();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(Constants.PORT);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        System.out.println("El servidor s'està executant...");
        while (true) {
            System.out.println("Esperant connexions ...");
            servidor.setSocket(serverSocket.accept());
            System.out.println("Client connectat");
            servidor.executa();
        }
        //serverSocket.close();


    }


    public void executa() {
        String comanda;
        BufferedReader lector;
        try {
            lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            comanda = lector.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        switch (comanda) {
            case "login":
                System.out.println("Executant comanda de login");
                loginClient(lector);
                break;
            case "check":
                System.out.println("Executant comanda de check");
                try {
                    String userToken = lector.readLine();
                    comprovaCaducitat(userToken);
                    if (loggedClients.containsKey(userToken)) {
                        sendSocketOut(loggedClients.get(userToken));
                    } else {
                        sendSocketOut("Usuari no trobat");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "uf": // first update
                if (sMenjadora != null) {
                    sendSocketOut(sMenjadora.firstUpdate());
                } else {
                    sMenjadora = new SMenjadora();
                    sendSocketOut(sMenjadora.firstUpdate());
                }
                break;
            case "u": // update
                if (sMenjadora != null) {
                    //sendSocketOut("nu");
                    sendSocketOut(sMenjadora.update());
                } else {
                    sMenjadora = new SMenjadora();
                    sendSocketOut(sMenjadora.firstUpdate());
                }
                break;
            case "logout":
                System.out.println("Executant comanda de logout");
                try {
                    String userToken = lector.readLine();
                    comprovaCaducitat(userToken);
                    if (loggedClients.containsKey(userToken)) {
                        loggedClients.remove(userToken);
                        sendSocketOut("true");
                    } else {
                        sendSocketOut("false");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private void comprovaCaducitat(String token) {
        if (caducitatClients.containsKey(token)) {
            if (System.currentTimeMillis() - caducitatClients.get(token) >= Constants.CADUCITAT_CLIENTS && Constants.CADUCITAT_CLIENTS != -1) {
                loggedClients.remove(token);
                caducitatClients.remove(token);
            }
        }
    }
    private void loginClient(BufferedReader lector) {
        String usuari = "";
        String contrassenya = "";
        try {
            usuari = lector.readLine();
            contrassenya = lector.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (checkCredentials(usuari, contrassenya)) {
            String loginToken = generateNewToken();
            loggedClients.put(loginToken, usuari);
            caducitatClients.put(loginToken, System.currentTimeMillis());
            sendSocketOut("true", loginToken);
        } else {
            sendSocketOut("false");
        }
    }

    private boolean checkCredentials(String usuari, String contrassenya) {
        if (usuaris.containsKey(usuari)) {
            if (contrassenya.equals(usuaris.get(usuari))) {
                System.out.println("Credencials correctes per l'usuari: " + usuari);
                return true;
            }
        }
        System.out.println("Credencials INcorrectes per l'usuari: " + usuari);
        return false;
    }

    private void sendSocketOut(String... args) {
        try {
            BufferedWriter escriptor = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            escriptor.write(String.valueOf(args.length));
            escriptor.newLine();
            for (String arg : args) {
                escriptor.write(arg);
                escriptor.newLine();
            }
            escriptor.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }


    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
