import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws IOException {
        var numbers = Files.lines(Paths.get("input.txt")).mapToInt(Integer::parseInt).toArray();
        System.out.println("part2".equals(System.getenv("part")) ? part2(numbers, 3) : part1(numbers));
    }

    private static int part1(int[] numbers) {
        int count = 0;
        int prev = Integer.MAX_VALUE;
        for (int number : numbers) {
            if (number > prev) {
                count++;
            }
            prev = number;
        }
        return count;
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