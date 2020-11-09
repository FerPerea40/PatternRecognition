/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rp20201a;

import clasificadores.MinimaDistancia;
import data.Patron;
import data.LeerDatos;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author working
 */
public class RP2021A {

    public static void main(String[] args) throws IOException {

        ArrayList<Patron> patrones = LeerDatos.tokenizarDataSet();
        MinimaDistancia mn = new MinimaDistancia();
        mn.entrenar(patrones);

        ArrayList<Patron> patrones2 = LeerDatos.tokenizarDataSet();

        for (int i = 0; i < patrones2.size(); i++) {
            mn.clasificar(patrones2.get(i));
        }
        for (int i = 0; i < patrones2.size(); i++) {
            System.out.println("Elemneto: " + (i+1) + " -> Clase: " + patrones2.get(i).getClase() + " -> Clase Resultante: " + patrones2.get(i).getClaseResultante());
        }

        System.out.println("Eficacia: " + mn.eficacia(patrones2) + "222");

    }

}
