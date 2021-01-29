package org.pavms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationSolveMazes {

    @Test
    public void testDistance() {

        Location a = new Location(5, 15);
        Location b = new Location(10, 10);

        assertEquals(10, Location.distance(a, b));
        assertEquals(10, Location.distance(b, a));
    }
}