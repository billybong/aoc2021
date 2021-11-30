import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        final List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        final long result = "part1".equals(System.getenv("part")) ? part1(lines) : part2(lines);
        System.out.println(result);
    }

    private static long part1(List<String> lines) {
        for (int x = 0; x < lines.size(); x++) {
            var candidate1 = parseLine(lines, x);
            for (int y = x+1; y < lines.size(); y++) {
                var candidate2 = parseLine(lines, y);
                if (candidate1 + candidate2 == 2020) {
                    return (long) candidate1 * candidate2;
                }
            }
        }
        throw new IllegalStateException("Didn't find a solution");
    }

    private static long part2(List<String> lines) {
        final int magicNr = 2020;
        for (int x = 0; x < lines.size(); x++) {
            var candidate1 = parseLine(lines, x);
            if (candidate1 > 2020) {
                continue;
            }

            for (int y = x+1; y < lines.size(); y++) {
                var candidate2 = parseLine(lines, y);
                if (candidate1 + candidate2 > magicNr) {
                    continue;
                }

                for (int z = y+1; z < lines.size(); z++) {
                    var candidate3 = parseLine(lines, z);
                    if (candidate1 + candidate2 + candidate3 == magicNr) {
                        return (long) candidate1 * candidate2 * candidate3;
                    }
                }
            }
        }
        throw new IllegalStateException("Didn't find a solution");
    }

    private static int parseLine(List<String> lines, int x) {
        return Integer.parseInt(lines.get(x));
    }
}