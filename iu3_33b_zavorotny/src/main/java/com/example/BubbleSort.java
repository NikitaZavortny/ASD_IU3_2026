package com.example;

import java.io.PrintStream;
import java.util.Scanner;

/** Задача 15: пузырьковая сортировка с подсчётом обменов и досрочным выходом. */
public final class BubbleSort {
    private BubbleSort() {}

    //Сортировка переданного массива по возрастанию
    public static long sort(int[] values) {
        //проверка на null
        if (values == null) {
            throw new IllegalArgumentException("Массив не должен быть null");
        }
        long swaps = 0;
        //пузырек + счет
        for (int end = values.length - 1; end > 0; end--) {
            boolean changed = false;
            for (int index = 0; index < end; index++) {
                if (values[index] > values[index + 1]) {
                    int temporary = values[index];
                    values[index] = values[index + 1];
                    values[index + 1] = temporary;
                    swaps++;
                    changed = true;
                }
            }
            if (!changed) {
                break;
            }
        }
        return swaps;
    }

    //Читает n и массив; печатает нужный результат.
    public static void run(Scanner scanner, PrintStream output) {
        int length = scanner.nextInt();
        if (length < 0) {
            throw new IllegalArgumentException("Длина не может быть отрицательной");
        }
        int[] values = new int[length];
        for (int index = 0; index < length; index++) {
            values[index] = scanner.nextInt();
        }
        long swaps = sort(values);
        for (int index = 0; index < length; index++) {
            if (index > 0) {
                output.print(' ');
            }
            output.print(values[index]);
        }
        output.println();
        output.println(swaps);
    }

    //запуск
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }
}
