package party.cuckcatcher.impl.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Made by SkidRevenant at 04/03/2018
 */
public class AverageUtil {

    @Getter
    private List<Double> entries = new ArrayList<>();

    public void addEntry(double value) {
        if (entries.size() >= 30) {
            this.entries.remove(entries.size() - 1);
        } else {
            this.entries.add(value);
        }
    }

    public double getAverage() {
        return this.entries.stream().mapToDouble(d -> d).average().getAsDouble();
    }
}
