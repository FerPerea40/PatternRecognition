/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Dell
 */
public class MatrizConf extends JFrame{
      
    private double[][] matriz;
    private ArrayList<Patron> instancias;
    private ArrayList<String> clases;

    public MatrizConf(ArrayList<Patron> instancias) {
        this.instancias = instancias;
        this.clases = new ArrayList<>();
        this.matriz = null;
        inicializarMatriz();
    }

    private void inicializarMatriz() {
       for(Patron p: this.instancias){
           if(!this.clases.contains(p.getClase())){
                this.clases.add(p.getClase());
           }
       }
     
       int m = this.clases.size();
       this.matriz = new double[m][m+1];
       // recorremos las instancias nuevamente
       for(Patron p: this.instancias){
           int r =this.clases.indexOf(p.getClase());
           int c =this.clases.indexOf(p.getClaseResultante()) ;
           this.matriz[r][c]++;
       }
       
      
    }

       
    public void generartabla(String nombre){
    

//array bidimencional de objetos con los datos de la tabla
Object[][] data = new Object[this.clases.size()+1][this.clases.size()+1];


 String aux = "Hola ";
        for(int r=0;r<this.matriz.length;r++){
              data[r][0]=this.clases.get(r);
            for(int c=0;c<this.matriz.length;c++){
             aux=""+this.matriz[r][c];
                     data[r][c+1]=aux;

             }
        }
String[] columnNames = new String[this.clases.size()+1];
columnNames[0]="Tabla: "+nombre;
for(int i=1;i<=this.clases.size();i++){
     columnNames[i] = this.clases.get(i-1);
}

//creamos el modelo de tabla con los datos anteriores
DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
//se crea la tabla con el defaultablemodel
JTable tabla = new JTable(dtm);


//se define el tamaño
tabla.setPreferredScrollableViewportSize(new Dimension(500, 70));

//Creamos un JscrollPane y le agregamos la JTable
JScrollPane scrollPane = new JScrollPane(tabla);

//Agregamos el JScrollPane al contenedor
getContentPane().add(scrollPane, BorderLayout.CENTER);

//manejamos la salida
addWindowListener(new WindowAdapter() {
public void windowClosing(WindowEvent e) {
System.exit(0);
}
});
}
  }




