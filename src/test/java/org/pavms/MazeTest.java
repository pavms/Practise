package org.pavms;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    @Test
    public void testNeighbours() throws URISyntaxException, IOException {

        Path path = Paths.get(getClass().getResource("/maze/neighbours.txt").toURI());
        Loader loader = new Loader();
        Maze maze = loader.load(path);

        List<Location> topLeft = maze.getNeighbours(new Location(0, 0));

        assertEquals(2, topLeft.size());
        assertTrue(topLeft.contains(new Location(0,1)));
        assertTrue(topLeft.contains(new Location(1,0)));

        List<Location> topRight = maze.getNeighbours(new Location(0, 4));

        assertEquals(2, topRight.size());
        assertTrue(topRight.contains(new Location(0,3)));
        assertTrue(topRight.contains(new Location(1,4)));

        List<Location> bottomLeft = maze.getNeighbours(new Location(2, 0));

        assertEquals(2, bottomLeft.size());
        assertTrue(bottomLeft.contains(new Location(2,1)));
        assertTrue(bottomLeft.contains(new Location(1,0)));

        List<Location> bottomRight = maze.getNeighbours(new Location(2, 4));

        assertEquals(2, bottomRight.size());
        assertTrue(bottomRight.contains(new Location(2,3)));
        assertTrue(bottomRight.contains(new Location(1,4)));

        List<Location> middle = maze.getNeighbours(new Location(1, 2));

        assertEquals(2, bottomRight.size());
        assertTrue(middle.contains(new Location(1,1)));
        assertTrue(middle.contains(new Location(1,3)));
    }

}