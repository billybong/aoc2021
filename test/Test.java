import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class Test {

    static final Map<String, Answer> ANSWERS = Map.ofEntries(
            entry("day1", new Answer("211899", "275765682")),
            entry("day2", new Answer("410", "694"))
    );

    public static void main(String[] args) throws IOException {
        var days = args.length == 1 ? Stream.of(findDay(Integer.parseInt(args[0]))) : findDays();
        testDays(days);
    }

    private static void testDays(Stream<Path> days) {
        var correct = new ArrayList<String>();
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
                    correct.add(day.getFileName().toString());
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        if (!errors.isEmpty()) {
            errors.forEach(System.err::println);
        } else {
            System.out.println("Got " + correct.size() + " answers correct! " + correct);
        }
    }

    private static Answer executeDay(Path day) throws IOException, InterruptedException {
        new ProcessBuilder("docker build -t aoc .".split(" "))
                .directory(day.toFile())
                .inheritIO()
                .start()
                .waitFor();

        var solution1 = runTest(day, 1);
        var solution2 = runTest(day, 2);

        return new Answer(solution1, solution2);
    }

    private static String runTest(Path day, int number) throws IOException, InterruptedException {
        var solution1Process = new ProcessBuilder(List.of("docker", "run", "-e", "solution=" + number, "aoc"))
                .directory(day.toFile())
                .start();

        var solution = new String(solution1Process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
        solution1Process.waitFor();
        return solution;
    }

    private static Path findDay(int day) throws IOException {
        BiPredicate<Path, BasicFileAttributes> filter = (path, attr) -> path.getFileName().toString().equals("day" + day) && Files.isDirectory(path);
        try (var pathStream = Files.find(Paths.get("../"), 2, filter)) {
            return pathStream.findFirst().orElseThrow();
        }
    }

    private static Stream<Path> findDays() throws IOException {
        return Files.find(Paths.get("../"), 2, (path, attr) -> path.getFileName().toString().startsWith("day") && Files.isDirectory(path));
    }

    record Answer(String solution1, String solution2) {
    }
}
