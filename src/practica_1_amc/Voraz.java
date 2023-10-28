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

    /*
    //Metodo que uso para pasar de una lista de puntos a una lista de Nodos con sus Aristas
    public static ArrayList<Nodo> leerPuntos(ArrayList<Punto> puntos) {
        Nodo nodo;
        Arista aris;
        Segmento segm;
        ArrayList<Nodo> listaNodos = new ArrayList<>();
        ArrayList<Arista> listaAristas = null;
        for (int i = 0; i < puntos.size(); i++) {
            listaAristas = new ArrayList<>();
            for (int j = 0; j < puntos.size(); j++) {
                if (i != j) {
                    segm = new Segmento(puntos.get(i), puntos.get(j));
                    aris = new Arista(puntos.get(j), segm.distancia());
                    listaAristas.add(aris);
                }
            }
            nodo = new Nodo(puntos.get(i), listaAristas);
            listaNodos.add(nodo);
        }
        return listaNodos;
    }
    
    public static ArrayList<Nodo> Unidireccional(ArrayList<Nodo> listaNodos){
        
        boolean[] visitados = new boolean[listaNodos.size()];
        for(int i = 0; i < visitados.length; i++){
            visitados[i] = false;
        }
        
        for(int i = 0; i < listaNodos.size(); i++){
            Nodo nodoActual = new Nodo(listaNodos.get(i));
            Nodo nodoElegido = null;
            double dMin = Double.MAX_VALUE;
            for(int j = 0; j < nodoActual.aristas.size(); j++){
                if(nodoActual.aristas.get(j).etiqueta < dMin && !visitados[j]){
                    dMin = nodoActual.aristas.get(j).etiqueta;
                    Punto pAux = new Punto(nodoActual.aristas.get(j).destino); 
                    for()
                }
            }
        }
    }
    */
}
