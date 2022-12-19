package org.example.t3;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    ObjectOutputStream socketOut;
    ObjectInputStream socketIn;

    public void run() throws IOException, ClassNotFoundException {
        Socket socket = new Socket("127.0.0.1", 4000);
        this.socketOut = new ObjectOutputStream(socket.getOutputStream());
        this.socketIn = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connected to $host:$port");

        System.out.println("use \"help\" command for commands list");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            String command = br.readLine();
            if ("exit".equals(command)) {
                socketWrite("exit");
                break;
            } else {
                executeCommand(br, command);
            }
        }
    }

    private void socketWrite(Object obj) throws IOException {
        socketOut.writeObject(obj);
        socketOut.flush();
    }

    private void executeCommand(BufferedReader br, String command) throws IOException, ClassNotFoundException {
        socketWrite(command);
        while(true) {
            String localcommand = (String) socketRead();
            switch (localcommand) {
                case "prompt": {
                    String message = (String) socketRead();
                    System.out.println(message);
                    String value = br.readLine();
                    socketWrite(value);
                    break;
                }
                case "print": {
                    String message = (String) socketRead();
                    System.out.println(message);
                    break;
                }
                case "done": {
                    return;
                }
            }
        }
    }

    private <T> T socketRead() throws IOException, ClassNotFoundException {
        T obj = (T) socketIn.readObject();
//        System.out.println(obj);
        return obj;
    }


}
