import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.stream.LongStream;

public class App {
    public static void main(String[] args) throws IOException {
        final int days = "part2".equals(System.getenv("part")) ? 256 : 80;
        try (final BufferedReader br = new BufferedReader(new FileReader("input.txt"))){
            System.out.println(solution(br.readLine(), days));
        }
    }
    private static long solution(String line, int days) {
        final long[] fishesByDaysUntilReproduction = new long[9];
        final StringTokenizer tokenizer = new StringTokenizer(line, ",");
        while (tokenizer.hasMoreElements()) {
            fishesByDaysUntilReproduction[Integer.parseInt(tokenizer.nextToken())]++;
        }

        long previousBucket = 0;
        for (int day = 0; day < days; day++) {
            for (int i = fishesByDaysUntilReproduction.length - 1; i > 0; i--) {
                final long tempBucket = fishesByDaysUntilReproduction[i];
                fishesByDaysUntilReproduction[i] = previousBucket;
                previousBucket = tempBucket;
            }
            fishesByDaysUntilReproduction[7] += previousBucket;
        }

        return LongStream.of(fishesByDaysUntilReproduction).sum();
    }
}