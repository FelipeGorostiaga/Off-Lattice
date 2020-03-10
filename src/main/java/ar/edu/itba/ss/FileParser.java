package ar.edu.itba.ss;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FileParser {

    // dynamic file x y theta
    // static file N L velocity (default = 0.03)

    static int N;
    static Queue<Particle> particles = new LinkedList<>();
    static double L;
    static double maxRadius = 0;

    static void parseFiles() throws Exception {
        parseStaticFile();
        parseDynamicFile();
    }

    private static void parseStaticFile() throws FileNotFoundException {
        File file = new File(CommandParser.staticFilePath);
        Scanner sc = new Scanner(file);
        N = sc.nextInt();
        L = sc.nextDouble();
        for (int i = 0; i < N; i++) {
            particles.add(new Particle(i + 1));
        }
    }

    private static void parseDynamicFile() throws FileNotFoundException{
        File dynamicFile = new File(CommandParser.dynamicFilePath);
        Scanner sc = new Scanner(dynamicFile);
        sc.nextInt();
        for (int i = 0; i < N; i++) {
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            double theta = sc.nextDouble();
            Particle particle = particles.poll();
            particle.setX(x);
            particle.setY(y);
            particle.setTheta(theta);
            particles.add(particle);
        }
    }

}
