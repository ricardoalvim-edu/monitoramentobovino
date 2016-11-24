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
import java.net.Socket;
import java.rmi.UnknownHostException;

/**
 *
 * @author ricardoalvim
 */
public class Cliente {
    private static String sentence;
    private static String modifiedSentence;
    private static Socket client;
    
    public static void main(String[] args) {

       try {
           // Cria um buffer que armazenará as informações de entrada do teclado
           BufferedReader inFromUSer = new BufferedReader(new InputStreamReader(System.in));

           // Cria um Socket cliente passando como parâmetro o ip e a porta do servidor   
           client = new Socket("localhost", 40000);
           System.out.println("Conectou a localhost:4000");
            while (true){
                

                // Cria um stream de saída 
                //ataOutputStream outToServer = new DataOutputStream(client.getOutputStream());

                // Cria um buffer que armazenará as informações retornadas pelo servidor
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

                // Atribui as informações armazenadas no buffer do teclado à variável "sentence"
                //sentence = inFromUSer.readLine();

                // Disponibiliza as informações contidas em "sentence" para a stream de saída do cliente
                //outToServer.writeBytes(sentence + "\n");

                // Atribui as informações modificadas pelo servidor na variável "modifiedSentence"
                modifiedSentence = inFromServer.readLine();
                
                // Imprime no console do cliente a informação retornada pelo servidor
                System.out.println("From Server: " + modifiedSentence);                              
            }
            // Fecha o Socket     
            //client.close();
       } catch (UnknownHostException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
   } 
}
