/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class VectorBinario {

    private ArrayList<double[]> x = new ArrayList<>();
    private ArrayList<double[]> y  = new ArrayList<>();

    public VectorBinario() {

    }

    public VectorBinario(ArrayList<double[]> x, ArrayList<double[]> y) {
        this.x = x;
        this.y = y;

    }

    public ArrayList<double[]> getX() {
        return x;
    }

    public void setX(ArrayList<double[]> x) {
        this.x = x;
    }

    public ArrayList<double[]> getY() {
        return y;
    }

    public void setY(ArrayList<double[]> y) {
        this.y = y;
    }

  

  

  

}
