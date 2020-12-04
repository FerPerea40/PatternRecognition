/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author working
 */
public class Patron {

    private String clase;
    private String claseResultante;
    private double[] vectorC;
    private int num_clas;

    public Patron() {
        
    }    
    
    public Patron(String clase, String claseResultante, double[] vectorC) {
        this.clase = clase;
        this.claseResultante = claseResultante;
        this.vectorC = vectorC;
        this.num_clas = num_clas;
    }

    
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @return the claseResultante
     */
    public String getClaseResultante() {
        return claseResultante;
    }

    /**
     * @param claseResultante the claseResultante to set
     */
    public void setClaseResultante(String claseResultante) {
        this.claseResultante = claseResultante;
    }

    /**
     * @return the vectorC
     */
    public double[] getVectorC() {
        return vectorC;
    }


    public void setVectorC(double[] vectorC) {
        this.vectorC = vectorC;
    }

    public int getNum_clas() {
        return num_clas;
    }

    public void setNum_clas(int num_clas) {
        this.num_clas = num_clas;
    }

}
