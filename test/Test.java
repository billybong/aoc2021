import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class Test {

    static final Map<String, Answer> ANSWERS = Map.ofEntries(
            entry("day01", new Answer("1301", "1346")),
            entry("day02", new Answer("2027977", "1903644897")),
            entry("day03", new Answer("4001724", "587895")),
            entry("day06", new Answer("4001724", "587895"))
    );

    public static void main(String[] args) throws IOException {
        var days = args.length == 1 ? Stream.of(findDay(Integer.parseInt(args[0]))) : findAllDays();
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
                if (expectedAnswer != null) {
                    var answer = executeDay(day);

                    if (!expectedAnswer.equals(answer)) {
                        errors.add("%s produced wrong answer %s, expected %s%n".formatted(directory, answer, expectedAnswer));
                    } else {
                        correct.add(day.getFileName().toString());
                    }
                } else {
                    errors.add("%s doesn't have an expected answer!".formatted(directory));
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
        var solution1 = runTest(day, "graal", 1);
        var solution2 = runTest(day, "graal", 2);

        if (Files.exists(day.resolve("Dockerfile.openjdk"))) {
            new ProcessBuilder("docker build -t aoc -f Dockerfile.openjdk .".split(" "))
                    .directory(day.toFile())
                    .inheritIO()
                    .start()
                    .waitFor();
            runTest(day, "openjdk", 1);
            runTest(day, "openjdk", 2);
        }

        return new Answer(solution1, solution2);
    }

    private static String runTest(Path day, String platform, int part) throws IOException, InterruptedException {
        System.out.println("Starting docker run day: " + day + " part: " + part + " on " + platform);
        var process = new ProcessBuilder(List.of("docker", "run", "-e", "part=part" + part, "-e", "DEBUG=true", "aoc"))
                .directory(day.toFile())
                .start();

        var solution = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
        process.waitFor();
        final String errorMsg = new String(process.getErrorStream().readAllBytes(), StandardCharsets.UTF_8).trim();
        if (errorMsg != null && !errorMsg.isEmpty()) {
            System.out.println("DEBUG: " + errorMsg);
        }
        return solution;
    }

    private static Path findDay(int day) throws IOException {
        var dayName = "day" + (day < 10 ? "0" + day : String.valueOf(day));

        return findAllDays()
                .filter(path -> path.getFileName().toString().equals(dayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find " + dayName));
    }

    private static Stream<Path> findAllDays() throws IOException {
        return Files.find(Paths.get("../"), 2, (path, attr) -> path.getFileName().toString().startsWith("day") && Files.isDirectory(path))
                .filter(dir -> Files.exists(dir.resolve("Dockerfile")));
    }

    record Answer(String part1, String part2) {
    }
}
