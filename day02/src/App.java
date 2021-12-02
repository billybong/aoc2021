import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) throws IOException {
        try (var numbers = Files.lines(Paths.get("input.txt")).mapToInt(Integer::parseInt)) {
            System.out.println("part2".equals(System.getenv("part")) ? part2(numbers) : part1(numbers));
        }
    }

    private static int part1(IntStream numbers) {
        return 0;
    }

    private static int part2(IntStream numbers) {
        return 0;
    }
}