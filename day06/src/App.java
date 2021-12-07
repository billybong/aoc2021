import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.stream.LongStream;

public class App {
    public static void main(String[] args) throws IOException {
        final int days = "part2".equals(System.getenv("part")) ? 256 : 80;
        try (final BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            System.out.println(solution(br.readLine(), days));
        }
    }

    private static long solution(String line, int days) {
        final long[] ages = new long[9];
        final StringTokenizer tokenizer = new StringTokenizer(line, ",");
        while (tokenizer.hasMoreElements()) {
            ages[Integer.parseInt(tokenizer.nextToken())]++;
        }

        for (int day = 0; day < days; day++) {
            final int groupAtDay0 = day % 9;
            // oh Java, why don't you have Python and Groovy's nth-to-last element accessors!?
            final int groupForNewborns = switch (groupAtDay0) {
                case 0 -> 7;
                case 1 -> 8;
                default -> groupAtDay0 - 2;
            };
            ages[groupForNewborns] += ages[groupAtDay0];
        }

        return LongStream.of(ages).sum();
    }
}