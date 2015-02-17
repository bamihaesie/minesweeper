package service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomMinePlacingStrategy {

    public Integer[] generatePlacement(int range, int mines) {

        Set<Integer> duplicateFreePlacement = new HashSet<>();
        Random random = new Random();

        while (duplicateFreePlacement.size() < mines) {
            duplicateFreePlacement.add(random.nextInt(range));
        }

        Integer[] placement = new Integer[duplicateFreePlacement.size()];
        return duplicateFreePlacement.toArray(placement);
    }
}
