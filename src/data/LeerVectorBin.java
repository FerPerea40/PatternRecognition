/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Dell
 */
public class LeerVectorBin {

    public static ArrayList<Patron> tokenizarDataSet() throws IOException {
        // ventana para abrir el txt

        String texto, aux;
        LinkedList<String> lista = new LinkedList();
        ArrayList<Patron> patrones = new ArrayList<>();
        try {
            //llamamos el metodo que permite cargar la ventana
            JFileChooser file = new JFileChooser();
            file.showOpenDialog(file);
            // FileReader fr = new FileReader("C:\\Users\\carli\\Desktop\\iris.data"); 
            //abrimos el archivo seleccionado
            File abre = file.getSelectedFile();

            //recorremos el archivo y lo leemos
            if (abre != null) {
                FileReader archivos = new FileReader(abre);
                BufferedReader lee = new BufferedReader(archivos);

                while ((aux = lee.readLine()) != null) {
                    texto = aux;
                    lista.add(texto);
                }
                lee.close();
              //  System.out.println(lista.size());
              
               ArrayList<String> lista2 = new ArrayList<>();
                String clase = "";
                String claseComp="";
               
                int n = 0;
                for (int i = 0; i < lista.size(); i++) {
                    StringTokenizer st = new StringTokenizer(lista.get(i), ",");

                    while (st.hasMoreTokens()) {
                        lista2.add(st.nextToken());
                    }

                    double[] vector = new double[lista2.size() - 1];

                    for (int r = 0; r < lista2.size() - 1; r++) {
                        vector[r] = Double.parseDouble(lista2.get(r));
                    }
                                      
                        claseComp = clase;
                        clase = lista2.get(lista2.size()-1);
                        if(!clase.equals(claseComp)){
                           n++;
                           patrones.add(new Patron(clase,"",vector));
                        }else{
                           patrones.add(new Patron(clase,"",vector));
                        }
                     
                    lista2.clear();
                }}}
          
    
    catch (IOException ex

    
        ) {
            JOptionPane.showMessageDialog(null, ex + ""
                + "\nNo se ha encontrado el archivo",
                "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
        return null;
    }

    return patrones ;
}

    public static VectorBinario obtenerDataset() throws IOException{
        ArrayList<VectorBinario> Datos =new ArrayList<>();
        ArrayList<Patron> patrones = LeerVectorBin.tokenizarDataSet();
        ArrayList<double[]> x = new ArrayList<>();
        ArrayList<double[]> y = new ArrayList<>();
        int[] tamanos = new int[2];
        
        tamanos[0]=patrones.get(0).getVectorC().length;
        
        for(int i=0 ; i<patrones.size();i++){
            if(tamanos[0]!=patrones.get(i).getVectorC().length){
                tamanos[1]=patrones.get(i).getVectorC().length;
            }
        }
         for(int i=0 ; i<patrones.size();i++){
             if(patrones.get(i).getVectorC().length==tamanos[0]){
              x.add(patrones.get(i).getVectorC());}
             else{
              y.add(patrones.get(i).getVectorC());
             }
        }
         
       Datos.add(new VectorBinario(x,y));
     return Datos.get(0);
    }
public static void main(String[] args) throws IOException {

        
        
    
     }
    
    
}
