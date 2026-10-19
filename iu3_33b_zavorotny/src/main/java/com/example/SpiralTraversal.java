package com.example;

import java.io.PrintStream;
import java.util.Scanner;

/** Задача 57: обход матрицы по спирали по часовой стрелке. */
public final class SpiralTraversal {
    private SpiralTraversal() {}

    //Возвращает элементы по спирали, не изменяя исходную матрицу.
    public static int[] traverse(int[][] matrix) {
        int columns = columnCount(matrix);
        if ((long) matrix.length * columns > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Слишком много элементов");
        }
        int[] result = new int[matrix.length * columns];
        int index = 0;
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = columns - 1;
        while (top <= bottom && left <= right) {
            index = appendLayer(matrix, result, index, top, bottom, left, right);
            top++;
            bottom--;
            left++;
            right--;
        }
        return result;
    }

    private static int appendLayer(int[][] matrix, int[] result, int index, int top, int bottom, int left, int right) {
        for (int column = left; column <= right; column++) {
            result[index++] = matrix[top][column];
        }
        for (int row = top + 1; row <= bottom; row++) {
            result[index++] = matrix[row][right];
        }
        // При одной оставшейся строке нижнюю сторону повторно не обходим.
        if (top < bottom) {
            for (int column = right - 1; column >= left; column--) {
                result[index++] = matrix[bottom][column];
            }
        }
        // При одном столбце левая и правая стороны совпадают.
        if (left < right) {
            for (int row = bottom - 1; row > top; row--) {
                result[index++] = matrix[row][left];
            }
        }
        return index;
    }

    private static int columnCount(int[][] matrix) {

        //проверки
        if (matrix == null) {
            throw new IllegalArgumentException("Матрица не должна быть null");
        }
        if (matrix.length == 0) {
            return 0;
        }
        if (matrix[0] == null) {
            throw new IllegalArgumentException("Строка не должна быть null");
        }
        int columns = matrix[0].length;
        for (int[] row : matrix) {
            if (row == null || row.length != columns) {
                throw new IllegalArgumentException("Матрица должна быть прямоугольной");
            }
        }
        return columns;
    }

    //Читает n, m и матрицу, затем печатает обход одной строкой.
    public static void run(Scanner scanner, PrintStream output) {
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        if (rows < 0 || columns < 0) {
            throw new IllegalArgumentException("Размеры не могут быть отрицательными");
        }
        int[][] matrix = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                matrix[row][column] = scanner.nextInt();
            }
        }
        int[] result = traverse(matrix);
        for (int index = 0; index < result.length; index++) {
            if (index > 0) {
                output.print(' ');
            }
            output.print(result[index]);
        }
        output.println();
    }

    //запуск
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }
}
