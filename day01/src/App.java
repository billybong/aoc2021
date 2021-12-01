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

    private static long part2(IntStream numbers, int windowSize) {
        var windows = new ArrayDeque<MutableInt>(windowSize + 1);

        return numbers.peek(nr -> {
                    windows.forEach(value -> value.increment(nr));
                    windows.add(new MutableInt(nr));
                })
                .skip(windowSize)
                .map(nr -> windows.removeFirst().value - nr)
                .filter(head -> head < windows.peekFirst().value)
                .count();
    }

    static class MutableInt {
        int value;

        private MutableInt(int initial) {
            this.value = initial;
        }

        public void increment(int delta) {
            value += delta;
        }
    }
}