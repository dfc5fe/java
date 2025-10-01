package org.example;

import java.util.ArrayList;
import java.util.List;

public class EmirpFinder {

    // Перевірка на простоту
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // Перевертання числа
    public static int reverse(int n) {
        int rev = 0;
        while (n > 0) {
            rev = rev * 10 + n % 10;
            n /= 10;
        }
        return rev;
    }

    // Перевірка на emirp (надпросте)
    public static boolean isEmirp(int n) {
        return isPrime(n) && isPrime(reverse(n));
    }

    // Повертає список усіх emirp до limit
    public static List<Integer> getEmirps(int limit) {
        List<Integer> emirps = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isEmirp(i)) {
                emirps.add(i);
            }
        }
        return emirps;
    }

    // Підрахунок (залишив для зручності)
    public static int countEmirps(int limit) {
        return getEmirps(limit).size();
    }
}
