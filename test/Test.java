import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class Test {

    static final Map<String, Answer> ANSWERS = Map.ofEntries(
            entry("day1", new Answer("211899", "275765682")),
            entry("day2", new Answer("410", "694"))
    );

    public static void main(String[] args) throws IOException {
        var days = findDays();
        testDays(days);
    }

    private static void testDays(Stream<Path> days) {
        var correct = new LongAdder();
        var errors = new ArrayList<String>();

        days.forEach(day -> {
            System.out.println("Testing " + day);
            try {
                var directory = day.getFileName().toString();
                var expectedAnswer = ANSWERS.get(directory);
                var answer = executeDay(day);

                if (!expectedAnswer.equals(answer)) {
                    errors.add("%s produced wrong answer %s, expected %s%n".formatted(directory, answer, expectedAnswer));
                } else {
                    correct.increment();
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        if (!errors.isEmpty()) {
            errors.forEach(System.err::println);
        } else {
            System.out.println("Got " + correct.intValue() + " answers correct!");
        }
    }

    private static Answer executeDay(Path day) throws IOException, InterruptedException {
        new ProcessBuilder("docker build -t aoc .".split(" "))
                .directory(day.toFile())
                .inheritIO()
                .start()
                .waitFor();

        final String solution1 = runTest(day, 1);
        final String solution2 = runTest(day, 2);

        return new Answer(solution1, solution2);
    }

    private static String runTest(Path day, int number) throws IOException, InterruptedException {
        final Process solution1Process = new ProcessBuilder(List.of("docker", "run", "-e", "solution=" + number, "aoc"))
                .directory(day.toFile())
                .start();

        final String solution = new String(solution1Process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
        solution1Process.waitFor();
        return solution;
    }

    private static Stream<Path> findDays() throws IOException {
        return Files.find(Paths.get("../"), 2, (path, attr) -> path.getFileName().toString().startsWith("day") && Files.isDirectory(path));
    }

    record Answer(String solution1, String solution2) {
    }
}
