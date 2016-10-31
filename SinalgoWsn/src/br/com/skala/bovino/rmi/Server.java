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
import projects.wsn1.nodes.messages.MessageToSink;
import sinalgo.nodes.Position;

/**
 *
 * @author ricardoalvim
 */
public class Server implements Analisadora{

    @Override
    public boolean isOnArea(MessageToSink m) throws RemoteException {
        Position p = m.getOrigin().getPosition();
        
        System.out.println("Bovino #"+m.getOrigin().ID);
        if (p.xCoord < 15.00){
            return true;
        }
        else if (p.yCoord < 15.00){
            return true;
        }   
        else if (p.xCoord > 85.00){
            return true;
        }   
        else if (p.yCoord > 85.00){
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
