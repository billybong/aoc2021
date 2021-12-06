import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {
    private static final short LINE_WIDTH = 12;

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("input.txt"));
        final int result = "part2".equals(System.getenv("part")) ? part2(lines) : part1(lines);
        System.out.println(result);
    }

    private static int part1(List<String> lines) {
        return 0;
    }

    private static int part2(List<String> lines) {
        return 0;
    }
}