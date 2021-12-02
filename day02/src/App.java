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
            switch (command.nextToken()) {
                case "up" -> depth -= Integer.parseInt(command.nextToken());
                case "down" -> depth += Integer.parseInt(command.nextToken());
                case "forward" -> pos += Integer.parseInt(command.nextToken());
                default -> {}
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
            switch (command.nextToken()) {
                case "up" -> aim -= Integer.parseInt(command.nextToken());
                case "down" -> aim += Integer.parseInt(command.nextToken());
                case "forward" -> {
                    int value = Integer.parseInt(command.nextToken());
                    pos += value;
                    depth += aim * value;
                }
                default -> {}
            }
        }

        return pos * depth;
    }
}