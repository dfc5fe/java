import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class LabTasks {

    // Перші літери слів
    public String getFirstLettersString(List<String> words) {
        return words.stream()
                .map(s -> s.substring(0, 1))
                .collect(Collectors.joining());
    }

    // Факторіал
    public long calculateFactorial(int n) {
        if (n <= 1) return 1;
        return LongStream.rangeClosed(1, n)
                .reduce(1, (a, b) -> a * b);
    }

    // Всі унікальні мови команди
    public List<String> getAllUniqueLanguages(List<Developer> team) {
        return team.stream()
                .map(Developer::getLanguages)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    // Логіка пошуку досконалих чисел
    private boolean isPerfect(int n) {
        if (n < 2) return false;
        int sum = IntStream.rangeClosed(1, n / 2)
                .filter(i -> n % i == 0)
                .sum();
        return sum == n;
    }

    public List<Integer> findPerfectNumbers(int maxLimit) {
        return IntStream.rangeClosed(1, maxLimit)
                .filter(this::isPerfect)
                .boxed()
                .collect(Collectors.toList());
    }

    // Асинхронний запуск через CompletableFuture
    public CompletableFuture<List<Integer>> findPerfectNumbersAsync(int maxLimit) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("[Thread-" + Thread.currentThread().getId() + "] Початок пошуку досконалих чисел...");
            return findPerfectNumbers(maxLimit);
        });
    }
}