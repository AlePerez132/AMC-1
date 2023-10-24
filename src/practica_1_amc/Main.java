/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_1_amc;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            clearScreen();  // Limpia la pantalla antes de mostrar el menú
            System.out.println("Menú Principal:");
            System.out.println("1. Crear fichero TSP aleatorio:");
            System.out.println("2. Cargar un .TSP en memoria:");
            System.out.println("3. Comparar todas las estrategias:");
            System.out.println("4. Comparar 2 estrategias");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = scanner.nextInt();
                switch (opcion) {
                    case 1: {
                        //Crear fichero TSP aleatorio
                        System.out.println("Opción 1:");
                        System.out.println("\t" + "Cuántos puntos va a tener el archivo?");
                        int cantidadDePuntos = scanner.nextInt();
                        FicherosTSP.crearFicheroTSP(cantidadDePuntos);
                        System.out.println("Archivo guardado con éxito");
                    }
                    break;
                    case 2: {
                        //Comprobar un fichero TSP en concreto y mostrar todas las estrategias con ese fichero
                        System.out.println("Opción 2:");
                        System.out.println("\t" + "Ruta/Nombre del archivo a comprobar:");
                        scanner.nextLine();
                        String nombreArchivo = scanner.nextLine();
                        System.out.println(nombreArchivo);

                        System.out.println(String.format("%-20s %-45s %-45s %-25s %-15s %-15s", "Exhaustivo", "Punto1", "Punto2", "Distancia final", "Calculadas", "Tiempo (mseg)"));
                        ArrayList<Punto> listaPuntos = FicherosTSP.cargarPuntos(nombreArchivo);
                        //Al tener que devolver varios tipos distintos, la solucion mas facil que encontre fue crear una clase nueva
                        //que contenga esos tipos
                        ResultadoAlgoritmo ra1, ra2, ra3, ra4;
                        ra1 = new ResultadoAlgoritmo(Algoritmos.exhaustivo(listaPuntos));
                        ra2 = new ResultadoAlgoritmo(Algoritmos.exhaustivoPoda(listaPuntos));
                        ra3 = new ResultadoAlgoritmo(Algoritmos.divideYVenceras(listaPuntos));
                        ra4 = new ResultadoAlgoritmo(Algoritmos.divideYVencerasMejorado(listaPuntos));

                        System.out.println(String.format("%-20s %-45s %-45s %-25s %-15s %-15s", "Exhaustivo", ra1.getSeg().getP1(), ra1.getSeg().getP2(), ra1.getDistanciaFinal(), ra1.getDistanciasCalculadas(), ra1.getTiempoEjecucion()));
                        System.out.println(String.format("%-20s %-45s %-45s %-25s %-15s %-15s", "ExhaustivoPoda", ra2.getSeg().getP1(), ra2.getSeg().getP2(), ra2.getDistanciaFinal(), ra2.getDistanciasCalculadas(), ra2.getTiempoEjecucion()));
                        System.out.println(String.format("%-20s %-45s %-45s %-25s %-15s %-15s", "DivideYVenceras", ra3.getSeg().getP1(), ra3.getSeg().getP2(), ra3.getDistanciaFinal(), ra3.getDistanciasCalculadas(), ra3.getTiempoEjecucion()));
                        System.out.println(String.format("%-20s %-45s %-45s %-25s %-15s %-15s", "DyVM", ra4.getSeg().getP1(), ra4.getSeg().getP2(), ra4.getDistanciaFinal(), ra4.getDistanciasCalculadas(), ra4.getTiempoEjecucion()));
                    }
                    break;
                    case 3: {
                        //Comparar todas las estrategias
                        System.out.println("Opción 3:");
                        System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s", "", "Exhaustivo", "ExhasutivoPoda", "DivideYVenceras", "DyV Mejorado"));
                        System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)", "Tiempo(mseg)", "Tiempo (mseg)"));
                        for (int i = 500; i <= 5000; i += 500) {
                            ResultadoAlgoritmo ra1, ra2, ra3, ra4;
                            ra1 = new ResultadoAlgoritmo(Algoritmos.exhaustivo(Algoritmos.GenerarPuntosAleatorios(i)));
                            ra2 = new ResultadoAlgoritmo(Algoritmos.exhaustivoPoda(Algoritmos.GenerarPuntosAleatorios(i)));
                            ra3 = new ResultadoAlgoritmo(Algoritmos.divideYVenceras(Algoritmos.GenerarPuntosAleatorios(i)));
                            ra4 = new ResultadoAlgoritmo(Algoritmos.divideYVencerasMejorado(Algoritmos.GenerarPuntosAleatorios(i)));
                            System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s", i, ra1.getTiempoEjecucion(), ra2.getTiempoEjecucion(), ra3.getTiempoEjecucion(), ra4.getTiempoEjecucion()));
                        }
                    }
                    break;
                    case 4: {
                        //Comparar 2 estrategias
                        menuComparar2();
                    }
                    break;

                    case 0:
                        // Opción para salir
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Introduce un valor válido.");
                }
                if (opcion != 0) {
                    System.out.print("Presiona Enter para continuar...");
                    scanner.nextLine();  // Limpia el búfer
                    scanner.nextLine();  // Espera a que el usuario presione Enter
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Ingresa un número válido.");
                scanner.nextLine();  // Limpia el búfer
                opcion = -1;
            }
        } while (opcion != 0);

        scanner.close();
    } // Función para limpiar la pantalla

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void menuComparar2() {
        System.out.println("Opción 4:");
        Scanner scanner = new Scanner(System.in);
        int subOpcion1, subOpcion2 = -1;
        do {
            clearScreen();  // Limpia la pantalla antes de mostrar el menú
            System.out.println("Menú Comparar 2:");
            System.out.println("1. Exhaustivo");
            System.out.println("2. Exhaustivo con Poda");
            System.out.println("3. Divide y Venceras");
            System.out.println("4. Divide y Venceras Mejorado");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            try {
                subOpcion1 = scanner.nextInt();
                if (subOpcion1 != 0) {
                    System.out.println("Elige el segundo algoritmo");
                    subOpcion2 = scanner.nextInt();
                    switch (subOpcion1) {
                        case 1: {
                            switch (subOpcion2) {
                                case 2: {
                                    ResultadoAlgoritmo ra1, ra2;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "Exhaustivo", "ExhasutivoPoda"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra1 = new ResultadoAlgoritmo(Algoritmos.exhaustivo(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra2 = new ResultadoAlgoritmo(Algoritmos.exhaustivoPoda(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra1.getTiempoEjecucion(), ra2.getTiempoEjecucion()));
                                    }
                                    break;
                                }
                                case 3: {
                                    ResultadoAlgoritmo ra1, ra3;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "Exhaustivo", "Divide y Venceras"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra1 = new ResultadoAlgoritmo(Algoritmos.exhaustivo(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra3 = new ResultadoAlgoritmo(Algoritmos.divideYVenceras(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra1.getTiempoEjecucion(), ra3.getTiempoEjecucion()));
                                    }
                                    break;
                                }
                                case 4: {
                                    ResultadoAlgoritmo ra1, ra4;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "Exhaustivo", "DyVM"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra1 = new ResultadoAlgoritmo(Algoritmos.exhaustivo(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra4 = new ResultadoAlgoritmo(Algoritmos.divideYVencerasMejorado(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra1.getTiempoEjecucion(), ra4.getTiempoEjecucion()));
                                    }
                                    break;
                                }
                                default:
                                    System.out.println("Opcion incorrecta");
                            }
                            break;
                        }
                        case 2: {
                            switch (subOpcion2) {
                                case 1: {
                                    ResultadoAlgoritmo ra1, ra2;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "Exhaustivo", "ExhasutivoPoda"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra1 = new ResultadoAlgoritmo(Algoritmos.exhaustivo(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra2 = new ResultadoAlgoritmo(Algoritmos.exhaustivoPoda(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra1.getTiempoEjecucion(), ra2.getTiempoEjecucion()));
                                    }
                                    break;
                                }
                                case 3: {
                                    ResultadoAlgoritmo ra2, ra3;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "ExhaustivoPoda", "Divide y Venceras"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra2 = new ResultadoAlgoritmo(Algoritmos.exhaustivoPoda(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra3 = new ResultadoAlgoritmo(Algoritmos.divideYVencerasMejorado(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra2.getTiempoEjecucion(), ra3.getTiempoEjecucion()));
                                    }
                                    break;
                                }
                                case 4: {
                                    ResultadoAlgoritmo ra2, ra4;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "ExhaustivoPoda", "DyVM"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra2 = new ResultadoAlgoritmo(Algoritmos.exhaustivoPoda(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra4 = new ResultadoAlgoritmo(Algoritmos.divideYVencerasMejorado(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra2.getTiempoEjecucion(), ra4.getTiempoEjecucion()));
                                    }
                                    break;
                                }
                                default:
                                    System.out.println("Opcion incorrecta");
                            }
                            break;
                        }
                        case 3: {
                            switch (subOpcion2) {
                                case 1: {
                                    ResultadoAlgoritmo ra1, ra3;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "Exhaustivo", "Divide y Venceras"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra1 = new ResultadoAlgoritmo(Algoritmos.exhaustivo(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra3 = new ResultadoAlgoritmo(Algoritmos.divideYVenceras(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra1.getTiempoEjecucion(), ra3.getTiempoEjecucion()));
                                    }
                                    break;
                                }
                                case 2: {
                                    ResultadoAlgoritmo ra2, ra3;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "ExhaustivoPoda", "Divide y Venceras"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra2 = new ResultadoAlgoritmo(Algoritmos.exhaustivoPoda(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra3 = new ResultadoAlgoritmo(Algoritmos.divideYVenceras(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra2.getTiempoEjecucion(), ra3.getTiempoEjecucion()));
                                    }
                                    break;
                                }
                                case 4: {
                                    ResultadoAlgoritmo ra3, ra4;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "Divide y Venceras", "DyVM"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra3 = new ResultadoAlgoritmo(Algoritmos.divideYVenceras(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra4 = new ResultadoAlgoritmo(Algoritmos.divideYVencerasMejorado(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra3.getTiempoEjecucion(), ra4.getTiempoEjecucion()));
                                    }
                                    break;                                }
                                default:
                                    System.out.println("Opcion incorrecta");
                            }
                            break;
                        }
                        case 4: {
                            switch (subOpcion2) {
                                case 1: {
                                    ResultadoAlgoritmo ra1, ra4;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "Exhaustivo", "DyVM"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra1 = new ResultadoAlgoritmo(Algoritmos.exhaustivo(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra4 = new ResultadoAlgoritmo(Algoritmos.divideYVencerasMejorado(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra1.getTiempoEjecucion(), ra4.getTiempoEjecucion()));
                                    }
                                    break;

                                }
                                case 2: {
                                    ResultadoAlgoritmo ra2, ra4;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "ExhaustivoPoda", "DyVM"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra2 = new ResultadoAlgoritmo(Algoritmos.exhaustivoPoda(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra4 = new ResultadoAlgoritmo(Algoritmos.divideYVencerasMejorado(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra2.getTiempoEjecucion(), ra4.getTiempoEjecucion()));
                                    }
                                    break;
                                }
                                case 3: {
                                    ResultadoAlgoritmo ra3, ra4;
                                    System.out.println(String.format("%-20s %-20s %-20s", "", "Divide y Venceras", "DyVM"));
                                    System.out.println(String.format("%-20s %-20s %-20s", "Talla", "Tiempo(mseg)", "Tiempo(mseg)"));
                                    for (int i = 500; i <= 5000; i += 500) {
                                        ra3 = new ResultadoAlgoritmo(Algoritmos.divideYVenceras(Algoritmos.GenerarPuntosAleatorios(i)));
                                        ra4 = new ResultadoAlgoritmo(Algoritmos.divideYVencerasMejorado(Algoritmos.GenerarPuntosAleatorios(i)));
                                        System.out.println(String.format("%-20s %-20s %-20s", i, ra3.getTiempoEjecucion(), ra4.getTiempoEjecucion()));
                                    }
                                    break;
                                }
                                default:
                                    System.out.println("Opcion incorrecta");
                            }
                            break;
                        }
                        case 0:
                            System.out.println("Saliendo del menu...");
                        default:
                            System.out.println("Opción no válida");
                    }
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Ingresa un número válido.");
                scanner.nextLine();  // Limpia el búfer
                subOpcion1 = -1;
                subOpcion2 = -1;
            }
        } while (subOpcion1 != 0 && subOpcion2 != 0);
    }

    /*
    public  static void main(){
        Scanner sc = new Scanner(System.in);
        String opcionPrincipal, op1, op2, op11, op12;
        String[] caminoMin;
        int[][] matrizAd;
        String fichero;
        int sumaTotal = 0;
                
        System.out.print("Desea ejecutar la parte 1 o la parte 2 de la practica: " );
        opcionPrincipal = sc.nextLine();
        switch(opcionPrincipal)
        {
            case "1":
                    try { // ponemos estilo nimbus
            for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
        Vista v= new Vista();
        v.setVisible(true);

            break;
            
            case "2": //Parte 2 de la practica
                System.out.print("\nDesea abrir un archivo ya existente(1), crear puntos aleatorios(2) o realizar un estudio empirico(3): ");
                op2 = sc.nextLine();
                switch(op2)
                {
                    case "1": //Abrir un archibo tsp
                        Punto[] puntos = null;
                        Scanner scan = new Scanner(System.in);
                        System.out.print("\nEscribe el nombre del archivo: ");
                        fichero = scan.nextLine();
                        String ruta = "./src/DataSet/"+ fichero + ".tsp" + "/" + fichero + ".tsp";
                        puntos = FicherosTSP.LeerFichero(ruta); //Asigamos al array P los puntos del fichero seleccionado
                        
                        caminoMin= new String[puntos.length];
                        
                        String op21;
                        matrizAd = Algoritmos.generarMatrizDeAdyacencia(puntos);
                        
                        int[] D = Algoritmos.algoritmoDijkstra(matrizAd, caminoMin);
          
                        System.out.print("\nAplicando Dijkstra...");
                        System.out.print("\nLa solucion es: {");
                        for(int i = 1; i<D.length; i++)
                        {
                            System.out.print(D[i] + ", ");
                            sumaTotal = sumaTotal + D[i];
                        }
                        System.out.println("}");
                        System.out.println("\nEl total es: " + sumaTotal);
                        
                        for(int i=1; i< puntos.length; i++)
                        {
                            System.out.println(caminoMin[i]);
                        }
                        
                        System.out.print("Desea guardar el resultado(s),(n): ");
                        op21 = sc.nextLine();
                        switch(op21)
                        {
                            case "s":
                                String rutaDeGuardado = "./src/DataSet/" + fichero + ".opt.tour";
                                System.out.println("\nEl fichero se guardara en: " + rutaDeGuardado + "\n");
                                FicherosTSP.guardarTour(rutaDeGuardado, puntos, sumaTotal, caminoMin, D);
                                break;
                    
                            default:
                                System.out.println("El resultado no se guardara...\n");
                                break;
                        }
                        break;
                        
                    case "2": //Generar puntos aleatorios
                        int n=0;
                        System.out.print("\nCuantos puntos quieres generar: ");
                        n = sc.nextInt();
                        Punto[] p = Algoritmos.GenerarPuntosAleatorios(n);
                        caminoMin= new String[n];
                        
                        String op22;
                        Scanner scaner = new Scanner(System.in);
                        matrizAd = Algoritmos.generarMatrizDeAdyacencia(p);
                        
                        int[] E = Algoritmos.algoritmoDijkstra(matrizAd, caminoMin);
          
                        System.out.print("\nAplicando Dijkstra...");
                        System.out.print("\nLa solucion es: {");
                        for(int i = 1; i<E.length; i++)
                        {
                            System.out.print(E[i] + ", ");
                            sumaTotal = sumaTotal + E[i];
                        }
                        System.out.println("}");
                        System.out.println("\nEl total es: " + sumaTotal);
                        
                        for(int i=1; i< p.length; i++)
                            System.out.println(caminoMin[i]);
                        
                        System.out.print("Desea guardar el resultado(s),(n): ");
                        op22 = scaner.nextLine();
                        switch(op22)
                        {
                            case "s":
                                String fich;
                                Scanner escan = new Scanner(System.in);
                                System.out.print("\nIndica el nombre del fichero: ");
                                fich = escan.nextLine();
                                String rutaDeGuardado = "./src/DataSet/" + fich + ".opt.tour";
                                System.out.println("\nEl fichero se guardara en: " + rutaDeGuardado + "\n");
                                FicherosTSP.guardarTour(rutaDeGuardado, p, sumaTotal, caminoMin, E);
                                break;
                    
                            default:
                                System.out.println("El resultado no se guardara...\n");
                                break;
                        }
                        break;
                        
                    case "3": //Estudio empirico
                        int TallaIni = 200;
                        int TallaFin = 5000; //hace falta 17 ejecuciones para llegar a 5000 partiendo de 200 con un incremento de 300
                        int Incremento = 300;
                        long comienzo, fin = 0;
                        double media;
                        
                        System.out.println("\n\tDijkstra");
                        for(int i = TallaIni; i<=TallaFin; i+=Incremento)
                        {
                            for(int j = 0; j < 10; j++) //10 ejecuciones para cada talla
                            {
                                Punto[] pRandom = Algoritmos.GenerarPuntosAleatorios(i);
                                caminoMin= new String[pRandom.length];
                                matrizAd = Algoritmos.generarMatrizDeAdyacencia(pRandom);
                        
                                comienzo = System.nanoTime();
                                int[] solucion = Algoritmos.algoritmoDijkstra(matrizAd, caminoMin);
                                fin += (System.nanoTime() - comienzo);
                            }
                            media = (double)fin/10;
                            System.out.println(i + "\t" + media);
                        }
                        
                        break;
                }
                break;
        }
    }
     */
}
