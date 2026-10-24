package com.example;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки спирали, оставшихся строк и столбцов, корректности входа. */
class SpiralTraversalTest {
    @Test
    void shouldTraverseSquare() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] expected = {1, 2, 3, 6, 9, 8, 7, 4, 5};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseWideMatrix() {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        int[] expected = {1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseTallMatrix() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int[] expected = {1, 2, 3, 6, 9, 12, 11, 10, 7, 4, 5, 8};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseSingleRowOnce() {
        int[][] matrix = {{1, 2, 3}};
        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseSingleColumnOnce() {
        int[][] matrix = {{1}, {2}, {3}};
        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseSingleCell() {
        int[][] matrix = {{7}};
        int[] expected = {7};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldTraverseEvenSquare() {
        int[][] matrix = {{1, 2}, {3, 4}};
        int[] expected = {1, 2, 4, 3};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldKeepZerosAndRepeatedNegativeValues() {
        int[][] matrix = {{0, -1}, {0, -1}};
        int[] expected = {0, -1, -1, 0};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldHandleZeroRows() {
        int[][] matrix = {};
        int[] expected = {};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldHandleZeroColumns() {
        int[][] matrix = {{}, {}};
        int[] expected = {};
        assertArrayEquals(expected, SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldPreserveInput() {
        int[][] matrix = {{1, 2}, {3, 4}};
        int[][] expected = {{1, 2}, {3, 4}};
        SpiralTraversal.traverse(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    void shouldRejectNullMatrix() {
        assertThrows(IllegalArgumentException.class, () -> SpiralTraversal.traverse(null));
    }

    @Test
    void shouldRejectNullFirstRow() {
        assertThrows(IllegalArgumentException.class, () -> SpiralTraversal.traverse(new int[][] {null}));
    }

    @Test
    void shouldRejectNullLaterRow() {
        assertThrows(IllegalArgumentException.class, () -> SpiralTraversal.traverse(new int[][] {{1}, null}));
    }

    @Test
    void shouldRejectRaggedMatrix() {
        assertThrows(IllegalArgumentException.class, () -> SpiralTraversal.traverse(new int[][] {{1}, {2, 3}}));
    }

    @Test
    void shouldRejectResultSizeOverflow() {
        int[][] matrix = new int[50_000][];
        Arrays.fill(matrix, new int[50_000]);
        assertThrows(IllegalArgumentException.class, () -> SpiralTraversal.traverse(matrix));
    }

    @Test
    void shouldPrintExpectedResult() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner("2 3 1 2 3 4 5 6");
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            SpiralTraversal.run(scanner, output);
        }
        String actual = buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        assertEquals("1 2 3 6 5 4\n", actual);
    }

    @Test
    void shouldPrintEmptyTraversal() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner("0 3");
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            SpiralTraversal.run(scanner, output);
        }
        String actual = buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        assertEquals("\n", actual);
    }

    @Test
    void shouldRejectNegativeInputSizes() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner negativeRows = new Scanner("-1 2");
                Scanner negativeColumns = new Scanner("2 -1");
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            assertThrows(IllegalArgumentException.class, () -> SpiralTraversal.run(negativeRows, output));
            assertThrows(IllegalArgumentException.class, () -> SpiralTraversal.run(negativeColumns, output));
        }
    }
}
