/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_1_amc;

import java.util.ArrayList;
import java.util.Comparator;

public class Algoritmos {
    //Las divisiones entre 1000000 en los returns es para que el resultado en milisegundos y no en nanosegundos

    //Metodo para generar puntos aleatorios
    public static ArrayList<Punto> GenerarPuntosAleatorios(int n) {
        ArrayList<Punto> puntos = new ArrayList<Punto>(n);
        for (int i = 0; i < n; i++) {
            puntos.add(i, new Punto(Math.random() * 3000, Math.random() * 3000)); //Valor aleatorio entre 0 y 3000
        }
        return puntos;
    }

    public static void quickSortX(ArrayList<Punto> puntos, int izquierda, int derecha) {
        if (izquierda < derecha) {
            int indiceParticion = particionX(puntos, izquierda, derecha);
            quickSortX(puntos, izquierda, indiceParticion - 1);
            quickSortX(puntos, indiceParticion + 1, derecha);
        }
    }

    public static int particionX(ArrayList<Punto> puntos, int izquierda, int derecha) {
        double pivote = puntos.get(derecha).getX();
        int indiceMenor = izquierda - 1;

        for (int i = izquierda; i < derecha; i++) {
            if (puntos.get(i).getX() < pivote) {
                indiceMenor++;
                intercambiar(puntos, indiceMenor, i);
            }
        }

        intercambiar(puntos, indiceMenor + 1, derecha);
        return indiceMenor + 1;
    }
    
    public static void quickSortY(ArrayList<Punto> puntos, int izquierda, int derecha) {
        if (izquierda < derecha) {
            int indiceParticion = particionY(puntos, izquierda, derecha);
            quickSortY(puntos, izquierda, indiceParticion - 1);
            quickSortY(puntos, indiceParticion + 1, derecha);
        }
    }

    public static int particionY(ArrayList<Punto> puntos, int izquierda, int derecha) {
        double pivote = puntos.get(derecha).getY();
        int indiceMenor = izquierda - 1;

        for (int i = izquierda; i < derecha; i++) {
            if (puntos.get(i).getY() < pivote) {
                indiceMenor++;
                intercambiar(puntos, indiceMenor, i);
            }
        }

        intercambiar(puntos, indiceMenor + 1, derecha);
        return indiceMenor + 1;
    }

    public static void intercambiar(ArrayList<Punto> puntos, int i, int j) {
        Punto temp = puntos.get(i);
        puntos.set(i, puntos.get(j));
        puntos.set(j, temp);
    }

    public static ResultadoAlgoritmo exhaustivo(ArrayList<Punto> lista) {
        int numeroDistanciasCalculadas = 0;
        double distancia = 0;
        long inicioEjecucion = System.nanoTime();
        double dMin = Double.MAX_VALUE;
        Segmento sol = new Segmento(lista.get(0), lista.get(1)), aux;
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                numeroDistanciasCalculadas++;
                aux = new Segmento(lista.get(i), lista.get(j));
                if (aux.distancia() < dMin) {
                    dMin = aux.distancia();
                    sol = new Segmento(aux);
                }
            }
        }
        distancia = dMin;
        long finEjecucion = System.nanoTime();
        long tiempoEjecucion = finEjecucion - inicioEjecucion;
        return new ResultadoAlgoritmo(sol, numeroDistanciasCalculadas, distancia, tiempoEjecucion);
    }

    public static ResultadoAlgoritmo exhaustivoPoda(ArrayList<Punto> lista) {
        try {
            quickSortX(lista, 0, lista.size() - 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return exhaustivoPodaOrdenado(lista);
    }

    public static ResultadoAlgoritmo exhaustivoPodaOrdenado(ArrayList<Punto> lista) {
        int numeroDistanciasCalculadas = 0;
        double distancia = 0;
        long inicioEjecucion = System.nanoTime();
        double dMin = Double.MAX_VALUE;
        Segmento sol = new Segmento(lista.get(0), lista.get(1)), aux;
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                //Aqui esta la diferencia con el exhaustivo normal
                double distanciaHorizontal = Math.abs(lista.get(i).getX() - lista.get(j).getX());
                if (distanciaHorizontal < dMin) { //Solo continuamos si la distancia horizontal entre los puntos es menor que la distancia minima que tenemos actualmente
                    numeroDistanciasCalculadas++;
                    aux = new Segmento(lista.get(i), lista.get(j));
                    if (aux.distancia() < dMin) {
                        dMin = aux.distancia();
                        sol = new Segmento(aux);
                    }
                }
            }
        }
        distancia = dMin;
        long finEjecucion = System.nanoTime();
        long tiempoEjecucion = finEjecucion - inicioEjecucion;
        return new ResultadoAlgoritmo(sol, numeroDistanciasCalculadas, distancia, tiempoEjecucion);
    }

    public static ResultadoAlgoritmo divideYVenceras(ArrayList<Punto> lista) {
        try {
            quickSortX(lista, 0, lista.size() - 1);
            ResultadoAlgoritmo ra;
            //La unica forma de utilizar un entero como parametro de entrada/salida es creando un array de 1 solo dato
            int[] distanciasCalculadas = new int[1];
            distanciasCalculadas[0] = 0;
            long tiempoInicio = System.nanoTime();
            Segmento s = dYV(lista, 0, lista.size() - 1, distanciasCalculadas);
            long tiempoFin = System.nanoTime();
            long tiempoEjecucion = tiempoFin - tiempoInicio;
            ra = new ResultadoAlgoritmo(s, distanciasCalculadas[0], s.distancia(), tiempoEjecucion);
            return ra;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static Segmento dYV(ArrayList<Punto> puntos, int izquierda, int derecha, int[] distanciasCalculadas) {
        Segmento segm = new Segmento(puntos.get(0), puntos.get(1));
        if (derecha - izquierda < 3) { //Caso base
            double dMin = Double.MAX_VALUE;
            double distancia = 0.0;
            for (int i = izquierda; i <= derecha; i++) {
                for (int j = i + 1; j <= derecha; j++) {
                    distanciasCalculadas[0]++; //La unica forma de poder pasar por referencia un entero es con un array de 1 solo elemento
                    Segmento aux = new Segmento(puntos.get(i), puntos.get(j));
                    distancia = aux.distancia();
                    if (distancia < dMin) {
                        dMin = distancia;
                        segm = new Segmento(aux);
                    }
                }
            }
            return segm;
        }

        int medio = ((derecha - izquierda) / 2) + izquierda;
        Punto puntoMedio = puntos.get(medio);

        //Llamadas recursivas tanto a la izquierda como a la derecha
        Segmento segIzq = new Segmento(dYV(puntos, izquierda, medio, distanciasCalculadas));
        Segmento segDcha = new Segmento(dYV(puntos, medio + 1, derecha, distanciasCalculadas));
        double dMin;

        //Miro si la solucion esta a la izquierda o a la derecha
        if (segIzq.distancia() < segDcha.distancia()) {
            dMin = segIzq.distancia();
            segm = new Segmento(segIzq);
        } else {
            dMin = segDcha.distancia();
            segm = new Segmento(segDcha);
        }

        //Recorro el array completo para ver cuales son los pares de puntos con una distancia X con respecto a puntoMedio
        //que sea menor a la dMin calculada anteriormente
        ArrayList<Punto> puntosEnRango;
        puntosEnRango = new ArrayList<>();
        for (int i = izquierda; i <= derecha; i++) {
            double valorAbsoluto = Math.abs(puntos.get(i).getX() - puntoMedio.getX());
            if (valorAbsoluto < dMin) {
                puntosEnRango.add(puntos.get(i));
            }
        }

        //Vuelvo a hacer un exhaustivo con los puntos en rango para ver si la solucion esta entre esos puntos
        for (int i = 0; i < puntosEnRango.size() - 1; i++) {
            for (int j = i + 1; j < puntosEnRango.size(); j++) {
                Segmento aux = new Segmento(puntosEnRango.get(i), puntosEnRango.get(j));
                if (aux.distancia() < dMin) {
                    dMin = aux.distancia();
                    segm = new Segmento(aux);
                }
            }
        }
        return segm;
    }

    public static ResultadoAlgoritmo divideYVencerasMejorado(ArrayList<Punto> lista) {
        try {
            quickSortX(lista, 0, lista.size() - 1);
            ResultadoAlgoritmo ra;
            //La unica forma de utilizar un entero como parametro de entrada/salida es creando un array de 1 solo dato
            int[] distanciasCalculadas = new int[1];
            distanciasCalculadas[0] = 0;
            long tiempoInicio = System.nanoTime();
            Segmento s = dYVM(lista, 0, lista.size() - 1, distanciasCalculadas);
            long tiempoFin = System.nanoTime();
            long tiempoEjecucion = tiempoFin - tiempoInicio;
            ra = new ResultadoAlgoritmo(s, distanciasCalculadas[0], s.distancia(), tiempoEjecucion);
            return ra;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static Segmento dYVM(ArrayList<Punto> puntos, int izquierda, int derecha, int[] distanciasCalculadas) {
        Segmento segm = new Segmento(puntos.get(0), puntos.get(1));
        if (derecha - izquierda < 3) { //Caso base
            double dMin = Double.MAX_VALUE;
            double distancia = 0.0;
            for (int i = izquierda; i <= derecha; i++) {
                for (int j = i + 1; j <= derecha; j++) {
                    distanciasCalculadas[0]++; //La unica forma de poder pasar por referencia un entero es con un array de 1 solo elemento
                    Segmento aux = new Segmento(puntos.get(i), puntos.get(j));
                    distancia = aux.distancia();
                    if (distancia < dMin) {
                        dMin = distancia;
                        segm = new Segmento(aux);
                    }
                }
            }
            return segm;
        }

        int medio = ((derecha - izquierda) / 2) + izquierda;
        Punto puntoMedio = puntos.get(medio);

        //Llamadas recursivas tanto a la izquierda como a la derecha
        Segmento segIzq = new Segmento(dYVM(puntos, izquierda, medio, distanciasCalculadas));
        Segmento segDcha = new Segmento(dYVM(puntos, medio + 1, derecha, distanciasCalculadas));
        double dMin;

        //Miro si la solucion esta a la izquierda o a la derecha
        if (segIzq.distancia() < segDcha.distancia()) {
            dMin = segIzq.distancia();
            segm = new Segmento(segIzq);
        } else {
            dMin = segDcha.distancia();
            segm = new Segmento(segDcha);
        }

        //Recorro el array completo para ver cuales son los pares de puntos con una distancia X con respecto a puntoMedio
        //que sea menor a la dMin calculada anteriormente
        ArrayList<Punto> puntosEnRango;
        puntosEnRango = new ArrayList<>();
        for (int i = izquierda; i <= derecha; i++) {
            double valorAbsoluto = Math.abs(puntos.get(i).getX() - puntoMedio.getX());
            if (valorAbsoluto < dMin) {
                puntosEnRango.add(puntos.get(i));
            }
        }
        
        //Ordeno los puntos que esten en rango segun la coordenada Y 
        quickSortY(puntosEnRango, 0, puntosEnRango.size()-1);
        //Recorro el array completo para ver cuales son los pares de puntos con una distancia Y con respecto a puntoMedio
        //que sea menor a la dMin calculada anteriormente
        ArrayList<Punto> puntosEnRangoY;
        puntosEnRangoY = new ArrayList<>();
        for (int i = 0; i < puntosEnRango.size(); i++) {
            double valorAbsoluto = Math.abs(puntosEnRango.get(i).getY() - puntoMedio.getY());
            if (valorAbsoluto < dMin) {
                puntosEnRangoY.add(puntosEnRango.get(i));
            }
        }

        //Vuelvo a hacer un exhaustivo con los puntos en rango para ver si la solucion esta entre esos puntos
        //Al haberlo ordenado, la cantidad de puntos que calculo aquí es máximo 15
        for (int i = 0; i < puntosEnRangoY.size() - 1; i++) {
            for (int j = i + 1; j < puntosEnRangoY.size(); j++) {
                Segmento aux = new Segmento(puntosEnRangoY.get(i), puntosEnRangoY.get(j));
                if (aux.distancia() < dMin) {
                    dMin = aux.distancia();
                    segm = new Segmento(aux);
                }
            }
        }
        return segm;
    }
}
