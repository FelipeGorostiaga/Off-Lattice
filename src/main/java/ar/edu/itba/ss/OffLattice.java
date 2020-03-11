package ar.edu.itba.ss;

import java.util.Set;

import static ar.edu.itba.ss.CommandParser.alpha;
import static ar.edu.itba.ss.FileParser.L;
import static ar.edu.itba.ss.FileParser.V;

public class OffLattice {

    public static void calculateNewPosition(Particle p) {
        p.setX(p.getX() + Math.cos(p.getTheta()) * V);
        p.setY(p.getY() + Math.sin(p.getTheta()) * V);
        if (p.getX() >= L) p.setX(p.getX() - L);
        if (p.getY() >= L) p.setY(p.getY() - L);
        if (p.getX() < 0) p.setX(p.getX() + L);
        if (p.getY() < 0) p.setY(p.getY() + L);
    }

    public static void calculateNewTheta(Particle p) {
        double noise = getRandomNoise();
        Set<Particle> neighbours = p.getNeighbours();
        double cosTheta = 0;
        double sinTheta = 0;
        for(Particle neighbour: neighbours) {
            cosTheta += Math.cos(neighbour.getTheta());
            sinTheta += Math.sin(neighbour.getTheta());
        }
        cosTheta /= neighbours.size();
        sinTheta /= neighbours.size();
        double newTheta = Math.atan2(sinTheta, cosTheta) + noise;
        p.setTheta(checkThetaValue(newTheta));
    }

    private static double getRandomNoise() {
        return alpha * (Math.random() - 1.0 / 2.0);
    }

    private static double checkThetaValue(double theta) {
        if (theta > Math.PI){
            theta -= 2 * Math.PI;
        }else if (theta < -Math.PI){
            theta += 2 * Math.PI;
        }
        return theta;
    }
}
