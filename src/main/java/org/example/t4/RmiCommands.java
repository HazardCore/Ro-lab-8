package org.example.t4;

import org.example.Album;
import org.example.Author;

import java.io.IOException;
import java.rmi.server.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;

import org.example.Album;
import org.example.Author;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class RmiCommands extends UnicastRemoteObject implements RmiCommandsInterface {

    private HashMap<String, Author> authors = new HashMap<String, Author>();

    public RmiCommands() throws RemoteException {
        super();
    }

    public void addAuthor(String name) throws RemoteException {
        if (authors.containsKey(name)) {
            System.out.println("Author already exists");
            return;
        }
        Author author = new Author(name);
        authors.put(name, author);
        System.out.println("success");
    }

    public void removeAuthor(String name) throws RemoteException {
        if (authors.remove(name) == null) {
            System.out.println("no author");
            return;
        }
        System.out.println("success");
    }


    public ArrayList<Author> listAuthor() throws RemoteException {
        authors.forEach((k,v) -> {
            System.out.println("key: " + k + " value:" + v);
        });
        return new ArrayList(authors.values());
    }

    public Author promptAuthor(String authorName) throws RemoteException {
        return authors.get(authorName);
    }

    public void addAlbum(String authorName, String name, String content) throws IOException, RemoteException {
        Author author = promptAuthor(authorName);
        if (author == null) {
            System.out.println("No such author");
            return;
        }

        if (author.albums.containsKey(name)) {
            System.out.println("Already exists");
            return;
        }

        Album album = new Album(name, content);
        author.albums.put(name, album);
        System.out.println("success");
    }

    public void removeAlbum(String authorName, String name) throws IOException, RemoteException {
        Author author = promptAuthor(authorName);
        if (author == null) {
            System.out.println("No such author");
            return;
        }

        if (author.albums.remove(name) == null) {
            System.out.println("No such album");
            return;
        }
        System.out.println("success");
    }

    public void updateAlbum(String authorName, String name, String content) throws IOException, RemoteException {
        Author author = promptAuthor(authorName);
        if (author == null) {
            System.out.println("No such author");
            return;
        }

        Album album = author.albums.get(name);
        if (album == null) {
            System.out.println("No suck album");
            return;
        }

        album.content = content;
        System.out.println("success");
    }

    public ArrayList<Album> listAlbum(String authorName) throws IOException, RemoteException {
        Author author = promptAuthor(authorName);
        if (author == null) {
            System.out.println("No such author");
            return new ArrayList();
        }
        author.albums.forEach((k,v) -> {
            System.out.println("Album name: " + k + "; Content:" + v.content);
        });

        return new ArrayList(author.albums.values());
    }
}
