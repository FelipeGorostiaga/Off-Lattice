package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static ar.edu.itba.ss.CommandParser.*;
import static ar.edu.itba.ss.FileParser.L;
import static ar.edu.itba.ss.FileParser.particles;

public class CellIndex {

    private static List<List<Particle>> cells;

    private static List<List<Particle>> cloneCells(List<List<Particle>> cells) {
        List<List<Particle>> clonedCells = new ArrayList<>();
        for(List<Particle> cell: cells) {
            List<Particle> clonedCell = new ArrayList<>();
            for(Particle particle: cell) {
                Particle clone = null;
                try {
                     clone = particle.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    System.out.println("Couldn't clone particles, aborting...");
                    System.exit(1);
                }
                clonedCell.add(clone);
            }
            clonedCells.add(clonedCell);
        }
        return clonedCells;
    }

    // O(N)
    static void cellIndexAlgorithm() {
        for (List<Particle> cell : cells) {
            for (Particle p : cell) {
                p.setNeighbours(new TreeSet<Particle>());
                double cellX = p.getCellX();
                double cellY = p.getCellY();
                checkNeighbourCells(p, cellX, cellY);
                checkNeighbourCells(p, cellX, cellY + 1);
                checkNeighbourCells(p, cellX + 1, cellY + 1);
                checkNeighbourCells(p, cellX + 1, cellY);
                checkNeighbourCells(p, cellX + 1, cellY - 1);
            }
        }
    }

    private static void checkNeighbourCells(Particle p, double cellX, double cellY) {
        if(periodicContour) {
            if(cellX >= M) cellX = 0;
            if(cellY >= M) cellY = 0;
            if(cellX == -1) cellX = M - 1;
            if(cellY == -1) cellY = M - 1;
        }
        else {
            if(cellX < 0 || cellY < 0 || cellX >= M || cellY >= M) return;
        }
        int currentCell = (int) (cellY * M + cellX);
        List<Particle> particlesInCurrentCell = cells.get(currentCell);
        for(Particle neighbourCellParticle: particlesInCurrentCell) {
            if(!neighbourCellParticle.equals(p)) {
                double distance;
                if(periodicContour) {
                    distance = p.calculatePeriodicDistance(neighbourCellParticle);
                }
                else {
                    distance = p.calculateDistance(neighbourCellParticle);
                }
                if(distance < RC) {
                    p.addNeighbour(neighbourCellParticle);
                    neighbourCellParticle.addNeighbour(p);
                }
            }
        }
    }

    static void initializeCells() {
        // create matrix
        cells = new ArrayList<>();
        for(int i = 0; i < (M * M) ; i++){
            cells.add(new ArrayList<Particle>());
        }
        // populate cells with particles
        for(Particle p : particles) {
            double cellX = Math.floor(p.getX() / (L / M));
            double cellY = Math.floor(p.getY() / (L / M));
            int cellNumber = (int) (cellY * M + cellX);
            List <Particle> cell = cells.get(cellNumber);
            cell.add(p);
            // set current cell coordinates for this particle
            p.setCellX(cellX);
            p.setCellY(cellY);
        }
    }


    static void reCalculateCells() {
        for (Particle p : particles){
            double cellX = Math.floor(p.getX() / (L / M));
            double cellY = Math.floor(p.getY() / (L / M));
            int cellNumber = (int) (cellY * M + cellX);
            int previousCellNumber = (int) (p.getCellY() * M + p.getCellX());
            List<Particle> newCell = cells.get(cellNumber);
            List<Particle> previousCell = cells.get(previousCellNumber);
            if(newCell != previousCell) {
                previousCell.remove(p);
            }
            p.setCellX(cellX);
            p.setCellY(cellY);
            newCell.add(p);
        }
    }

}
