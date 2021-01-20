/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rp20201a;

import clasificadores.Bayes;
import clasificadores.CAP;
import clasificadores.KNN;
import clusters.Min_Max;
import clasificadores.MinimaDistancia;
import clasificadores.lernMatrix;
import clusters.cmeans;
import clusters.cmeansImage;
import data.JframeImagen;
import data.Patron;
import data.LeerDatos;
import data.LeerVectorBin;
import data.MatrizConf;
import data.SeleccImg;
import data.VectorBinario;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;

/**
 *
 * @author working
 */
public class RP2021A {

    public static void main(String[] args) throws IOException {

       ArrayList<Patron> patrones = LeerDatos.tokenizarDataSet();
        /*   MinimaDistancia mn = new MinimaDistancia();
        mn.entrenar(patrones);
        KNN k = new KNN(3);
        k.entrenar(patrones);
        Bayes b = new Bayes();
        b.entrenar(patrones);

        
        for (int i = 0; i < patrones.size(); i++) {
            k.clasificar(patrones.get(i));
          }
         System.out.println("Eficacia KNN: " + k.eficacia(patrones) + "%");
         k.generarMat(patrones);
        
     
        for (int i = 0; i < patrones.size(); i++) {
            mn.clasificar(patrones.get(i));
           }
         System.out.println("Eficacia MD: " + mn.eficacia(patrones) + "%");
         mn.generarMat(patrones);
       
        for(int i=0;i<patrones.size();i++){
            b.clasificar(patrones.get(i));
          }
         System.out.println("Eficacia Bayes: " + b.eficacia(patrones) + "%");
          b.generarMat(patrones);*/

 /*  SeleccImg si = new SeleccImg();
     cmeansImage cmi = new cmeansImage(2000);
     cmi.entrenamiento(si.openImage());
     cmi.clasificar();
     cmi.imprimirfinal();
         // cm.generarMat(patrones);*/
      
/* Min_Max mM = new Min_Max(patrones, .91);
        mM.clasifica();*/



 
//VectorBinario vectores = LeerVectorBin.obtenerDataset();
//lernMatrix lm  =new lernMatrix(vectores);
//lm.aprendizaje();
//lm.recuperacion(new double[] {1,0,1,0,1});
//lm.recuperacion(new double[] {1,1,0,0,1});
//lm.recuperacion(new double[] {1,0,1,1,0});
//lm.recuperacion(new double[] {0,1,0,1,1});
//lm.recuperacion(new double[] {0,0,1,0,1});

 
CAP c = new CAP(patrones);
c.aprendizaje();
for(int i=0;i<patrones.size();i++){
c.recuperacion(patrones.get(i));
}
for(int i=1;i<=patrones.size();i++){
    if(i%3==0){
    System.out.println(i+"# Dato: "+patrones.get(i-1).getClase()+" --> "+patrones.get(i-1).getClaseResultante()+"  ");
    }else{
    System.out.print(i+"# Dato: "+patrones.get(i-1).getClase()+" --> "+patrones.get(i-1).getClaseResultante()+"  ");
    }
}
System.out.println("Eficacia : "+c.eficacia(patrones)+"%");
    }
}

