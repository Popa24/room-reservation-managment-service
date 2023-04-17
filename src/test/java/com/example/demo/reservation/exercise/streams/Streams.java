import java.util.*;

public class Streams {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4,6,5);
        List<String> strings = Arrays.asList("apple", "banana", "cherry", "radar", "level");

        // Task 1
        int sum = numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n * n)
                .sum();
        System.out.println("Task 1: " + sum);

        // Task 2
        List<String> result = strings.stream()
                .map(s -> s.substring(0, 1).toUpperCase())
                .toList();
        System.out.println("Task 2: " + result);

        // Task 3
        Optional<Integer> secondHighest = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst();
        System.out.println("Task 3: " + secondHighest.orElse(null));

        // Task 4
        List<String> uniqueStrings = strings.stream()
                .distinct()
                .toList();
        System.out.println("Task 4: " + uniqueStrings);

        // Task 5
        List<Double> squareRoots = numbers.stream()
                .filter(n -> n >= 0)
                .map(Math::sqrt)
                .toList();
        System.out.println("Task 5: " + squareRoots);

        // Task 6
        List<Integer> lengths = strings.stream()
                .map(String::length)
                .sorted(Comparator.reverseOrder())
                .toList();
        System.out.println("Task 6: " + lengths);

        // Task 7
        List<Integer> primes = numbers.stream()
                .filter(Streams::isPrime)
                .toList();
        System.out.println("Task 7: " + primes);

        // Task 8
        String concatenated = String.join("", strings);
        System.out.println("Task 8: " + concatenated);

        // Task 9
        OptionalDouble median = numbers.stream()
                .mapToInt(Integer::intValue)
                .sorted()
                .skip((numbers.size() - 1) / 2)
                .limit(2 - numbers.size() % 2)
                .average();
        System.out.println("Task 9: " + median.orElse(Double.NaN));

        // Task 10
        List<String> palindromes = strings.stream()
                .filter(Streams::isPalindrome)
                .toList();
        System.out.println("Task 10: " + palindromes);
    }

    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome(String input) {
        int left = 0;
        int right = input.length() - 1;

        while (left < right) {
            if (input.charAt(left) != input.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
