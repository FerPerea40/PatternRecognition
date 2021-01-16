/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import data.VectorBinario;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class lernMatrix {

    double[][] matriz;
    VectorBinario vB;
    int tamx, tamy;
    int sobran;

    public lernMatrix() {
    }

    public lernMatrix(VectorBinario vB) {
        this.vB = vB;
        this.sobran = vB.getX().get(0).length - vB.getY().get(0).length;
        if (this.sobran != 0) {
            for (int i = 0; i < this.sobran; i++) {
                this.vB.getY().add(this.vB.getY().get(0));
            }
        }

        this.tamx = this.vB.getX().get(0).length;
        this.tamy = this.vB.getY().get(0).length;
        this.matriz = new double[tamy][tamx];

    }

    public void aprendizaje() {

        for (int i = 0; i < tamx; i++) {
            for (int j = 0; j < tamy; j++) {
                matriz[j][i] = 0;
            }
        }

        int i = 0;
        while (i < vB.getX().size()) {
            for (int y = 0; y < vB.getY().get(i).length; y++) {
                for (int x = 0; x < vB.getX().get(i).length; x++) {
                    if (vB.getX().get(i)[x] == 1 && vB.getY().get(i)[y] == 1) {
                        matriz[y][x] += 1;
                    } else if (vB.getX().get(i)[x] == 0 && vB.getY().get(i)[y] == 1) {
                        matriz[y][x] += -1;
                    } else {
                        matriz[y][x] += 0;
                    }

                }
            }
            i++;
        }

        System.out.println("LernMatrix:");
        for (int z = 0; z < tamy; z++) {
            for (int y = 0; y < tamx; y++) {
                System.out.print("|\t" + (int)matriz[z][y] + "\t");
            }
            System.out.println();
        }

    }

    public void recuperacion(double[] Consulta) {
        double[] Salida = new double[tamy];
        for (int y = 0; y < tamy; y++) {
            Salida[y] = 0;
        }

        for (int z = 0; z < tamy; z++) {
            for (int y = 0; y < tamx; y++) {
                Salida[z] += matriz[z][y] * Consulta[y];
            }
        }

        double mayor = 0;

        for (int y = 0; y < tamy; y++) {
            if (mayor < Salida[y]) {
                mayor = Salida[y];
            }
        }
        for (int y = 0; y < tamy; y++) {
            if (mayor == Salida[y]) {
                Salida[y] = 1;
            } else {
                Salida[y] = 0;

            }
        }
        System.out.println("La salida es: ");
        for (int y = 0; y < tamy; y++) {
            System.out.println((int)Salida[y]);
        }

    }

}
