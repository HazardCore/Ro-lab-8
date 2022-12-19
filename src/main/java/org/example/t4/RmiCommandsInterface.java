package org.example.t4;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import org.example.Album;
import org.example.Author;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


interface RmiCommandsInterface extends Remote {
    void addAuthor(String name) throws RemoteException;
    void removeAuthor(String name) throws RemoteException;
    ArrayList<Author> listAuthor() throws RemoteException;
    Author promptAuthor(String authorName) throws RemoteException;
    void addAlbum(String authorName, String name, String content) throws IOException, RemoteException;
    void removeAlbum(String authorName, String name) throws IOException, RemoteException;
    void updateAlbum(String authorName, String name, String content) throws IOException, RemoteException;
    ArrayList<Album> listAlbum(String authorName) throws IOException, RemoteException;
}