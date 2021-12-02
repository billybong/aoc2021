import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) throws IOException {
        try (var numbers = Files.lines(Paths.get("input.txt")).mapToInt(Integer::parseInt)) {
            System.out.println("part2".equals(System.getenv("part")) ? part2(numbers.toArray(), 3) : part1(numbers));
        }
    }

    private static int part1(IntStream numbers) {
        int[] count = new int[]{0};
        numbers.reduce((prev, current) -> {
            if (current > prev) {
                count[0]++;
            }
            return current;
        });
        return count[0];
    }

    private static int part2(int[] numbers, int windowSize) {
        int result = 0;
        final int lastIndex = numbers.length - windowSize;
        for (int i = 0; i < lastIndex; i++) {
            if (numbers[i] < numbers[i + windowSize]) {
                result++;
            }
        }
        return result;
    }
}