import java.util.concurrent.ThreadLocalRandom;

class RingBuffer {
    private final String[] buffer;
    private int head = 0;
    private int tail = 0;
    private int count = 0;
    private final int capacity;

    public RingBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new String[capacity];
    }

    // Метод додавання (Producer)
    public synchronized void put(String message) throws InterruptedException {
        while (count == capacity) {
            wait();
        }
        buffer[tail] = message;
        tail = (tail + 1) % capacity;
        count++;
        notifyAll();
    }

    // Метод вичитування (Consumer)
    public synchronized String take() throws InterruptedException {
        while (count == 0) {
            wait();
        }
        String message = buffer[head];
        head = (head + 1) % capacity;
        count--;
        notifyAll();
        return message;
    }
}

public class RingBufferSystem {

    public static void runTask() {
        System.out.println("\n--- ЗАПУСК TASK 2: PRODUCER-CONSUMER ---");

        RingBuffer buffer1 = new RingBuffer(5);
        RingBuffer buffer2 = new RingBuffer(5);

        for (int i = 1; i <= 5; i++) {
            int threadNum = i;
            Thread producer = new Thread(() -> {
                while (true) {
                    try {
                        String msg = "Потік №" + threadNum + " згенерував повідомлення";
                        buffer1.put(msg);
                        Thread.sleep(ThreadLocalRandom.current().nextInt(10, 30));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            producer.setDaemon(true); // Важливо!
            producer.start();
        }

        // 2. Потоки-перекладачі (2 шт) -> з buffer1 в buffer2
        for (int i = 1; i <= 2; i++) {
            int threadNum = i;
            Thread mediator = new Thread(() -> {
                while (true) {
                    try {
                        String received = buffer1.take();
                        String transformed = "Потік №" + threadNum + " переклав повідомлення [" + received + "]";
                        buffer2.put(transformed);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            mediator.setDaemon(true); // Важливо!
            mediator.start();
        }

        // 3. Головний потік читає 100 повідомлень з buffer2
        try {
            for (int i = 1; i <= 100; i++) {
                String finalMessage = buffer2.take();
                System.out.println("Main отримав (" + i + "/100): " + finalMessage);
            }
            System.out.println("Головний потік завершив вичитку 100 повідомлень.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}