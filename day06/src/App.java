import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.LongStream;

public class App {
    public static void main(String[] args) throws IOException {
        final int days = "part2".equals(System.getenv("part")) ? 256 : 80;
        try (final BufferedReader br = new BufferedReader(new FileReader("input.txt"))){
            System.out.println(solution(br.readLine(), days));
        }
    }
    private static long solution(String line, int days) {
        final String[] initialState = line.split(",");
        final long[] fishesByDaysUntilReproduction = new long[9];

        for (final String state : initialState) {
            fishesByDaysUntilReproduction[Integer.parseInt(state)]++;
        }

        long previousBucket = 0;
        for (int day = 0; day < days; day++) {
            for (int i = fishesByDaysUntilReproduction.length - 1; i >= 0; i--) {
                if (i == 0) {
                    fishesByDaysUntilReproduction[7] += previousBucket;
                    fishesByDaysUntilReproduction[0] = 0;
                } else {
                    final long tempBucket = fishesByDaysUntilReproduction[i];
                    fishesByDaysUntilReproduction[i] = previousBucket;
                    previousBucket = tempBucket;
                }
            }
        }

        return LongStream.of(fishesByDaysUntilReproduction).sum();
    }
}