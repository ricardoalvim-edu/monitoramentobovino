/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.skala.bovino.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import projects.wsn1.nodes.messages.ToSink;
/**
 *
 * @author ricardoalvim
 */
public interface Analisadora extends Remote{
    public boolean isOnArea(Integer ID, Double X, Double Y) throws RemoteException;
}
