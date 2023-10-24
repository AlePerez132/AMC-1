/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_1_amc;

import java.util.ArrayList;

public class Segmento {

    private Punto p1, p2;
    
    public Segmento(Punto p1, Punto p2) {
        this.p1 = new Punto(p1);
        this.p2 = new Punto(p2);
    }
    
    public Segmento(){}
    
    public Segmento(Segmento s){
        this.p1=new Punto(s.p1);
        this.p2=new Punto(s.p2);
    }

    public Punto getP1() {
        return p1;
    }

    public Punto getP2() {
        return p2;
    }

    public double distancia() {
        double sol;
        sol =  Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
        return sol;
    }
    
    public String toString(){
        String segmento = "(Punto " + String.valueOf(p1.getId()) + ": " + p1.getX() + ", " + p1.getY();
        segmento += ", Punto " + p2.getId() + ": " + p2.getX() + ", " + p2.getY() + ")";
        return segmento;
    }

    public void setP1(Punto p1) {
        this.p1 = p1;
    }

    public void setP2(Punto p2) {
        this.p2 = p2;
    }

    
}
