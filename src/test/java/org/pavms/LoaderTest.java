package org.pavms;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoaderTest {

    @Test
    public void testLoader() throws URISyntaxException, IOException {

        String[] input = new String[]{
                "11111",
                "10101",
                "10101",
                "10001",
                "11111"
        };

        Maze maze1 = read(new Location(1,1), new Location(3,3), input);

        Path path = Paths.get(getClass().getResource("/maze/input.txt").toURI());

        Loader loader = new Loader();

        Maze maze2 = loader.load(path);

        assertEquals(maze1.getStart(), maze2.getStart());
        assertEquals(maze1.getEnd(), maze2.getEnd());
        assertEquals(maze1.getRows(), maze2.getRows());
        assertEquals(maze1.getCols(), maze2.getCols());

        for (int i = 0; i < maze1.getRows(); i++) {
            for (int j = 0; j < maze1.getCols(); j++) {
                assertEquals(maze1.getCell(i, j), maze2.getCell(i, j));
            }
        }
    }

    private Maze read(Location start, Location end, String... lines) {

        int rows = lines.length;
        int cols = lines[0].length();

        Maze.Cell[][] cells = new Maze.Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char ch = lines[i].charAt(j);

                switch(ch) {
                    case '1':
                        cells[i][j] = Maze.Cell.WALL;
                        break;
                    case '0':
                        cells[i][j] = Maze.Cell.PASSAGE;
                        break;
                }
            }
        }
        return new Maze(rows, cols, start, end, cells);
    }

}