package org.pavms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {


    Solution sol1 = new Solution(new String[]{
            "XXXX",
            "X XX",
            "X  X",
            "XX X"
    });

    Solution sol2 = new Solution(new String[]{
            "XXXX",
            "X XX",
            "X  X",
            "XX X"
    });

    Solution sol3 = new Solution(new String[]{
            "X XX",
            "X  X",
            "XX X"
    });

    Solution sol4 = new Solution(new String[]{
            "XXXX",
            "X XX",
            "X   ",
            "XX X"
    });

    Solution sol5 = new Solution(new String[]{
            "XXX",
            "X X",
            "X  ",
            "XX "
    });

    @Test
    public void testEquals() {

        assertEquals(sol1, sol2);

        assertNotEquals(sol1, sol3);
        assertNotEquals(sol1, sol4);
        assertNotEquals(sol1, sol5);

        //noinspection AssertBetweenInconvertibleTypes
        assertNotEquals(sol1, "BLAH");
        assertNotEquals(sol1, null);
    }

    @Test
    public void testHashCode() {

        assertEquals(sol1.hashCode(), sol2.hashCode());

        assertNotEquals(sol1.hashCode(), sol3.hashCode());
        assertNotEquals(sol1.hashCode(), sol4.hashCode());
        assertNotEquals(sol1.hashCode(), sol5.hashCode());
    }
}