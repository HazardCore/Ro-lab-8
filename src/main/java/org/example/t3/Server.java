package org.example.t3;

import org.example.Album;
import org.example.Author;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import java.util.HashMap;
import java.util.Optional;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerHandler handler = new ServerHandler();
        handler.run();
    }
}