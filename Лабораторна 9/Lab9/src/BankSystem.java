import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Account {
    private final int id;
    private int balance;
    // AtomicInteger для безпечної генерації ID у багатопотоковому середовищі
    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    public Account(int initialBalance) {
        this.id = idGenerator.incrementAndGet();
        this.balance = initialBalance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }
}

public class BankSystem {

    public void transfer(Account from, Account to, int amount) {
        if (from.getId() == to.getId()) return;

        // ІЄРАРХІЧНЕ БЛОКУВАННЯ для уникнення Deadlock
        // Завжди блокується спочатку менший ID, потім більший
        Account firstLock = from.getId() < to.getId() ? from : to;
        Account secondLock = from.getId() < to.getId() ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                }
            }
        }
    }

    public long getTotalBalance(List<Account> accounts) {
        long total = 0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    // Метод для запуску демонстрації
    public static void runTask() {
        System.out.println("\n--- ЗАПУСК TASK 1: BANK SYSTEM ---");
        BankSystem bank = new BankSystem();
        List<Account> accounts = new ArrayList<>();

        int numberOfAccounts = 10;
        int initialBalance = 1000;
        int numberOfThreads = 2000;

        // 1. Створення рахунків
        for (int i = 0; i < numberOfAccounts; i++) {
            accounts.add(new Account(initialBalance));
        }

        // 2. Баланс ДО
        long totalBefore = bank.getTotalBalance(accounts);
        System.out.println("Загальний баланс ДО переказів: " + totalBefore);

        // 3. Запуск потоків
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Random random = new Random();

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                Account from = accounts.get(random.nextInt(numberOfAccounts));
                Account to = accounts.get(random.nextInt(numberOfAccounts));
                int amount = random.nextInt(100);
                bank.transfer(from, to, amount);
            });
        }

        executor.shutdown();
        try {
            // Чекаємо поки всі потоки закінчать роботу
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        // 4. Баланс після
        long totalAfter = bank.getTotalBalance(accounts);
        System.out.println("Загальний баланс ПІСЛЯ переказів: " + totalAfter);

        if (totalBefore == totalAfter) {
            System.out.println("[УСПІХ] Баланс збігається. Дедлоків не виявлено.");
        } else {
            System.out.println("[ПОМИЛКА] Баланс не збігається!");
        }
    }
}