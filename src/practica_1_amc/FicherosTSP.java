/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_1_amc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FicherosTSP {

    public static ArrayList<Punto> cargarPuntos(String nombreArchivo) {

        ArrayList<Punto> ListaPuntos = new ArrayList<>();

        try ( BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {

            String line;
            boolean leePuntos = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("DIMENSION")) {
                    int tamanio = Integer.parseInt(line.split(":")[1].trim());
                    ListaPuntos = new ArrayList<>(tamanio); //Se reserva el array para los puntos.
                } else if (line.equals("NODE_COORD_SECTION")) { //A partir de aquí se comienzan a almacenar los puntos
                    leePuntos = true;
                } else if (leePuntos && !line.isEmpty() && !line.equals("EOF")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3) {
                        int id = Integer.parseInt(parts[0]);
                        double x = Float.parseFloat(parts[1]);
                        double y = Float.parseFloat(parts[2]);
                        ListaPuntos.add(new Punto(id, x, y)); //Añadirá al array de puntos los puntos con las coordenadas de las posiciones 1 y 2 (x e y) 
                    } else {
                        System.err.println("Error en el formato de la línea: " + line);
                    }
                } else if (line.equals("EOF")) {
                    break; //Deja de leer puntos, ya que EOF indica el final del fichero
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ListaPuntos;
    }

    public static void crearFicheroTSP(int cantidadDePuntos) {
        Random random = new Random();
        double maxX = 5000.0;
        double maxY = 5000.0;
        ArrayList<Punto> puntos = new ArrayList<>();

        //Lo hago asi porque me aseguro no tener puntos duplicados
        while (puntos.size() < cantidadDePuntos) {
            double x = random.nextDouble() * maxX;
            double y = random.nextDouble() * maxY;
            Punto nuevoPunto = new Punto(x, y);

            // Verificar si el punto ya existe en la lista
            boolean puntoDuplicado = false;
            for (Punto puntoExistente : puntos) {
                if (puntoExistente.equals(nuevoPunto)) {
                    puntoDuplicado = true;
                    break;
                }
            }

            if (!puntoDuplicado) {
                puntos.add(nuevoPunto);
            }
        }

        String fileName = "dataset" + cantidadDePuntos + ".tsp";

        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("NAME: " + fileName + "\n");
            writer.write("TYPE: TSP\n");
            writer.write("COMMENT: " + cantidadDePuntos + " random locations\n");
            writer.write("DIMENSION: " + cantidadDePuntos + "\n");
            writer.write("EDGE_WEIGHT_TYPE: EUC_2D\n");
            writer.write("NODE_COORD_SECTION\n");

            for (int i = 0; i < cantidadDePuntos; i++) {
                // Formatea y reemplaza las comas por puntos en las coordenadas
                String formattedX = String.format("%.10f", puntos.get(i).getX()).replace(',', '.');
                String formattedY = String.format("%.10f", puntos.get(i).getY()).replace(',', '.');

                // Escribe las coordenadas en el archivo
                writer.write((i + 1) + " " + formattedX + " " + formattedY + "\n");
            }

            writer.write("EOF\n");
            writer.close();
            System.out.println("Archivo " + fileName + " creado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void crearFicheroSolucionTour(ArrayList<Punto> solucion, String nombreArchivo){
        try {
            FileWriter archivo = new FileWriter(nombreArchivo); // Abre el archivo para escritura
            BufferedWriter escritor = new BufferedWriter(archivo); // Prepara un escritor de texto

            double recorridoTotal = 0;
            for (int i = 0; i < solucion.size() - 1 ; i++) {
                double distanciaAux;
                Segmento segAux = new Segmento(solucion.get(i), solucion .get(i+1));
                distanciaAux = segAux.distancia();
                recorridoTotal+=distanciaAux;
            }

            // Encabezado del archivo
            escritor.write("NAME : " + nombreArchivo);
            escritor.newLine(); // Salto de línea para pasar a la siguiente línea
            escritor.write("TYPE : TOUR");
            escritor.newLine();
            escritor.write("DIMENSION : " + solucion.size()); // Obtiene la cantidad de nodos en la solución
            escritor.newLine();
            escritor.write("SOLUTION: " + recorridoTotal);
            escritor.newLine();
            escritor.write("TOUR_SECTION"); // Encabezado de la sección de la solución
            escritor.newLine();
            
            // Escribir la lista de nodos en la solución
            for (int i = 0; i < solucion.size(); i++) {
                escritor.write(Integer.toString(solucion.get(i).getId())); // Accede al número de nodo
                if (i < solucion.size() - 1) {
                    escritor.write(",");
                } else {
                    escritor.newLine(); // Salto de línea al final de la lista de nodos
                }
            }

            // Suponiendo que tienes un arreglo de distancias para cada par de nodos en "distancias"
            for (int i = 0; i < solucion.size() - 1; i++) {
                Segmento segAux = new Segmento(solucion.get(i), solucion.get(i+1));
                escritor.write(Double.toString(segAux.distancia()) + " - ");
                escritor.write(Integer.toString(segAux.getP1().getId()) + ", " + Integer.toString(segAux.getP2().getId()));
                escritor.newLine();
            }

            escritor.write("EOF"); // Marca el final del archivo

            escritor.close(); // Cierra el archivo después de escribir
            System.out.println("El archivo " + nombreArchivo + " se ha creado con éxito.");

        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores de E/S
        }
        
    }

}
