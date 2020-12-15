/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusters;

import data.MatrizConf;
import data.Patron;
import interfaces.ClasificadorNoSupervisado;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Dell
 */
public class cmeans implements ClasificadorNoSupervisado {

    ArrayList<Patron> representativos;
    ArrayList<Patron> patrones;
    private int C;
    private MatrizConf matriz;

    public cmeans(int C) {
        this.patrones = new ArrayList<>();
        this.representativos = new ArrayList<>();
        this.C = C;
        this.matriz = null;
    }

    @Override
    public void entrenar(ArrayList<Patron> instancias) {
        patrones = instancias;
    }

    @Override
    public void clasificar(Patron comparaPatron) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void clasificar() {
        int clus = 1;
        int ele;
        Random ran = new Random();
        ArrayList<Patron> nuevos;

        ele = ran.nextInt(patrones.size());
        representativos.add(new Patron("" + 0, "" + 0, patrones.get(ele).getVectorC()));

        while (clus < this.C) {
            ele = ran.nextInt(patrones.size());
            if (!busca(patrones.get(ele), clus)) {
                representativos.add(new Patron("" + clus, "" + clus, patrones.get(ele).getVectorC()));
                clus++;

            }
        }
        do {
            for (Patron aux : patrones) {
                double distC = data.DistInsta.calculaDistanciaEcuclidiana(aux, this.representativos.get(0));
                aux.setClaseResultante(this.representativos.get(0).getClase());

                for (int x = 1; x < this.representativos.size(); x++) {
                    double daux = data.DistInsta.calculaDistanciaEcuclidiana(aux, this.representativos.get(x));
                    if (daux < distC) {
                        distC = daux;
                        aux.setClaseResultante(this.representativos.get(x).getClase());

                    }

                }
            }
           for (int i = 0; i < this.representativos.size(); i++) {

                System.out.println("Clase: " + this.representativos.get(i).getClase() + " Vector: " + this.representativos.get(i).getVectorC()[0]
                        + " ---- " + this.representativos.get(i).getVectorC()[1]
                        + " ---- " + this.representativos.get(i).getVectorC()[2]
                        + " ---- " + this.representativos.get(i).getVectorC()[3]);
            }
            nuevos = promediar(patrones);

        } while (sonDiferentes(nuevos));
        for (int i = 0; i < this.representativos.size(); i++) {

            System.out.println("Clase: " + this.representativos.get(i).getClase() + " Vector: " + this.representativos.get(i).getVectorC()[0]
                    + " ---- " + this.representativos.get(i).getVectorC()[1]
                    + " ---- " + this.representativos.get(i).getVectorC()[2]
                    + " ---- " + this.representativos.get(i).getVectorC()[3]);
        }
       // System.out.println("Listo");

    }

    public ArrayList<Patron> promediar(ArrayList<Patron> instancias) {
        ArrayList<String> totalClases = new ArrayList<>();

        ArrayList<Patron> prom = new ArrayList<>();
        prom.clear();
        for (int i = 0; i < instancias.size(); i++) {
            totalClases.add(instancias.get(i).getClaseResultante());
        }

        Set<String> hashSet = new HashSet<String>(totalClases);
        totalClases.clear();
        totalClases.addAll(hashSet);

        int n_clases = totalClases.size();
        int tam_vector = instancias.get(0).getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];
        int[] contadoresPromedio = new int[n_clases];

        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            String temporal = totalClases.get(i); //el temporal es para saber que clase evaluamos
            contadoresPromedio[i] = 0;

            for (int j = 0; j < instancias.size(); j++) {  //siempre vamos a evaluar todas las instancias de nuestro arraylist de patrones
                String comparar = instancias.get(j).getClaseResultante();

                if (temporal.equals(comparar)) { //si la instancia que evaluamos es igual a nuestro temporal
                    contadoresPromedio[i]++;//aumentamos en su contador respectivo
                    for (int y = 0; y < tam_vector; y++) { //sumamos cada columa "y" a su respectiva clase
                        matriz_vectores[i][y] += instancias.get(j).getVectorC()[y];
                    }
                }
            }
        }
        //crearemos un ciclo para sacar el promedios de una vez, y mandar su respectivo vector  
        for (int i = 0; i < n_clases; i++) {
            double[] vectorFinal = new double[tam_vector];
            for (int y = 0; y < tam_vector; y++) {
                vectorFinal[y] = matriz_vectores[i][y] / contadoresPromedio[i];
            }
            prom.add(new Patron(totalClases.get(i), totalClases.get(i), vectorFinal));
        }
        return prom;
    }

    private boolean busca(Patron get, int tam) {
        for (int i = 0; i < tam; i++) {
            if (this.representativos.get(i).getVectorC().equals(get)) {
                return true;
            }
        }
        return false;
    }

    private boolean sonDiferentes(ArrayList<Patron> nuevos) {
        // si se determina que son diferentes susituimos a los actuales.
        for (int x = 0; x < nuevos.size(); x++) {
            if (!Arrays.equals(nuevos.get(x).getVectorC(), this.representativos.get(x).getVectorC())) {
                this.representativos = nuevos;
                return true;
            }
        }

        return false;
    }

    public void imprimirfinal() {
        for (int i = 0; i < this.patrones.size(); i++) {
            System.out.println("  Clase: " + this.patrones.get(i).getClase() + "  Clase Resultante -->" + this.patrones.get(i).getClaseResultante());
            /*if(i%2!=0){
            System.out.println();
            }*/
        }

    }

    public int eficacia(ArrayList<Patron> este) {
        int n = 0;
        for (int k = 0; k < este.size(); k++) {
            if (este.get(k).getClaseResultante().equals(este.get(k).getClase())) {
                n++;
            }
        }
        System.out.println("Aciertos Mínima Distancia: " + n);
        System.out.println("Total de Elemnentos: " + este.size());
        return (n * 100) / este.size();
    }

    public void generarMat(ArrayList<Patron> patrones) {

        this.matriz = new MatrizConf(patrones);
        this.matriz.generartabla("Minima Distancia");

        this.matriz.pack();
        this.matriz.setVisible(true);

    }

    /**
     * @return the mc
     */
    public MatrizConf getMc() {
        return matriz;
    }

}
