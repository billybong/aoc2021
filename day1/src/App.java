import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        final List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        final long result = "1".equals(System.getenv("solution")) ? solution1(lines) : solution2(lines);
        System.out.println(result);
    }

    private static long solution1(List<String> lines) {
        for (int x = 0; x < lines.size(); x++) {
            var candidate1 = Integer.parseInt(lines.get(x));
            for (int y = x+1; y < lines.size(); y++) {
                var candidate2 = Integer.parseInt(lines.get(y));
                if (candidate1 + candidate2 == 2020) {
                    return (long) candidate1 * candidate2;
                }
            }
        }
        throw new IllegalStateException("Didn't find a solution");
    }

    private static long solution2(List<String> lines) {
        for (int x = 0; x < lines.size(); x++) {
            var candidate1 = Integer.parseInt(lines.get(x));
            for (int y = x+1; y < lines.size(); y++) {
                var candidate2 = Integer.parseInt(lines.get(y));
                for (int z = y+1; z < lines.size(); z++) {
                    var candidate3 = Integer.parseInt(lines.get(z));
                    if (candidate1 + candidate2 + candidate3 == 2020) {
                        return (long) candidate1 * candidate2 * candidate3;
                    }
                }
            }
        }
        throw new IllegalStateException("Didn't find a solution");
    }
}