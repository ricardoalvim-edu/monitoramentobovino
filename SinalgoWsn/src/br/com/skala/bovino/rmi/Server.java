/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.skala.bovino.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;
import projects.wsn1.nodes.messages.MessageToSink;
import sinalgo.nodes.Position;

/**
 *
 * @author ricardoalvim
 */
public class Server implements Analisadora{

    @Override
    public boolean isOnArea(Integer ID, Double X, Double Y) throws RemoteException {
        
        System.out.println("Server - Bovino #"+ID);
        if (X < 15.00){
            return true;
        }
        else if (Y< 15.00){
            return true;
        }   
        else if (X > 85.00){
            return true;
        }   
        else if (Y > 85.00){
            return true;
        }      
        return false;
    }
    
    public static void main(String[] args) {
        Server s = new Server();
        try {
            
            Analisadora canal = (Analisadora) UnicastRemoteObject.exportObject(s, 0);            
            Registry registro = LocateRegistry.createRegistry(1099);        
            registro.bind("bovino", canal);
            
            System.out.println("Servidor pronto!");
            
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
