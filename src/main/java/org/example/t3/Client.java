package org.example.t3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientHandler handler = new ClientHandler();
        handler.run();
    }
}
//
