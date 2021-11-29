import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Predicate;

public class App {
    public static void main(String[] args) throws IOException {
        Predicate<PolicyAndPassword> test = "1".equals(System.getenv("solution")) ?
                PolicyAndPassword::isValidSolution1 :
                PolicyAndPassword::isValidSolution2;

        try (var lines = Files.lines(Paths.get("input.txt"))) {
            var validPasswords = lines.map(PolicyAndPassword::from)
                    .filter(test)
                    .count();
            System.out.println(validPasswords);
        }
    }

    record PolicyAndPassword(PasswordPolicy policy, String password) {
        static PolicyAndPassword from(String line) {
            var split = line.split(": ");
            return new PolicyAndPassword(PasswordPolicy.from(split[0]), split[1]);
        }

        boolean isValidSolution1() {
            var count = password.chars().filter(c -> policy.character == c).count();
            return policy.positions.includes(count);
        }

        boolean isValidSolution2() {
            var lowChar = password.charAt(policy.positions.low - 1);
            var highChar = password.charAt(policy.positions.high - 1);

            return (lowChar == policy.character || highChar == policy.character) && lowChar != highChar;
        }
    }

    record PasswordPolicy(Positions positions, Character character) {
        static PasswordPolicy from(String encoded) {
            var split = encoded.split("[\\s-]+");
            var range = new Positions(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            return new PasswordPolicy(range, split[2].charAt(0));
        }
    }

    record Positions(int low, int high) {
        boolean includes(long i) {
            return i >= low && i <= high;
        }
    }
}