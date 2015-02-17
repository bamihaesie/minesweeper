package service;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RandomMinePlacingStrategyTest {

    private RandomMinePlacingStrategy randomMinePlacingStrategy;
    public static final int ROUNDS = 100;
    public static final int RANGE = 10;

    @Before
    public void setUp() throws Exception {
        randomMinePlacingStrategy = new RandomMinePlacingStrategy();
    }

    @Test
    public void testGeneratePlacementIsRange() {
        int mines = 2;
        for (int round = 0; round < ROUNDS; round++) {
            Integer[] placement = randomMinePlacingStrategy.generatePlacement(RANGE, mines);
            assertEquals(2, placement.length);
            for (int i = 0; i < mines; i++) {
                assertTrue("Round " + round + " failed on " + placement[i],
                        placement[i] >= 0 && placement[i] < RANGE);
            }
        }

    }

    @Test
    public void testGeneratePlacementAllDistinct() {
        int mines = 7;
        for (int round = 0; round < ROUNDS; round++) {
            Integer[] placement = randomMinePlacingStrategy.generatePlacement(RANGE, mines);
            Set<Integer> seenBefore = new HashSet<>();
            for (int i = 0; i < mines; i++) {
                Integer currentMine = placement[i];
                assertTrue("Round " + round + " failed on " + currentMine,
                        !seenBefore.contains(currentMine));
                seenBefore.add(currentMine);
            }
        }

    }

}