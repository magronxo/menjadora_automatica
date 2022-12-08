package login.Client;

import login.Constants;
import login.Exceptions.ConnectionErrorException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static List<String> writeAndReadServer(String... args) throws ConnectionErrorException {
        try {
            Socket socket = new Socket(Constants.HOST, Constants.PORT);
            return writeAndReadServer(socket, args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String> writeAndReadServer(Socket socket, String... args) throws ConnectionErrorException {
        try (BufferedWriter escriptor = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()))) {
            for (String arg : args) {
                escriptor.write(arg);
                escriptor.newLine();
            }
            escriptor.flush();


            // Read message
            ArrayList<String> messages = new ArrayList<String>();
            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))) {

                int numofArgs = Integer.parseInt(lector.readLine());
                for (int i = 0; i < numofArgs; i++) {
                    String msg = lector.readLine();
                    messages.add(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ConnectionErrorException("Error de connexió al llegir.");
            }
            return messages.stream().toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionErrorException("Error de connexió al escriure.");
        }
    }/*
    public static List<String> readServerMessage(Socket socket) throws ConnectionErrorException {
        ArrayList<String> messages = new ArrayList<String>();
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = lector.readLine()) != null) {
                messages.add(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionErrorException("Error de connexió al llegir.");
        }
        return messages.stream().toList();
    }

    public static void writeServerMessage(Socket socket, String... args) throws ConnectionErrorException {
        try (BufferedWriter escriptor = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()))) {
            for (String arg : args) {
                escriptor.write(arg);
                System.out.println("He escrit " + arg);
                escriptor.newLine();
            }
            escriptor.flush();
            System.out.println(readServerMessage(socket).get(0));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionErrorException("Error de connexió al escriure.");
        }
    }*/
}
