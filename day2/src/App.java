import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws IOException {
        RuleValidator validator = "1".equals(System.getenv("solution")) ?
                App::compliesWithRuleSolution1 :
                App::compliesWithRuleSolution2;

        try (var lines = Files.lines(Paths.get("input.txt"))) {
            var validPasswords = lines.map(RuleAndPassword::from)
                    .filter(ruleAndPassword -> validator.compliesWithRule(ruleAndPassword.password(), ruleAndPassword.rule()))
                    .count();
            System.out.println(validPasswords);
        }
    }

    record RuleAndPassword(PasswordRule rule, String password) {
        static RuleAndPassword from(String line) {
            var split = line.split(": ");
            return new RuleAndPassword(PasswordRule.from(split[0]), split[1]);
        }
    }

    record PasswordRule(Positions positions, Character character) {
        static PasswordRule from(String encoded) {
            var split = encoded.split("[\\s-]+");
            var range = new Positions(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            return new PasswordRule(range, split[2].charAt(0));
        }
    }

    record Positions(int low, int high) {
        boolean includes(long i) {
            return i >= low && i <= high;
        }
    }

    @FunctionalInterface
    interface RuleValidator {
        boolean compliesWithRule(String password, PasswordRule rule);
    }

    static boolean compliesWithRuleSolution1(String password, PasswordRule rule) {
        var count = password.chars().filter(c -> rule.character == c).count();
        return rule.positions.includes(count);
    }

    static boolean compliesWithRuleSolution2(String password, PasswordRule rule) {
        var lowChar = password.charAt(rule.positions.low - 1);
        var highChar = password.charAt(rule.positions.high - 1);

        return (lowChar == rule.character || highChar == rule.character) && lowChar != highChar;
    }
}