import java.util.concurrent.RecursiveTask;

public class ArraySumTask extends RecursiveTask<Long> {
    private final int[] array;
    private final int start;
    private final int end;
    // Поріг, при якому переходимо на послідовне обчислення (за умовою < 20)
    private static final int THRESHOLD = 20;

    public ArraySumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        // Якщо кількість елементів менша або дорівнює порогу — звичайний цикл
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Інакше на дві частини
            int mid = start + (end - start) / 2;

            ArraySumTask leftTask = new ArraySumTask(array, start, mid);
            ArraySumTask rightTask = new ArraySumTask(array, mid, end);

            // Запускаємо ліву частину асинхронно
            leftTask.fork();

            // Праву частину рахуємо в поточному потоці (оптимізація)
            long rightResult = rightTask.compute();

            // Чекаємо результат лівої частини
            long leftResult = leftTask.join();

            return leftResult + rightResult;
        }
    }
}