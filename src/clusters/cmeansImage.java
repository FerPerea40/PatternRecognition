/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusters;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import data.JframeImagen;
import data.MatrizConf;
import data.Patron;
import data.PatronImg;
import data.SeleccImg;
import interfaces.ClasificadorNoSupervisado;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Dell
 */
public class cmeansImage implements ClasificadorNoSupervisado {

    ArrayList<PatronImg> representativos;
    ArrayList<PatronImg> patrones;
    private int C;
    private MatrizConf matriz;
    BufferedImage bi;
    public cmeansImage (int C) {
        this.patrones = new ArrayList<>();
        this.representativos = new ArrayList<>();
        this.C = C;
        this.matriz = null;
    }

   public void entrenamiento(Image imagenOriginal){
        // generar la coleccion de instancias obtenidas de los colores de la imagen 
       bi = SeleccImg.toBufferedImage(imagenOriginal);
                JframeImagen frame = new JframeImagen(imagenOriginal);

        ArrayList<PatronImg> instancias = new ArrayList<>();
        // recorremos la imagen
        Color color;
        for(int x=0; x<bi.getWidth();x++){
            for(int y = 0 ; y<bi.getHeight();y++){
                int rgb = bi.getRGB(x, y);
                color = new Color(rgb);
                patrones.add(new PatronImg(x, y,new double[]{color.getRed(),
                    color.getGreen(),
                    color.getBlue()}));
            }
        }
   }
        
  
    @Override
    public void clasificar(Patron comparaPatron) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void clasificar() {
        int clus = 1;
        int ele;
        Random ran = new Random();
        ArrayList<PatronImg> nuevos;

        ele = ran.nextInt(patrones.size());
        representativos.add(new PatronImg(0,  0, patrones.get(ele).getVectorC()));

        while (clus < this.C) {
            ele = ran.nextInt(patrones.size());
            if (!busca(patrones.get(ele), clus)) {
                representativos.add(new PatronImg( patrones.get(ele).getX(),patrones.get(ele).getX(), patrones.get(ele).getVectorC()));
                clus++;

            }
        }
                        System.out.println();
        do {
            for (PatronImg aux : patrones) {
                double distC = data.SeleccImg.calcularDistEucli(aux, this.representativos.get(0));
                aux.setClaseResultante(this.representativos.get(0).getClase());
                for (int x = 1; x < this.representativos.size(); x++) {
                    double daux = data.SeleccImg.calcularDistEucli(aux, this.representativos.get(x));
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
                       );
            }
            nuevos = promediar(patrones);

        } while (sonDiferentes(nuevos));
        for (int i = 0; i < this.representativos.size(); i++) {

            System.out.println("Clase: " + this.representativos.get(i).getClase() + " Vector: " + this.representativos.get(i).getVectorC()[0]
                    + " ---- " + this.representativos.get(i).getVectorC()[1]
                    + " ---- " + this.representativos.get(i).getVectorC()[2]
                   );
        }
                 
        System.out.println("Listo");
 for (PatronImg aux : patrones) {
               for (int x = 0; x < this.representativos.size(); x++) {
                    
                    if (aux.getClaseResultante() == this.representativos.get(x).getClase()) {
                        aux.setVectorC(this.representativos.get(x).getVectorC());
                    }

                }
            }
         System.out.println("Listo");

    }

    public ArrayList<PatronImg> promediar(ArrayList<PatronImg> instancias) {
        ArrayList<String> totalClases = new ArrayList<>();
        ArrayList<String> x = new ArrayList<>();
        ArrayList<String> r = new ArrayList<>();
        ArrayList<PatronImg> prom = new ArrayList<>();
        prom.clear();
        for (int i = 0; i < instancias.size(); i++) {
            totalClases.add(instancias.get(i).getClaseResultante());
            x.add(""+instancias.get(i).getX());
            r.add(""+instancias.get(i).getY());
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
            prom.add(new PatronImg(Integer.parseInt(x.get(i)),Integer.parseInt(r.get(i)),new double[]{(int)vectorFinal[0],(int)vectorFinal[1],(int)vectorFinal[2]}));
        }
        return prom;
    }

    private boolean busca(PatronImg get, int tam) {
        for (int i = 0; i < tam; i++) {
            if (this.representativos.get(i).getVectorC().equals(get)) {
                return true;
            }
        }
        return false;
    }

    private boolean sonDiferentes(ArrayList<PatronImg> nuevos) {
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
        
         BufferedImage nuevo = new BufferedImage(bi.getWidth(),bi.getHeight(),BufferedImage.TYPE_INT_RGB);
        
        for(PatronImg p: patrones){
            int x = p.getX();
            int y = p.getY();
            nuevo.setRGB(x, y,Integer.parseInt(p.getClaseResultante()));
        }
        
        Image imagencluster = SeleccImg.toImage(nuevo);
       
         JframeImagen frame = new JframeImagen(imagencluster);
       /* for (int i = 0; i < this.patrones.size(); i++) {
            System.out.println("  Clase: " + this.patrones.get(i).getClase() + "  Clase Resultante -->" + this.patrones.get(i).getClaseResultante());
            /*if(i%2!=0){
            System.out.println();
            }
        }*/

    }

    @Override
    public void entrenar(ArrayList<Patron> instancias) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

}

