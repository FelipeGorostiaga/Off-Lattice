package ar.edu.itba.ss;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static ar.edu.itba.ss.FileParser.L;


public class Particle implements Comparable<Particle> {

    private int id;
    private double x;
    private double y;
    private double velocity;
    private double theta;
    private Set<Particle> neighbours;
    private double cellX;
    private double cellY;

    public Particle(int id, double x, double y, double velocity, double theta) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.theta = theta;
        this.neighbours = new TreeSet<>();
    }

    public Particle(int id) {
        this.id = id;
        this.neighbours = new TreeSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
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

    double calculateDistance(Particle particle){
        return Math.sqrt(Math.pow(x - particle.getX(), 2) + Math.pow(y - particle.getY(), 2));
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
        return "Particle { Id = " + id +
                ", x = " + x +
                ", y = " + y +
                ", velocity = " + velocity +
                ", theta = " + theta +
                '}';
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

    public int compareTo(Particle particle){
        return id - particle.getId();
    }

}
