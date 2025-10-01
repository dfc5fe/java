package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Обери режим:");
        System.out.println("1 – Лаба: пошук надпростих чисел (Emirp)");
        System.out.println("2 – Hard task: динамічне перевантаження класу TestModule");
        System.out.print("Твій вибір: ");

        int choice = sc.nextInt();

        if (choice == 1) {
            System.out.print("Введи число (≤1000): ");
            int n = sc.nextInt();

            var emirps = EmirpFinder.getEmirps(n);
            System.out.println("Кількість надпростих (emirp) чисел: " + emirps.size());
            System.out.println("Самі числа: " + emirps);
    } else if (choice == 2) {
            System.out.println("Запускаю DynamicClassReloader...");
            try {
                DynamicClassReloader.startWatching();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Невідомий вибір");
        }
    }
}
