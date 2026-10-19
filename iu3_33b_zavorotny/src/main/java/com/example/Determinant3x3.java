package com.example;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

/** Задача 59: точный определитель матрицы 3 × 3 с элементами типа int. */
public final class Determinant3x3 {
    private static final int MATRIX_SIZE = 3;

    private Determinant3x3() {}

    //вычисляет определитель разложением по первой строке
    public static BigInteger calculate(int[][] matrix) {
        //проверки
        if (matrix == null || matrix.length != MATRIX_SIZE) {
            throw new IllegalArgumentException("Требуется матрица 3 × 3");
        }
        for (int[] row : matrix) {
            if (row == null || row.length != MATRIX_SIZE) {
                throw new IllegalArgumentException("Требуется матрица 3 × 3");
            }
        }
        // Миноры из двух int помещаются в long, но итоговый определитель может не поместиться.
        long firstMinor = (long) matrix[1][1] * matrix[2][2] - (long) matrix[1][2] * matrix[2][1];
        long secondMinor = (long) matrix[1][0] * matrix[2][2] - (long) matrix[1][2] * matrix[2][0];
        long thirdMinor = (long) matrix[1][0] * matrix[2][1] - (long) matrix[1][1] * matrix[2][0];
        //возвращаем результат
        return multiply(matrix[0][0], firstMinor)
                .subtract(multiply(matrix[0][1], secondMinor))
                .add(multiply(matrix[0][2], thirdMinor));
    }

    private static BigInteger multiply(int number, long minor) {
        return BigInteger.valueOf(number).multiply(BigInteger.valueOf(minor));
    }

    // Читает девять целых чисел по строкам и печатает определитель.
    public static void run(Scanner scanner, PrintStream output) {
        int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int column = 0; column < MATRIX_SIZE; column++) {
                matrix[row][column] = scanner.nextInt();
            }
        }
        output.println(calculate(matrix));
    }

    //запуск
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }
}
