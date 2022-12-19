package org.example.t4;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;

public class ClientRMI {
        public static void main(String[] args) throws IOException, AlreadyBoundException, NotBoundException {
            Registry registry = LocateRegistry.getRegistry(8001);
            RmiCommandsInterface rmi = (RmiCommandsInterface) registry.lookup("productsRMI");

            // FIX TO STORE A LIST ON A SERVER
            rmi.addAuthor("Images");
            rmi.addAuthor("Downloads");
            rmi.addAlbum("Images", "pwd.txt", "324apple");
            System.out.println(rmi.listAuthor());
            System.out.println(rmi.listAlbum("Images"));
        }
}
