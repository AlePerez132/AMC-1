/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_1_amc;

import static java.lang.Math.*;

public class Punto {

    int id;
    private double x, y;
    
    public Punto(double x, double y){
        this.x=x;
        this.y=y;
    }

    public Punto(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Punto(Punto p) {
        this.id=p.id;
        this.x = p.x;
        this.y = p.y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distancia(Punto p1) {
        return sqrt(pow((this.x - p1.x), 2) + pow(this.y - p1.y, 2));
    } //Distancia entre un punto y otro

    public boolean comparar(Punto p) {
        boolean menor = false;
        if (this.x < p.x) {
            menor = true;
        } else if (this.x == p.x && this.y < p.y) {
            menor = true;
        }

        return menor;
    }

    public String toString() {
        String punto = id + "(" +  x + ", " + y + ")";
        return punto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Punto punto = (Punto) o;
        return Double.compare(punto.x, x) == 0 && Double.compare(punto.y, y) == 0;
    }
}
