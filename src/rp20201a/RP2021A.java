/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rp20201a;

import clasificadores.Bayes;
import clasificadores.KNN;
import clasificadores.MinimaDistancia;
import data.Patron;
import data.LeerDatos;
import data.MatrizConf;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;

/**
 *
 * @author working
 */
public class RP2021A {

    public static void main(String[] args) throws IOException {

    /*   ArrayList<Patron> patrones = LeerDatos.tokenizarDataSet();
        MinimaDistancia mn = new MinimaDistancia();
        mn.entrenar(patrones);
        KNN k = new KNN(3);
        k.entrenar(patrones);

     //   ArrayList<Patron> patrones2 = LeerDatos.tokenizarDataSet();
   for (int i = 0; i < patrones.size(); i++) {
            System.out.println("Resultado"+i+": ");
            k.clasificar(patrones.get(i));
          }

        System.out.println("Eficacia KNN: " + k.eficacia(patrones) + "%");
        
            k.generarMat(patrones);
        
         System.out.println(k.getMc().toString());
        
        for (int i = 0; i < patrones.size(); i++) {
            mn.clasificar(patrones.get(i));
        }
      
      //  ArrayList<Patron> patrones3 = LeerDatos.tokenizarDataSet();
          
      System.out.println("Eficacia MD: " + mn.eficacia(patrones) + "%");
       k.generarMat(patrones);
        
         System.out.println(k.getMc().toString());*/
    
        ArrayList<Patron> patrones = LeerDatos.tokenizarDataSet();
        Bayes b = new Bayes();
        b.entrenar(patrones);
        for(int i=0;i<patrones.size();i++){
        b.clasificar(patrones.get(i));
        }
        System.out.println("Eficacia Bayes: " + b.eficacia(patrones) + "%");
    }
}
