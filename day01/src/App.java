import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) throws IOException {
        final IntBinaryOperator operator = "part1".equals(System.getenv("part")) ?
                (index, number) -> isPrime(number) ? number * index : 0 :
                (index, number) -> isPrime(number) ? 0 : index % 2 == 0 ? number : -number;
        final int[] index = {-1};
        final int result = Files.readAllLines(Paths.get("input.txt")).stream()
                .mapToInt(Integer::parseInt)
                .reduce(0, (prev, current) -> prev + operator.applyAsInt(++index[0], current));
        System.out.println(result);
    }

    private static boolean isPrime(int number) {
        return number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number)).noneMatch(n -> (number % n == 0));
    }
}