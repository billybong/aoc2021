import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class App {
    private static final short LINE_WIDTH = 12;

    public static void main(String[] args) throws IOException {
        final long start = System.getenv("DEBUG") != null ? System.nanoTime() : 0;
        var lines = Files.readAllLines(Paths.get("input.txt"));
        System.out.println("part2".equals(System.getenv("part")) ? part2(lines) : part1(lines));
        if (start != 0) {
            System.err.println("Execution time: " + (System.nanoTime() - (float) start) / 1_000_000 + " ms");
        }
    }

    private static int part1(List<String> lines) {
        short[] occurrences = new short[LINE_WIDTH];
        for (String line : lines) {
            if (line.isEmpty()) break;
            final char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                occurrences[i] += chars[i] == '0' ? -1 : 1;
            }
        }
        short gamma = 0;
        short epsilon = 0;
        for (byte i = 0; i < occurrences.length; i++) {
            if (occurrences[occurrences.length - 1 - i] > 0) {
                gamma |= (1 << i);
                epsilon &= ~(1 << i);
            } else {
                gamma &= ~(1 << i);
                epsilon |= (1 << i);
            }
        }
        return gamma * epsilon;
    }

    private static int part2(List<String> lines) {
        Collections.sort(lines);
        int oxygenGenRating = findRating(lines, (zeroes, ones) -> zeroes.size() > ones.size() ? -1 : 1);
        int co2Rating = findRating(lines, (zeroes, ones) -> zeroes.size() > ones.size() ? 1 : -1);
        return oxygenGenRating * co2Rating;
    }

    private static int findRating(List<String> sortedLines, Comparator<List<String>> listSelector) {
        List<String> candidatesLeft = new ArrayList<>(sortedLines);
        for (int charPos = 0; charPos < LINE_WIDTH; charPos++) {
            final List<String> zeroes = new ArrayList<>();
            final List<String> ones = new ArrayList<>();
            for (int lineIndex = 0; lineIndex < candidatesLeft.size(); lineIndex++) {
                final String line = candidatesLeft.get(lineIndex);
                if (line.isEmpty()) break;
                else if (line.charAt(charPos) == '0') {
                    zeroes.add(line);
                } else {
                    ones.addAll(candidatesLeft.subList(lineIndex, candidatesLeft.size()));
                    break;
                }
            }
            candidatesLeft = listSelector.compare(zeroes, ones) > 0 ? zeroes : ones;
            if (candidatesLeft.size() == 1) {
                return Integer.parseInt(candidatesLeft.get(0), 2);
            }
        }
        return Integer.parseInt(candidatesLeft.get(0), 2);
    }
}