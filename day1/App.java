import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws IOException {
        try (var numbers = Files.lines(Paths.get("input.txt")).mapToInt(Integer::valueOf)) {
            var result = "1".equals(System.getenv("solution")) ?
                    numbers.sum() :
                    numbers.reduce(1, Math::multiplyExact);
            System.out.println(result);
        }
    }
}