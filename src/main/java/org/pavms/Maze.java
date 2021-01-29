package org.pavms;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Maze {

    public enum Cell { WALL, PASSAGE }

    @Getter final private Location start;
    @Getter final private Location end;

    @Getter final private int rows;
    @Getter final private int cols;

    private final Cell[][] cells;

    public Maze(int rows, int cols, Location start, Location end, Cell[][] cells) {
        this.rows = rows;
        this.cols = cols;
        this.start = start;
        this.end = end;
        this.cells = cells;
    }

    private Cell getCell(Location loc) {
        return cells[loc.getRow()][loc.getCol()];
    }

    Cell getCell(int row, int col) {
        return cells[row][col];
    }

    private static final int[][] OFFSETS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    List<Location> getNeighbours(Location loc) {
        return Arrays.stream(OFFSETS)
                .map(o -> new Location(loc.getRow() + o[0], loc.getCol() + o[1]))
                .filter(l -> l.getCol() >= 0 && l.getCol() < cols)
                .filter(l -> l.getRow() >= 0 && l.getRow() < rows)
                .filter(l -> getCell(l) == Cell.PASSAGE)
                .collect(toList());
    }
}
