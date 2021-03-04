/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedesNeuronales;

import data.Patron;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class PerceptronSimple {

    //entradas
    private ArrayList<Patron> entradas;
    private double[] pesos;
    private double[] pesosN3;
    private double[] pesosN1;
    private double[] pesosN2;

    private double acumulado;
    private double umbral;
    // entrenamiento
    private double alpha;
        double salida;

    public PerceptronSimple(ArrayList<Patron> entradas, double umbral, double alpha) {
        this.entradas = entradas;
        this.alpha = alpha;
        this.umbral = umbral;
    }

    public void entrenar() {
        pesos = new double[3];
        for (int i = 0; i < entradas.get(0).getVectorC().length + 1; i++) {
            pesos[i] = Math.random();
        }

        double sumatoria = 0;
        double[] x;
        int r = 0;
        
        while (r < entradas.size()) {
            sumatoria = 0;
            x = new double[]{umbral, entradas.get(r).getVectorC()[0], entradas.get(r).getVectorC()[1]};
            for (int i = 0; i < x.length; i++) {
                sumatoria += pesos[i] * x[i];
            }

           //Funcion de Activación
            if (sumatoria >= 0) {
                salida = 1;
            } else {
                salida = -1;
            }
           //Calcular error para saber si se recalcula
            double error = Integer.parseInt(entradas.get(r).getClase()) - salida;
            if (error != 0) {
                for (int i = 0; i < pesos.length; i++) {
                    pesos[i] = pesos[i] + (this.alpha * error * x[i]);
                }
                System.out.println("Aprendiendo espere...");
                r = 0;
            } else {
                System.out.println("Entrada: "+this.entradas.get(r).getVectorC()[0]+" , "+this.entradas.get(r).getVectorC()[1]+"--> Salida: "+ salida );

                r++;
            }

        }
        System.out.println("¡Estoy Entrenado!");

    }
    
    
      public void entrenarXOR() {
     
         int r=0;
       
        while(r<entradas.size()){
           int iteraciones=0;
            double error3;
             double error2;
              double error1;
                  double y1=0,y2=0,y3=0; 
        pesosN1 = new double[3];
        pesosN2 = new double[3];
        pesosN3 = new double[3];
        double[] x;

        for (int i = 0; i < entradas.get(0).getVectorC().length + 1; i++) {
              pesosN1[i] = Math.random();
              pesosN2[i] = Math.random();    
              pesosN3[i] = Math.random();

        }
         while(iteraciones<=10000000){
          x = new double[]{umbral, entradas.get(r).getVectorC()[0], entradas.get(r).getVectorC()[1]};
            
        for(int i=0;i<pesosN1.length;i++){
            y1 += pesosN1[i]* x[i];
        }
         y1=1.0/(1+Math.pow(Math.E,(-1)*y1));
        for(int i=0;i<pesosN2.length;i++){
          y2 += pesosN2[i]* x[i];
        }
          y2=1.0/(1+Math.pow(Math.E,(-1)*y2));

          double[] entradasN3 = new double[]{umbral,y1,y2};
              for(int i=0;i<pesosN2.length;i++){
          y3 += pesosN3[i]* entradasN3[i];
        }
          y3 =1.0/(1+Math.pow(Math.E,(-1)*y3));
      
             if(iteraciones==1000000){
           break;
         }     
          error3 = (y3*(1-y3))*(Integer.parseInt(entradas.get(r).getClase()) - y3);
          for (int i = 0; i < entradas.get(0).getVectorC().length + 1; i++) {
              pesosN3[i] = pesosN3[i] + (this.alpha * error3 * x[i]);

          }
         
             error1= (y1*(1-y1))*(error3 - y3);
             error2 = (y2*(1-y2))*(error3 - y3);
                 for (int i = 0; i < entradas.get(0).getVectorC().length + 1; i++) {
                    pesosN1[i] = pesosN1[i] + (this.alpha * error1 * x[i]);
                    pesosN2[i] = pesosN2[i] + (this.alpha * error2 * x[i]);
                    
            }
                 iteraciones++;
            }
      
          System.out.println("Entrada: "+this.entradas.get(r).getVectorC()[0]+" , "+this.entradas.get(r).getVectorC()[1]+"--> Salida: "+ Math.round(y3) );
          iteraciones=0;
            r++;
            
        }
      
      }
            
            
       public void ClasificarXOR(Patron p){  
            
             double[] x;
           double y1 = 0;
           double y2 = 0;
           double y3 = 0;
           int iteraciones=0;
 
           x = new double[]{umbral, p.getVectorC()[0], p.getVectorC()[1]};
            
        for(int i=0;i<pesosN1.length;i++){
            y1 += pesosN1[i]*x[i];
        }
         y1=1.0/(1+Math.pow(Math.E,(-1)*y1));
        for(int i=0;i<pesosN2.length;i++){
          y2 += pesosN2[i]* x[i];
        }
          y2=1.0/(1+Math.pow(Math.E,(-1)*y2));

          double[] entradasN3 = new double[]{umbral,y1,y2};
              for(int i=0;i<pesosN2.length;i++){
          y3 += pesosN3[i]*entradasN3[i];
        }
          y3 =1.0/(1+Math.pow(Math.E,(-1)*y3));
 
           //  System.out.println("Entrada: "+p.getVectorC()[0]+" , "+p.getVectorC()[1]+"--> Salida: "+ y3 );

           if (Math.round(y3) >= 0) {
               System.out.println("Entrada: "+p.getVectorC()[0]+" , "+p.getVectorC()[1]+"--> Salida: "+ 1 );
                p.setClaseResultante("1");
            } else {
               System.out.println("Entrada: "+p.getVectorC()[0]+" , "+p.getVectorC()[1]+"--> Salida: "+ 0 );
                p.setClaseResultante("0");
            }
      
           }
        

    

    
    public void Clasificar(Patron p){
            double[] x;
           double sumatoria = 0;

           x = new double[]{umbral, p.getVectorC()[0], p.getVectorC()[1]};
            for (int i = 0; i < x.length; i++) {
                sumatoria += pesos[i] * x[i];
            }

           //Funcion de Activación
            if (sumatoria >= 0) {
               System.out.println("Entrada: "+p.getVectorC()[0]+" , "+p.getVectorC()[1]+"--> Salida: "+ 1 );
                p.setClaseResultante("1");
            } else {
               System.out.println("Entrada: "+p.getVectorC()[0]+" , "+p.getVectorC()[1]+"--> Salida: "+ 0 );
                p.setClaseResultante("-1");
            }
    
    
    }

}
