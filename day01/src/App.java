import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) throws IOException {
        final int[] lines = Files.readAllLines(Paths.get("input.txt")).stream().mapToInt(Integer::parseInt).toArray();
        final int result = "part1".equals(System.getenv("part")) ? part1(lines) : part2(lines);
        System.out.println(result);
    }

    private static int part1(int[] lines) {
        int sum = 0;

        for (int i = 0; i < lines.length; i++) {
            final int nr = lines[i];
            if (isPrime(nr)) {
                sum += nr * i;
            }
        }

        return sum;
    }

    private static int part2(int[] lines) {
        int sum = 0;

        for (int i = 0; i < lines.length; i++) {
            final int nr = lines[i];
            if (!isPrime(nr)) {
                sum += i % 2 == 0 ? nr : -nr;
            }
        }

        return sum;
    }

    private static boolean isPrime(int number) {
        return number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(n -> (number % n == 0));
    }
}