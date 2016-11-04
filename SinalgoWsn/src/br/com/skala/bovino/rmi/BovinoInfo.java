/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.skala.bovino.rmi;

import java.io.Serializable;

/**
 *
 * @author ricardoalvim
 */
public class BovinoInfo implements Serializable{
    private Integer ID;
    private Double X;
    private Double Y;
    
    public BovinoInfo(Integer ID, Double X, Double Y){
        this.ID = ID;
        this.X = X;
        this.Y = Y;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Double getX() {
        return X;
    }

    public void setX(Double X) {
        this.X = X;
    }

    public Double getY() {
        return Y;
    }

    public void setY(Double Y) {
        this.Y = Y;
    }
}
