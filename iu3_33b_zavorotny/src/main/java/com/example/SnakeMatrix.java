package com.example;

import java.io.PrintStream;
import java.util.Scanner;

/** Задача 58: заполнение прямоугольной матрицы змейкой по строкам. */
public final class SnakeMatrix {
    private SnakeMatrix() {}

    //Создаёт матрицу; первая строка заполняется слева направо.
    public static int[][] generate(int rows, int columns) {
        //проверка
        if (rows < 0 || columns < 0 || (long) rows * columns > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Недопустимые размеры матрицы");
        }

        int[][] matrix = new int[rows][columns];
        int number = 1;
        //заполняем
        for (int row = 0; row < rows; row++) {
            for (int position = 0; position < columns; position++) {
                int column = position;
                if (row % 2 != 0) {
                    column = columns - 1 - position;
                }
                matrix[row][column] = number++;
            }
        }
        return matrix;
    }

    //Читает n и m и печатает матрицу без приглашений к вводу.
    public static void run(Scanner scanner, PrintStream output) {
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        int[][] matrix = generate(rows, columns);
        for (int[] row : matrix) {
            for (int column = 0; column < row.length; column++) {
                if (column > 0) {
                    output.print(' ');
                }
                output.print(row[column]);
            }
            output.println();
        }
    }

    //запуск
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }
}
