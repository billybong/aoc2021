import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

public class App {
    public static final IntBinaryOperator SOLUTION = "part2".equals(System.getenv("part")) ? App::part2 : App::part1;

    public static void main(String[] args) throws IOException {
        try (var numbers = Files.lines(Paths.get("input.txt")).mapToInt(Integer::parseInt)) {
            final int[] index = {0};
            System.out.println(numbers.reduce(0, (prev, current) -> prev + SOLUTION.applyAsInt(index[0]++, current)));
        }
    }

    private static int part1(int index, int number) {
        return isPrime(number) ? number * index : 0;
    }

    private static int part2(int index, int number) {
        return isPrime(number) ? 0 : index % 2 == 0 ? number : -number;
    }

    private static boolean isPrime(int number) {
        return number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number)).noneMatch(n -> (number % n == 0));
    }
}