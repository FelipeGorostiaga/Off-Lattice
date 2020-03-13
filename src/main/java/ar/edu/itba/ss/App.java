package ar.edu.itba.ss;


import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static ar.edu.itba.ss.CellIndex.cellIndexAlgorithm;
import static ar.edu.itba.ss.CellIndex.populateCells;
import static ar.edu.itba.ss.CommandParser.T;
import static ar.edu.itba.ss.FileParser.parseDynamicFile;
import static ar.edu.itba.ss.FileParser.particles;
import static ar.edu.itba.ss.OffLattice.calculateNewPosition;
import static ar.edu.itba.ss.OffLattice.calculateNewTheta;

public class App {

    public static void main( String[] args ) {
        long startTime = System.currentTimeMillis();
        CommandParser.parseCommandLine(args);
        try {
            parseDynamicFile();
        } catch (Exception e) {
            System.out.println("Invalid file name...");
            System.exit(1);
        }
        File file = new File("output.txt");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("output.txt", "UTF-8");
        } catch (Exception e) {
            System.out.println("Couldn't write output to file...");
            System.exit(1);
        }
        populateCells();
        outputToFile(writer, 0);
        for(int i = 1 ; i <= T ; i++) {

            // calculate neighbours
            cellIndexAlgorithm();

            // recalculate new values (x, y, theta)
            Map<Particle, Double> angleMap = new HashMap<>();
            for(Particle p : particles) {
                calculateNewPosition(p);
                double theta = calculateNewTheta(p);
                angleMap.put(p, theta);
            }
            for(Particle p: particles) {
                p.setTheta(angleMap.get(p));
            }

            // store values in file
            outputToFile(writer, i);

            // recalculate new particles in cell
            populateCells();
        }
        writer.close();
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }

    private static void outputToFile(PrintWriter writer, int time) {
        writer.print(time);
        for (Particle p : particles) {
            writer.print(p.getId());
            writer.print(p.getX());
            writer.print(p.getY());
            writer.print(p.getTheta());
            writer.print("\n");
        }
    }
}
