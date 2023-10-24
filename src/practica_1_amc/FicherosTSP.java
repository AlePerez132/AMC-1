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

    /*
    public static Punto[] LeerFichero(String fichero) 
    {
        Punto[] puntos = null;
        int i = 0;
        try 
        {
            BufferedReader lectura = new BufferedReader(new FileReader(fichero));
            String line;
            boolean cordSection = false; //Estara a true si se encuentra en la parte del fichero
                                         //en las que esten las coordenadas.
            while ((line = lectura.readLine()) != null) 
            {
                if (!line.equals("EOF") && !line.equals("")) 
                {
                    if (cordSection) //Si estamos en la zona de las coordenadas, las guardamos en un array Punto
                    {
                        String[] parts = line.split(" ");
                        puntos[i++] = new Punto(Double.parseDouble(parts[1].trim()), Double.parseDouble(parts[2].trim()));
                    } 
                    else 
                    {
                        if (line.equals("NODE_COORD_SECTION"))//Despues de esta linea vienen las coordenadas
                            cordSection = true;
                        
                        else if (line.contains("DIMENSION"))//Si esta en la linea de dimension, indica cuantos puntos hay en el fichero
                        {
                            String[] parts = line.split(":");
                            puntos = new Punto[Integer.parseInt(parts[1].trim())];
                        }
                    }
                }
            }
            lectura.close();
            return puntos;
        } 
        catch (FileNotFoundException ex) 
        {
            ex.printStackTrace();
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        return puntos;
    }
    
    
    public static void guardarTour(String archivo, Punto[] puntos, int solucion, String[] caminoMin, int[] costes) 
    {
        try 
        {
            Path path = Paths.get(archivo);
            BufferedWriter escribir = new BufferedWriter(new FileWriter(path.toFile()));
            escribir.write("NAME : " + path.getFileName() + "\n");
            escribir.write("TYPE : TOUR\n");
            escribir.write("DIMENSION : " + puntos.length + "\n");
            escribir.write("SOLUTION : " + solucion + "\n");
            escribir.write("TOUR_SECTION\n");
            
            for (int i =1; i<caminoMin.length; i++) //Escribimos el coste y el caminoMin de cada punto
                escribir.write(costes[i] + " - " + caminoMin[i] + "\n"); //Coloca en el fichero el coste del punto iesimo y su camino

            escribir.write("-1\nEOF\n");
            escribir.close();
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }
     */
}
