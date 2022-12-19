package org.example.t4;

import java.rmi.registry.*;

import java.rmi.*;
public class ServerRMI {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        RmiCommands commands = new RmiCommands();
        Registry registry = LocateRegistry.createRegistry(8001);
        registry.bind("productsRMI", commands);
        System.out.println("Server Started");
    }
}
