package org.example.t3;

import org.example.Album;
import org.example.Author;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerHandler {
    ObjectOutputStream socketOut;
    ObjectInputStream socketIn;

    /**
     * <name, Folder>
     */
    private HashMap<String, Author> authors = new HashMap<String, Author>();

    public void run() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(4000);
        System.out.println("Server started on $port");

        Socket socket = serverSocket.accept();
        this.socketIn = new ObjectInputStream(socket.getInputStream());
        this.socketOut = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Client connected");

        while (true) {
            if (!executeCommand(socketRead())) {
                System.out.println("We break");
                break;
            }
            socketWrite("done");
        }
    }

    private Boolean executeCommand(String command) throws IOException, ClassNotFoundException {
        switch (command) {
            case "addAuthor":
                addAuthor();
                break;
            case "removeAuthor":
                removeAuthor();
                break;
            case "listAuthor": listAuthor(); break;
            case "addAlbum": addAlbum(); break;
            case "removeAlbum": removeAlbum(); break;
            case "updateAlbum": updateAlbum(); break;
            case "listAlbum": listAlbum(); break;
            case "exit":
                return false;
            default:
                socketPrint("wrong command, try again");
        }
        return true;
    }

    private void socketWrite(Object obj) throws IOException {
        socketOut.writeObject(obj);
        socketOut.flush();
        System.out.println(obj);
    }

    private <T> T socketRead() throws IOException, ClassNotFoundException {
        T obj = (T) socketIn.readObject();
        System.out.println(obj);
        return obj;
    }

    private <T> T socketPrompt(String topic) throws IOException, ClassNotFoundException {
        socketWrite("prompt");
        socketWrite(topic);
        return socketRead();
    }

    private void socketPrint(String message) throws IOException {
        socketWrite("print");
        socketWrite(message);
    }

    private void addAuthor() throws IOException, ClassNotFoundException {
        String name = socketPrompt("name");
        if (authors.containsKey(name)) {
            socketPrint("Author already exists");
            return;
        }
        Author author = new Author(name);
        authors.put(name, author);
        socketPrint("success");
    }

    private void removeAuthor() throws IOException, ClassNotFoundException {
        String name = socketPrompt("name");
        if (authors.remove(name) == null) {
            socketPrint("no author");
            return;
        }
        socketPrint("success");
    }


    private void listAuthor() throws IOException, ClassNotFoundException {
        authors.forEach((k,v) -> {
            System.out.println("key: " + k + " value:" + v);
            try {
                socketPrint(k);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Author promptAuthor() throws IOException, ClassNotFoundException {
        String authorName = socketPrompt("Author name");
        return authors.get(authorName);
    }

    private void addAlbum() throws IOException, ClassNotFoundException {
        Author author = promptAuthor();
        if (author == null) {
            socketPrint("No such author");
            return;
        }
        String name = socketPrompt("name");
        if (author.albums.containsKey(name)) {
            socketPrint("Already exists");
            return;
        }
        String content = socketPrompt("content");
        Album album = new Album(name, content);
        author.albums.put(name, album);
        socketPrint("success");
    }

    private void removeAlbum() throws IOException, ClassNotFoundException {
        Author author = promptAuthor();
        if (author == null) {
            socketPrint("No such author");
            return;
        }
        String name = socketPrompt("name");
        if (author.albums.remove(name) == null) {
            socketPrint("No such album");
            return;
        }
        socketPrint("success");
    }

    private void updateAlbum() throws IOException, ClassNotFoundException {
        Author author = promptAuthor();
        if (author == null) {
            socketPrint("No such author");
            return;
        }
        String name = socketPrompt("name");
        Album album = author.albums.get(name);
        if (album == null) {
            socketPrint("No suck album");
            return;
        }
        String content = socketPrompt("content");
        album.content = content;
        socketPrint("success");
    }

    private void listAlbum() throws IOException, ClassNotFoundException {
        Author author = promptAuthor();
        if (author == null) {
            socketPrint("No such author");
            return;
        }
        author.albums.forEach((k,v) -> {
            System.out.println("Album name: " + k + "; Content:" + v.content);
            try {
                socketPrint("key: " + k + " value:" + v.content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
