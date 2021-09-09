package com.cesar31.audiogenerator.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author csart
 */
public class Listener extends Thread {

    private int portServer;
    private String ipClient;
    private int portClient;

    private RequestParserHandler parser;

    private Listener() {
        this.parser = new RequestParserHandler();
    }

    /**
     * Constructor de listener
     *
     * @param portServer puerto donde el servidor escucha peticiones
     * @param ipClient direccion ip del cliente
     * @param portClient puerto donde el cliente escucha peticiones
     */
    public Listener(int portServer, String ipClient, int portClient) {
        this();
        this.portServer = portServer;
        this.ipClient = ipClient;
        this.portClient = portClient;
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(portServer);
            System.out.println("Escuhando en: " + portServer);
            while (true) {
                try ( Socket socket = server.accept()) {
                    InputStreamReader reader = new InputStreamReader(socket.getInputStream());
                    BufferedReader br = new BufferedReader(reader);
                    String message = "";
                    String line = br.readLine();
                    while (line != null) {
                        message += line;
                        line = br.readLine();
                        if (line != null) {
                            message += "\n";
                        }
                    }
                    System.out.println("Recibiendo : \n" + message);
                    // Analizar mensaje aqui
                    String response = parser.parseSource(message);
                    sendResponse(response);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Enviar respuesta a cliente
     *
     * @param response mensaje que se envia al cliente
     */
    private void sendResponse(String response) {
        System.out.println("Enviado: \n" + response);
        try {
            try ( Socket socket = new Socket(ipClient, portClient); PrintWriter printWriter = new PrintWriter(socket.getOutputStream())) {
                printWriter.write(response);
                printWriter.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
