package ar.edu.itba.ss;

import java.util.Set;

import static ar.edu.itba.ss.CommandParser.*;
import static ar.edu.itba.ss.FileParser.L;


class OffLattice {

    static void calculateNewPosition(Particle p) {
        double x = p.getX() + (Math.cos(p.getTheta()) * V);
        double y = p.getY() + (Math.sin(p.getTheta()) * V);
        if (x >= L) {
            x -= L;
        }
        if (x < 0) {
            x += L;
        }
        if (y >= L) {
            y -= L;
        }
        if (y < 0) {
            y += L;
        }
        p.setX(x);
        p.setY(y);
    }

    static double calculateNewTheta(Particle p) {
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
        return checkThetaValue(newTheta);
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
