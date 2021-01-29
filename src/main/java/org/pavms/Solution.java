package org.pavms;

import java.util.Arrays;

public class Solution {

    private final static char START = 'S';
    private final static char END = 'E';
    private final static char PATH = '.';
    private final static char WALL = '#';
    private final static char PASSAGE = ' ';

    private char[][] solution;

    public Solution(Maze maze) {

        solution = new char[maze.getRows()][maze.getCols()];

        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getCols(); j++) {
                solution[i][j] = (maze.getCell(i, j) == Maze.Cell.WALL)? WALL : PASSAGE;
            }
        }

        solution[maze.getStart().getRow()][maze.getStart().getCol()] = START;
        solution[maze.getEnd().getRow()][maze.getEnd().getCol()] = END;
    }

    public void markAsPath(Location location) {
        solution[location.getRow()][location.getCol()] = PATH;
    }

    Solution(String... lines) {
        solution = Arrays.stream(lines).map(String::toCharArray).toArray(char[][]::new);
    }

    @Override
    public boolean equals(Object obj) {

        if (null == obj) return false;
        else if (!(obj instanceof Solution)) return false;
        else if (obj == this) return true;
        else {
            Solution other = (Solution) obj;
            if (solution.length != other.solution.length) return false;
            for (int i = 0; i < solution.length; i++) {
                if (!Arrays.equals(solution[i], other.solution[i])) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(Arrays.stream(solution).mapToInt(Arrays::hashCode).toArray());
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                sb.append(solution[i][j]);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
