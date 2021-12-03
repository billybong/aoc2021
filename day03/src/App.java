import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class App {
    private static final short LINE_WIDTH = 12;

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("input.txt"));
        System.out.println("part2".equals(System.getenv("part")) ? part2(lines) : part1(lines));
    }

    private static int part1(List<String> lines) {
        short gammaB = 0;
        short epsilon = 0;
        short[] occurrences = new short[LINE_WIDTH];

        for (String line : lines) {
            if (line.isEmpty()) {
                break;
            }
            final char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                occurrences[i] += chars[i] == '0' ? -1 : 1;
            }
        }

        for (byte i = 0; i < occurrences.length; i++) {
            if(occurrences[occurrences.length - 1 - i] > 0) {
                gammaB |= (1 << i);
                epsilon &= ~(1 << i);
            } else {
                gammaB &= ~(1 << i);
                epsilon |= (1 << i);
            }
        }

        return gammaB * epsilon;
    }

    private static int part2(List<String> lines) {
        int lineWidth = lines.get(0).length();
        Collections.sort(lines);

        int oxygenGenRating = findRating(new ArrayList<>(lines), lineWidth, (zeroes, ones) -> zeroes.size() > ones.size() ? zeroes : ones);
        int co2Rating = findRating(lines, lineWidth, (zeroes, ones) -> zeroes.size() > ones.size() ? ones : zeroes);
        return oxygenGenRating * co2Rating;
    }

    private static int findRating(List<String> candidateLines, int lineWidth, ListSelector listSelector) {
        for (int charPos = 0; charPos < lineWidth; charPos++) {
            final List<String> zeroes = new ArrayList<>();
            final List<String> ones = new ArrayList<>();
            for (int i = 0; i < candidateLines.size(); i++) {
                final String line = candidateLines.get(i);
                if (line.isEmpty()) {
                    break;
                }
                if (line.charAt(charPos) == '0') {
                    zeroes.add(line);
                } else {
                    ones.addAll(candidateLines.subList(i, candidateLines.size()));
                    break;
                }
            }
            candidateLines = listSelector.select(zeroes, ones);
            if (candidateLines.size() == 1) {
                return Integer.parseInt(candidateLines.get(0), 2);
            }
        }
        return Integer.parseInt(candidateLines.get(0), 2);
    }

    @FunctionalInterface
    interface ListSelector {
        List<String> select(List<String> zeroes, List<String> ones);
    }
}