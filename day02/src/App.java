import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) throws IOException {
        RuleValidator validator = "part1".equals(System.getenv("part")) ?
                App::compliesWithRulePart1 :
                App::compliesWithRulePart2;

        try (var lines = Files.lines(Paths.get("input.txt"))) {
            var validPasswords = lines.map(RuleAndPassword::from)
                    .filter(ruleAndPassword -> validator.compliesWithRule(ruleAndPassword.password(), ruleAndPassword.rule()))
                    .count();
            System.out.println(validPasswords);
        }
    }

    record RuleAndPassword(PasswordRule rule, String password) {
        static Pattern SEPARATOR = Pattern.compile(": ");

        static RuleAndPassword from(String line) {
            var split = SEPARATOR.split(line);
            return new RuleAndPassword(PasswordRule.from(split[0]), split[1]);
        }
    }

    record PasswordRule(Positions positions, Character character) {
        static Pattern SEPARATOR = Pattern.compile("[\\s-]+");

        static PasswordRule from(String encoded) {
            var split = SEPARATOR.split(encoded);
            var range = new Positions(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            return new PasswordRule(range, split[2].charAt(0));
        }
    }

    record Positions(int low, int high) {}

    @FunctionalInterface
    interface RuleValidator {
        boolean compliesWithRule(String password, PasswordRule rule);
    }

    static boolean compliesWithRulePart1(String password, PasswordRule rule) {
        var count = password.chars().filter(c -> rule.character == c).count();
        return count >= rule.positions.low && count <= rule.positions.high;
    }

    static boolean compliesWithRulePart2(String password, PasswordRule rule) {
        var lowChar = password.charAt(rule.positions.low - 1);
        var highChar = password.charAt(rule.positions.high - 1);

        return (lowChar == rule.character || highChar == rule.character) && lowChar != highChar;
    }
}