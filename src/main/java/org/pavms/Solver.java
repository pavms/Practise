package org.pavms;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectHeapIndirectPriorityQueue;

import java.util.Comparator;

public class Solver {

    private static class Node {

        final Location location;
        final int estimated;

        Node cameFrom;
        int travelled;
        boolean processed;

        private Node(Location location, int estimated) {
            this.location = location;
            this.travelled = Integer.MAX_VALUE;
            this.estimated = estimated;
            this.cameFrom = null;
            this.processed = false;
        }

        int getDistance() {
            return travelled + estimated;
        }
    }

    public Solution solve(Maze maze) {

        Solution solution = new Solution(maze);

        Node node = getPath(maze);

        if (node == null) return solution;

        node = node.cameFrom;
        while (node != null && !node.location.equals(maze.getStart())) {
            solution.markAsPath(node.location);
            node = node.cameFrom;
        }

        return solution;
    }

    private Node getPath(Maze maze) {

        Node[] nodes = createNodes(maze);

        IndirectPriorityQueue<Node> queue = new ObjectHeapIndirectPriorityQueue<>(
                nodes, Comparator.comparingInt(Node::getDistance)
        );

        nodes[getIdx(maze, maze.getStart())].travelled = 0;
        queue.enqueue(getIdx(maze, maze.getStart()));

        while(!queue.isEmpty()) {

            Node node = nodes[queue.dequeue()];

            if (node.location.equals(maze.getEnd())) return node;

            for(Location loc: maze.getNeighbours(node.location)) {

                Node next = nodes[getIdx(maze, loc)];

                if (next.processed) continue;

                if (next.travelled == Integer.MAX_VALUE) {
                    queue.enqueue(getIdx(maze, next.location));
                    enterIntoFrom(next, node);
                } else if (next.travelled > node.travelled + 1) {
                    queue.changed(getIdx(maze, next.location));
                    enterIntoFrom(next, node);
                }
            }

            node.processed = true;
        }

        return null;
    }

    private void enterIntoFrom(Node next, Node node) {
        next.travelled = node.travelled + 1;
        next.cameFrom = node;
    }

    private Node[] createNodes(Maze maze) {
        Node[] nodes = new Node[maze.getRows() * maze.getCols()];

        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getCols(); j++) {
                Location location = new Location(i, j);
                nodes[i * maze.getCols() + j] = new Node(location, Location.distance(location, maze.getEnd()));
            }
        }
        return nodes;
    }

    private int getIdx(Maze maze, Location location) {
        return location.getRow() * maze.getCols() + location.getCol();
    }
}
