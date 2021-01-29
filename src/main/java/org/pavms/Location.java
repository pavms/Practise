package org.pavms;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Location {

    @Getter private final int row;
    @Getter private final int col;

    public Location(int[] pair) {
        row = pair[0];
        col = pair[1];
    }

    public static int distance(Location a, Location b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }
}
