import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) throws IOException {
        final List<Integer> lines = Files.readAllLines(Paths.get("input.txt")).stream().mapToInt(Integer::parseInt).boxed().toList();
        final long result = "part1".equals(System.getenv("part")) ? part1(lines) : part2(lines);
        System.out.println(result);
    }

    private static long part1(List<Integer> lines) {
        int sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            final int nr = lines.get(i);
            if (isPrime(nr)) {
                sum += nr * i;
            }
        }

        return sum;
    }

    private static long part2(List<Integer> lines) {
        int sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            final int nr = lines.get(i);
            if (!isPrime(nr)) {
                if (i % 2 == 0) {
                    sum += nr;
                } else {
                    sum -= nr;
                }
            }
        }

        return sum;
    }

    private static boolean isPrime(int number) {
        return number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(n -> (number % n == 0));
    }
}