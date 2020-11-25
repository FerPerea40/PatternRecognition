/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import data.BurbujaOptimizado;
import data.DistInsta;
import data.MatrizConf;
import data.Patron;
import interfaces.ClasificadorSupervisado;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Dell
 */
public class Bayes implements ClasificadorSupervisado {

    ArrayList<Patron> promedios;
    ArrayList<Patron> varianzas;
    ArrayList<Patron> desviaciones;
    ArrayList<Patron> distnorm;
    ArrayList<Patron> post;
    ArrayList<Double> promClase;
    double evidencia;
    ArrayList<String> totalClases;

    public Bayes() {
        this.promedios = new ArrayList<>();
        this.varianzas = new ArrayList<>();
        this.desviaciones = new ArrayList<>();
        this.distnorm = new ArrayList<>();
        this.promClase = new ArrayList<>();
        this.post = new ArrayList<>();
        this.totalClases = new ArrayList<>();
    }

    @Override
    public void entrenar(ArrayList<Patron> instancias) {
        for (int i = 0; i < instancias.size(); i++) {
            totalClases.add(instancias.get(i).getClase());
        }

        Set<String> hashSet = new HashSet<String>(totalClases);
        totalClases.clear();
        totalClases.addAll(hashSet);
        obtpromedios(instancias);
        obtvarianzas(instancias);
        obtdesvia(instancias);

    }

    @Override
    public void clasificar(Patron patron) {
        obtdistnor(patron);
        
        double auxevi = 0;
        for (int i = 0; i < promClase.size(); i++) {
            evidencia = promClase.get(i);
            for (int j = 0; j < desviaciones.get(i).getVectorC().length; j++) {
                evidencia = evidencia * desviaciones.get(i).getVectorC()[j];
            }
            auxevi += evidencia;
        }
            double posteriori = 0;
        for (int i = 0; i < promClase.size(); i++) {
            evidencia = promClase.get(i);
            for (int j = 0; j < distnorm.get(i).getVectorC().length; j++) {
                evidencia = evidencia * distnorm.get(i).getVectorC()[j];
            }
            double[]posaux = {evidencia/auxevi};
          
            post.add(new Patron(distnorm.get(i).getClase(),"",posaux));
        }
        
        double may=0;
        int clasemay=0;
        
        for(int i=0;i<post.size();i++){
              if(post.get(i).getVectorC()[0]>may){
              may=post.get(i).getVectorC()[0];
              clasemay=i;
              System.out.println("La clase"+clasemay);
                }
           patron.setClaseResultante(post.get(clasemay).getClase());
                }
        
System.out.println("La clase resultante: "+post.get(clasemay).getClase()+" y era: "+patron.getClase());
   post.clear();
   distnorm.clear();
    }

    public ArrayList<Patron> obtpromedios(ArrayList<Patron> instancias) {

        int n_clases = totalClases.size();
        int tam_vector = instancias.get(0).getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];
        double[] contadoresPromedio = new double[n_clases];

        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            String temporal = totalClases.get(i); //el temporal es para saber que clase evaluamos
            contadoresPromedio[i] = 0;

            for (int j = 0; j < instancias.size(); j++) {  //siempre vamos a evaluar todas las instancias de nuestro arraylist de patrones
                String comparar = instancias.get(j).getClase();

                if (temporal.equals(comparar)) { //si la instancia que evaluamos es igual a nuestro temporal
                    contadoresPromedio[i]++;//aumentamos en su contador respectivo
                    for (int y = 0; y < tam_vector; y++) { //sumamos cada columa "y" a su respectiva clase
                        matriz_vectores[i][y] += instancias.get(j).getVectorC()[y];
                    }
                }
            }
            double r = ((contadoresPromedio[i]) / (instancias.size()));
            promClase.add(r);
        }
        //crearemos un ciclo para sacar el promedios de una vez, y mandar su respectivo vector  
        for (int i = 0; i < n_clases; i++) {
            double[] vectorFinal = new double[tam_vector];
            for (int y = 0; y < tam_vector; y++) {
                vectorFinal[y] = matriz_vectores[i][y] / contadoresPromedio[i];

            }

            promedios.add(new Patron(totalClases.get(i), "", vectorFinal));
        }
        return promedios;
    }

    public ArrayList<Patron> obtvarianzas(ArrayList<Patron> instancias) {

        int n_clases = totalClases.size();
        int tam_vector = instancias.get(0).getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];
        int[] contadoresPromedio = new int[n_clases];

        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            String temporal = totalClases.get(i); //el temporal es para saber que clase evaluamos
            contadoresPromedio[i] = 0;

            for (int j = 0; j < instancias.size(); j++) {  //siempre vamos a evaluar todas las instancias de nuestro arraylist de patrones
                String comparar = instancias.get(j).getClase();

                if (temporal.equals(comparar)) { //si la instancia que evaluamos es igual a nuestro temporal
                    contadoresPromedio[i]++;//aumentamos en su contador respectivo
                    for (int y = 0; y < tam_vector; y++) { //sumamos cada columa "y" a su respectiva clase
                        matriz_vectores[i][y] += pow((instancias.get(j).getVectorC()[y]) - (promedios.get(i).getVectorC()[y]), 2);
                    }
                }
            }
        }
        //crearemos un ciclo para sacar el promedios de una vez, y mandar su respectivo vector  
        for (int i = 0; i < n_clases; i++) {
            double[] vectorFinal = new double[tam_vector];
            for (int y = 0; y < tam_vector; y++) {
                vectorFinal[y] = matriz_vectores[i][y] / (contadoresPromedio[i] - 1);
            }
            varianzas.add(new Patron(totalClases.get(i), "", vectorFinal));
        }
        return varianzas;
    }

    public ArrayList<Patron> obtdesvia(ArrayList<Patron> instancias) {

        int n_clases = totalClases.size();
        int tam_vector = instancias.get(0).getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];

        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            for (int j = 0; j < instancias.size(); j++) {  //siempre vamos a evaluar todas las instancias de nuestro arraylist de patrones
                for (int y = 0; y < tam_vector; y++) { //sumamos cada columa "y" a su respectiva clase
                    matriz_vectores[i][y] = sqrt(varianzas.get(i).getVectorC()[y]);
                }
            }
        }
        //crearemos un ciclo para sacar el promedios de una vez, y mandar su respectivo vector  
        for (int i = 0; i < n_clases; i++) {
            double[] vectorFinal = new double[tam_vector];
            for (int y = 0; y < tam_vector; y++) {
                vectorFinal[y] = matriz_vectores[i][y];
            }
            desviaciones.add(new Patron(totalClases.get(i), "", vectorFinal));
        }
        return desviaciones;
    }

    public ArrayList<Patron> obtdistnor(Patron instancias) {

        int n_clases = totalClases.size();
        int tam_vector = instancias.getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];

        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            for (int y = 0; y < tam_vector; y++) { //sumamos cada columa "y" a su respectiva clase
                //matriz_vectores[i][y] = (1/(sqrt(2*Math.PI*(pow(varianzas.get(i).getVectorC()[y],2)))))*pow(Math.E,(-1)*(pow((instancias.getVectorC()[y]-promedios.get(i).getVectorC()[y]),2))/(2*(pow(varianzas.get(i).getVectorC()[y],2))));
                matriz_vectores[i][y] = (1 / (sqrt(2 * Math.PI * (varianzas.get(i).getVectorC()[y])))) * pow(Math.E, (-1) * (pow((instancias.getVectorC()[y] - promedios.get(i).getVectorC()[y]), 2)) / (2 * (varianzas.get(i).getVectorC()[y])));

            }

        }
        //crearemos un ciclo para sacar el promedios de una vez, y mandar su respectivo vector  
        for (int i = 0; i < n_clases; i++) {
            double[] vectorFinal = new double[tam_vector];
            for (int y = 0; y < tam_vector; y++) {
                vectorFinal[y] = matriz_vectores[i][y];
            }
            distnorm.add(new Patron(totalClases.get(i), "", vectorFinal));
        }
        return distnorm;
    }

 public int eficacia(ArrayList<Patron> este) {
        int n = 0;
        for (int k = 0; k < este.size(); k++) {
            if (este.get(k).getClaseResultante().equals(este.get(k).getClase())) {
                n++;
            }
        }
        System.out.println("Aciertos de Bayes: " + n);
        System.out.println("Total de Elemnentos: " + este.size());
        return (n * 100) / este.size();
    }

}
