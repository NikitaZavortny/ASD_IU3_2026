package com.example;

import java.io.PrintStream;
import java.util.Scanner;

/** Задача 1: минимум и максимум массива за один проход. */
public final class MinMax {
    //констатны
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 99_999;

    private MinMax() {}

    //Нахождение минимума и максимума
    public static int[] find(int[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Массив не должен быть null");
        }
        validateLength(values.length);
        int minimum = values[0];
        int maximum = values[0];
        for (int index = 1; index < values.length; index++) {
            if (values[index] < minimum) {
                minimum = values[index];
            }
            if (values[index] > maximum) {
                maximum = values[index];
            }
        }
        return new int[] {minimum, maximum};
    }

    //проверка на выход из диапазона
    private static void validateLength(int length) {
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new IllegalArgumentException("Требуется 1 < n < 100000");
        }
    }

    //Читает n и массив; печатает минимум и максимум через пробел.
    public static void run(Scanner scanner, PrintStream output) {
        int length = scanner.nextInt();
        validateLength(length);
        int[] values = new int[length];
        for (int index = 0; index < length; index++) {
            values[index] = scanner.nextInt();
        }
        int[] result = find(values);
        output.println(result[0] + " " + result[1]);
    }
    
    //запуск программы
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }
}
