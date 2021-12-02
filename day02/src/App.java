import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class App {
    private static final Pattern SEPARATOR = Pattern.compile(" ");

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("input.txt"));
        System.out.println("part2".equals(System.getenv("part")) ? part2(lines) : part1(lines));
    }

    private static int part1(List<String> lines) {
        int pos = 0;
        int depth = 0;

        for (String line : lines) {
            var command = Command.parse(line);
            switch (command.direction) {
                case UP -> depth -= command.steps;
                case DOWN -> depth += command.steps;
                case FORWARD -> pos += command.steps;
            }
        }

        return pos * depth;
    }

    private static int part2(List<String> lines) {
        int pos = 0;
        int depth = 0;
        int aim = 0;

        for (String line : lines) {
            var command = Command.parse(line);
            switch (command.direction) {
                case UP -> aim -= command.steps;
                case DOWN -> aim += command.steps;
                case FORWARD -> {
                    pos += command.steps;
                    depth += aim * command.steps;
                }
            }
        }

        return pos * depth;
    }

    record Command(Direction direction, int steps) {
        static Command parse(String line) {
            var split = SEPARATOR.split(line);
            return new Command(Direction.from(split[0]), Integer.parseInt(split[1]));
        }
    }

    enum Direction {
        FORWARD, DOWN, UP;

        public static Direction from(String s) {
            return Direction.valueOf(s.toUpperCase(Locale.ROOT));
        }
    }
}