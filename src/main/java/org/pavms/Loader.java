package org.pavms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class Loader {

    public Maze load(Path path) throws IOException {

        File file = path.toFile();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {

            int[] size = readPair(reader.readLine());
            int rows = size[0];
            int cols = size[1];

            Location start = new Location(readPair(reader.readLine()));
            Location end = new Location(readPair(reader.readLine()));

            Maze.Cell[][] cells = new Maze.Cell[rows][];

            for (int i = 0; i < rows; i++) {
                cells[i] = readCellLine(reader.readLine());
            }

            return new Maze(rows, cols, start, end, cells);
        }
    }


    private static int[] readPair(String line) {
        return Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    private Maze.Cell[] readCellLine(String text)  {
        return Arrays.stream(text.split(" ")).map(this::readCell).toArray(Maze.Cell[]::new);
    }

    private Maze.Cell readCell(String value) {
        switch(value) {
            case "0":
                return Maze.Cell.PASSAGE;
            case "1":
                return Maze.Cell.WALL;
            default:
                throw new UnsupportedOperationException("Unknown character");
        }
    }
}
