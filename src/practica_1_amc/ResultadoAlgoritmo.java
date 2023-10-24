/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica_1_amc;

/**
 *
 * @author alepd
 */

//La clase se utiliza porque un metodo en java solo puede devolver un tipo, asi que los agrupe todos en uno
public class ResultadoAlgoritmo {

    private Segmento seg;
    private int distanciasCalculadas;
    private double distanciaFinal;
    private long tiempoEjecucion;

    public ResultadoAlgoritmo(ResultadoAlgoritmo ra) {
        this.seg = new Segmento(ra.seg);
        this.distanciasCalculadas = ra.distanciasCalculadas;
        this.distanciaFinal = ra.distanciaFinal;
        this.tiempoEjecucion = ra.tiempoEjecucion;
    }

    public ResultadoAlgoritmo(Segmento seg, int distanciasCalculadas, double distanciaFinal, long tiempoEjecucion) {
        this.seg = new Segmento(seg);
        this.distanciasCalculadas = distanciasCalculadas;
        this.distanciaFinal = distanciaFinal;
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public Segmento getSeg() {
        return seg;
    }

    public int getDistanciasCalculadas() {
        return distanciasCalculadas;
    }

    public double getDistanciaFinal() {
        return distanciaFinal;
    }

    public double getTiempoEjecucion() {
        return (double)tiempoEjecucion/1000000;
    }

    public void setSeg(Segmento seg) {
        this.seg = seg;
    }

    public void setDistanciasCalculadas(int distanciasCalculadas) {
        this.distanciasCalculadas = distanciasCalculadas;
    }

    public void setDistanciaFinal(double distanciaFinal) {
        this.distanciaFinal = distanciaFinal;
    }

    public void setTiempoEjecucion(long tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }
    
    
}
