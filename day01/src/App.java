import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) throws IOException {
        try (var numbers = Files.lines(Paths.get("input.txt")).mapToInt(Integer::parseInt)) {
            System.out.println("part2".equals(System.getenv("part")) ? part2(numbers, 3) : part1(numbers));
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

    private static int part2(IntStream numbers, int windowSize) {
        var result = new MutableInt(0);
        var index = new MutableInt(0);
        var windows = new ArrayDeque<MutableInt>(windowSize);

        numbers.forEach(nr -> {
            if (index.getAndIncrement() >= windowSize && windows.removeFirst().value < windows.peekFirst().value + nr) {
                result.getAndIncrement();
            }
            windows.forEach(value -> value.add(nr));
            windows.add(new MutableInt(nr));
        });

        return result.value;
    }

    static class MutableInt {
        int value;

        private MutableInt(int value) {
            this.value = value;
        }

        public void add(int delta) {
            value += delta;
        }

        public int getAndIncrement() {
            return value++;
        }
    }
}