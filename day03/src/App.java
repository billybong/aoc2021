import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) throws IOException {
        final long start = System.getenv("DEBUG") != null ? System.nanoTime() : 0;
        System.out.println("part2".equals(System.getenv("part")) ? part2(lines(new File("input.txt"))) : part1());
        if (start != 0) {
            System.err.printf("Execution time: %.10f ms%n", ((float) System.nanoTime() - (float) start) / 1_000_000);
        }
    }

    private static int part1() throws IOException {
        short[] occurrences = null;
        final BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (occurrences == null) {
                    occurrences = new short[line.length()];
                }
                final char[] chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    occurrences[i] += chars[i] == '0' ? -1 : 1;
                }
            }
        } finally {
            br.close();
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
        final int lineWidth = lines.get(0).length();
        int oxygenGenRating = findRating(lines, (zeroes, ones) -> zeroes.size() > ones.size() ? -1 : 1, lineWidth);
        int co2Rating = findRating(lines, (zeroes, ones) -> zeroes.size() > ones.size() ? 1 : -1, lineWidth);
        return oxygenGenRating * co2Rating;
    }

    private static int findRating(List<String> sortedLines, Comparator<List<String>> listSelector, int lineWidth) {
        List<String> candidatesLeft = new ArrayList<>(sortedLines);
        for (int charPos = 0; charPos < lineWidth; charPos++) {
            for (int lineIndex = 0; lineIndex < candidatesLeft.size(); lineIndex++) {
                if (candidatesLeft.get(lineIndex).charAt(charPos) == '1') {
                    final List<String> zeroes = candidatesLeft.subList(0, lineIndex);
                    final List<String> ones = candidatesLeft.subList(lineIndex, candidatesLeft.size());
                    candidatesLeft = listSelector.compare(zeroes, ones) > 0 ? zeroes : ones;
                    break;
                }
                if (lineIndex == candidatesLeft.size() - 1) {
                    //reached the end, found no ones - so all are zeroes
                    break;
                }
            }

            if (candidatesLeft.size() == 1) {
                return Integer.parseInt(candidatesLeft.get(0), 2);
            }
        }
        return Integer.parseInt(candidatesLeft.get(0), 2);
    }

    private static List<String> lines(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        final List<String> lines = new ArrayList<>(2000);
        try {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            br.close();
        }
        return lines;
    }
}