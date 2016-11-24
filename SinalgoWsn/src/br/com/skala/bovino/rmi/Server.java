/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.skala.bovino.rmi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricardoalvim
 */
public class Server implements Analisadora{
    private ArrayList<BovinoInfo> bois = new ArrayList<BovinoInfo>();
    private static String clientSentence;
    private static String capitalized;
    private static ServerSocket socket;
    private static Socket connectionSocket;
    
    public Server() throws RemoteException {
        super();
    } 
    
    @Override
    public boolean isOnArea(Integer ID, Double X, Double Y) throws RemoteException {

        if (X < 15.00){
            this.showMsgOutside(ID);            
            return false;
        }
        else if (Y< 15.00){
            this.showMsgOutside(ID);
            return false;
        }   
        else if (X > 85.00){
            this.showMsgOutside(ID);
            return false;
        }   
        else if (Y > 85.00){
            this.showMsgOutside(ID);
            return false;
        }      
        return true;
    }
    
    public void showMsgOutside(Integer ID){
        String msg = new String("O bovino #"+ID+ " está fora do pasto!");
        sendMsg(msg);
    }
    
    public static void main(String[] args) throws RemoteException {
        Server s = new Server();
        try {
            
            Analisadora canal = (Analisadora) UnicastRemoteObject.exportObject(s, 0);            
            Registry registro = LocateRegistry.createRegistry(1099);        
            registro.bind("bovino", canal);   
            if (socket == null){
                runServerSocket();
            }
                      
            System.out.println("Servidor pronto!");            
            
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public static void runServerSocket(){
       try {
 
           // Cria um SocketServer (Socket característico de um servidor)
           socket = new ServerSocket(40000);  
   

           /* Cria um objeto Socket, mas passando informações características de um servidor,
            *no qual somente abre uma porta e aguarda a conexão de um cliente 
            */
           connectionSocket = socket.accept();
           sendMsg ("cliente iniciou!");
      
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }  
    }
    
    public static void sendMsg (String msg){
        try {
            // Cria uma buffer que irá armazenar as informações enviadas pelo cliente
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            // Cria uma stream de sáida para retorno das informações ao cliente
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            // Faz a leitura das informações enviadas pelo cliente as amazenam na variável "clientSentence"
            
            /* Faz uma modificação na String enviada pelo cliente, simulando um processamento em "back-end"
            * antes de retorná-la ao cliente
            */
            capitalized = msg.toUpperCase() + "\n";
            // Retorna as informações modificadas, ao cliente
            outToClient.writeBytes(capitalized);    
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
