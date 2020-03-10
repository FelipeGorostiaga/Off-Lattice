package ar.edu.itba.ss;


import java.io.File;
import java.io.PrintWriter;

import static ar.edu.itba.ss.CellIndex.*;
import static ar.edu.itba.ss.CommandParser.*;
import static ar.edu.itba.ss.FileParser.*;
import static ar.edu.itba.ss.OffLattice.calculateNewValues;

public class App {

    public static void main( String[] args ) {

        // generate N particles (x, y, v0 = 0.03) random
        // create cells and populate cells with particles (t = 0)
        // [t + 1] for each particle, calculate new position(x,y) and new angle taking into account
        // the neighbours, taking into account the r value (interaction radius)
        // for each dt --> store values for each particle (x, y, v, angle)
        // re-calculate particles in cell (cell index method)
        // repeat till desired t.
        // animate

        CommandParser.parseCommandLine(args);
        long startTime = System.currentTimeMillis();
        try {
            parseFiles();

        } catch (Exception e) {
            System.out.println("Invalid file name...");
            System.exit(1);
        }
        if(!checkMCondition()) {
            System.out.println("Invalid M parameter value! Must comply with condition {M/L > (RC + 2 * maxRadius)} ");
            System.exit(1);
        }
        // create cells
        // populate them
        initializeCells();
        outputToFile(0);
        for(int i = 1 ; i < T ; i++) {
            // recalculate new values (x,y,theta)
            calculateNewValues();
            // store values in file
            outputToFile(i);
            // recalculate new particles in cell
            reCalculateCells();
        }

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");

    }

    private static void outputToFile(int time) {
        File file = new File("/files/output.txt");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("output.txt", "UTF-8");
        } catch (Exception e) {
            System.out.println("Couldn't write output to file...");
            System.exit(1);
        }
        writer.print(time);
        for (Particle p : particles) {
            writer.print(p.getId());
            writer.print(p.getX());
            writer.print(p.getY());
            writer.print(p.getTheta());
            writer.print("\n");

        }
        writer.close();
    }

    private static boolean checkMCondition() {
        return L/M > (RC + 2 * maxRadius);
    }

}
