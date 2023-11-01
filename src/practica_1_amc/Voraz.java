/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica_1_amc;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author alepd
 */
public class Voraz {

    public static ArrayList<Punto> unidireccionalTSP(ArrayList<Punto> ciudades) {
        int n = ciudades.size();
        boolean[] visitadas = new boolean[n];
        ArrayList<Punto> ruta = new ArrayList<>();
        Random rand = new Random(System.currentTimeMillis());

        int ciudadInicial = rand.nextInt(ciudades.size());
        ruta.add(ciudades.get(ciudadInicial));
        visitadas[ciudadInicial] = true;
        int ciudadMasCercana = ciudadInicial;

        for (int i = 0; i < n - 1; i++) {
            Punto puntoActual = ciudades.get(ciudadMasCercana);
            double distanciaMinima = Double.MAX_VALUE;
            ciudadMasCercana = -1;

            for (int j = 0; j < n; j++) {
                if (!visitadas[j]) {
                    Segmento seg = new Segmento(puntoActual, ciudades.get(j));
                    double d = seg.distancia();
                    if (d < distanciaMinima) {
                        distanciaMinima = d;
                        ciudadMasCercana = j;
                    }
                }
            }

            ruta.add(ciudades.get(ciudadMasCercana));
            visitadas[ciudadMasCercana] = true;
        }

        // Regresar a la ciudad de origen para completar el ciclo
        ruta.add(ciudades.get(ciudadInicial));

        return ruta;
    }

    public static ArrayList<Punto> bidireccionalTSP(ArrayList<Punto> puntos) {
        int n = puntos.size();
        ArrayList<Punto> ruta = new ArrayList<>();
        boolean[] visitados = new boolean[n];
        Random rand = new Random(System.currentTimeMillis());
        
        int puntoInicial = rand.nextInt(n);
        ruta.add(puntos.get(puntoInicial));
        visitados[puntoInicial] = true;

        while (ruta.size() < n) {
            for (int i = 0; i < ruta.size(); i++) {
                Punto puntoActual = ruta.get(i);
                double distanciaMinima = Double.MAX_VALUE;
                int puntoMasCercano = -1;
                for (int j = 0; j < n; j++) {
                    if (!visitados[i]) {
                        Punto puntoDestino = puntos.get(i);
                        Segmento seg = new Segmento(puntoActual, puntoDestino);
                        double distancia = seg.distancia();
                        if (distancia < distanciaMinima) {
                            distanciaMinima = distancia;
                            puntoMasCercano = i;
                        }
                    }
                }

                ruta.add(puntos.get(puntoMasCercano));
                visitados[puntoMasCercano] = true;
            }
        }

        return ruta;
    }
}
