import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class App {
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("input.txt"));
        System.out.println("part2".equals(System.getenv("part")) ? part2(lines) : part1(lines));
    }

    private static int part1(List<String> lines) {
        int pos = 0;
        int depth = 0;

        for (String line : lines) {
            var command = new StringTokenizer(line, " ", false);
            var direction = command.nextToken();
            int steps = Integer.parseInt(command.nextToken());
            switch (direction) {
                case "up" -> depth -= steps;
                case "down" -> depth += steps;
                case "forward" -> pos += steps;
            }
        }

        return pos * depth;
    }

    private static int part2(List<String> lines) {
        int pos = 0;
        int depth = 0;
        int aim = 0;

        for (String line : lines) {
            var command = new StringTokenizer(line, " ", false);
            var direction = command.nextToken();
            int steps = Integer.parseInt(command.nextToken());
            switch (direction) {
                case "up" -> aim -= steps;
                case "down" -> aim += steps;
                case "forward" -> {
                    pos += steps;
                    depth += aim * steps;
                }
            }
        }

        return pos * depth;
    }
}