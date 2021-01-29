package org.pavms;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectHeapIndirectPriorityQueue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Maze {

    private static class Node {

        final Location location;
        Node cameFrom;
        int travelled;
        int estimated;
        boolean processed = false;

        private Node(Location location, int estimated) {
            this.location = location;
            this.travelled = Integer.MAX_VALUE;
            this.estimated = estimated;
            this.cameFrom = null;
        }

        int getDistance() {
            return travelled + estimated;
        }
    }

    private  int getIdx(Location location) {
        return location.getRow() * rows + location.getCol();
    }

    private Node solve() {

        Node[] nodes = new Node[ rows * cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Location location = new Location(i, j);
                nodes[i * rows + j] = new Node(location, Location.distance(location, end));
            }
        }

        IndirectPriorityQueue<Node> queue = new ObjectHeapIndirectPriorityQueue<>(
                nodes, Comparator.comparingInt(Node::getDistance)
        );

        queue.enqueue(getIdx(start));

        while(!queue.isEmpty()) {

            Node node = nodes[queue.dequeue()];

            if (node.location.equals(end)) return node;

            for(Location loc: getNeighbours(node.location)) {

                Node next = nodes[getIdx(loc)];
                if (next.processed) continue;

                if (next.getDistance() > node.travelled + node.estimated) {
                    next.travelled = node.travelled + 1;
                    next.cameFrom = node;
                    queue.changed(getIdx(node.location));
                }
            }
            node.processed = true;
        }

        return null;
    }

    @AllArgsConstructor
    enum Cell {
        WALL("X"), PASSAGE(" ");

        @Getter private final String text;
    }

    @Getter private Location start;
    @Getter private Location end;

    @Getter private int rows;
    @Getter private int cols;

    private Cell[][] maze;

    private Cell getCell(Location loc) {
        return maze[loc.getRow()][loc.getCol()];
    }

    private static int[][] OFFSETS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private List<Location> getNeighbours(Location loc) {
        return Arrays.stream(OFFSETS)
                .map(o -> new Location(loc.getRow() + o[0], loc.getCol() +o[1]))
                .filter(l -> l.getCol() < 0 || l.getCol() > cols)
                .filter(l -> l.getRow() < 0 || l.getRow() > rows)
                .filter(l -> getCell(l) == Cell.WALL)
                .collect(toList());
    }

    public static Maze load(String fileName) throws IOException {

        Maze maze = new Maze();
        File file = new File(fileName);

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {

            int[] size = readPair(reader.readLine());
            maze.rows = size[0];
            maze.cols = size[1];

            maze.start = new Location(readPair(reader.readLine()));
            maze.end = new Location(readPair(reader.readLine()));

            maze.maze = new Cell[maze.rows][];

            for (int i = 0; i < maze.rows; i++) {
                maze.maze[i] = readCellLine(reader.readLine());
            }
        }

        return maze;
    }

    private static int[] readPair(String line) {
        return Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    private static Cell[] readCellLine(String text)  {
        return Arrays.stream(text.split(" ")).map(Maze::readCell).toArray(Cell[]::new);
    }

    private static Cell readCell(String value) {
        switch(value) {
            case "0":
                return Cell.PASSAGE;
            case "1":
                return Cell.WALL;
            default:
                throw new UnsupportedOperationException("Unknown character");
        }
    }
}
