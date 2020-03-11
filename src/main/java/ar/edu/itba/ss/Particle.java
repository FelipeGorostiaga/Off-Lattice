package ar.edu.itba.ss;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static ar.edu.itba.ss.FileParser.L;


public class Particle implements Comparable<Particle>, Cloneable {

    private int id;
    private double x;
    private double y;
    private double theta;
    private Set<Particle> neighbours;
    private double cellX;
    private double cellY;

    Particle(int id, double x, double y, double theta) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.neighbours = new TreeSet<>();
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    double getY() {
        return y;
    }

    void setY(double y) {
        this.y = y;
    }

    double getTheta() {
        return theta;
    }

    void setTheta(double theta) {
        this.theta = theta;
    }

    Set<Particle> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Set<Particle> neighbours) {
        this.neighbours = neighbours;
    }

    double getCellX() {
        return cellX;
    }

    void setCellX(double cellX) {
        this.cellX = cellX;
    }

    double getCellY() {
        return cellY;
    }

    void setCellY(double cellY) {
        this.cellY = cellY;
    }

    void addNeighbour(Particle neighbour){
        this.neighbours.add(neighbour);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Particle { id = " + id +
                ", x = " + x +
                ", y = " + y +
                ", theta = " + theta +
                '}';
    }

    double calculateDistance(Particle particle){
        return Math.sqrt(Math.pow(x - particle.getX(), 2) + Math.pow(y - particle.getY(), 2));
    }

    double calculatePeriodicDistance(Particle particle) {
        double xDistance = Math.abs(this.x - particle.x);
        // La distancia requerida "dando la vuelta" es menor a la directa
        if (xDistance > (L / 2)) {
            xDistance = L - xDistance;
        }
        double yDistance = Math.abs(this.y - particle.y);
        if (yDistance > (L / 2)) {
            yDistance = L - yDistance;
        }
        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }

    @Override
    public int compareTo(Particle particle){
        return id - particle.getId();
    }

    public Particle clone() throws CloneNotSupportedException {
        return (Particle)super.clone();
    }

}
