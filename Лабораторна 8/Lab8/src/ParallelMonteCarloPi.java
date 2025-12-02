import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelMonteCarloPi {

    public static void run(int numThreads) {
        // Кількість "кидків" точок
        long totalIterations = 1_000_000_000L;
        long iterationsPerThread = totalIterations / numThreads;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Long>> results = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        // Створюємо завдання для потоків
        for (int i = 0; i < numThreads; i++) {
            results.add(executor.submit(() -> {
                long pointsInCircle = 0;
                // ThreadLocalRandom прям дуже лучче швидче у багатопоточності, ніж Math.random()
                ThreadLocalRandom random = ThreadLocalRandom.current();

                for (long j = 0; j < iterationsPerThread; j++) {
                    double x = random.nextDouble();
                    double y = random.nextDouble();
                    // Перевірка: x^2 + y^2 <= 1
                    if (x * x + y * y <= 1.0) {
                        pointsInCircle++;
                    }
                }
                return pointsInCircle;
            }));
        }

        // результати
        long totalPointsInCircle = 0;
        try {
            for (Future<Long> result : results) {
                totalPointsInCircle += result.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        long endTime = System.currentTimeMillis();

        // Pi = 4 * (PointsInside / TotalPoints)
        double pi = 4.0 * totalPointsInCircle / totalIterations;

        // Виведення
        System.out.println("\n=== РЕЗУЛЬТАТ МОНТЕ-КАРЛО ===");
        System.out.printf("PI is %.5f%n", pi);
        System.out.println("THREADS " + numThreads);
        System.out.printf("ITERATIONS %,d%n", totalIterations);
        System.out.println("TIME " + (endTime - startTime) + "ms");
    }
}