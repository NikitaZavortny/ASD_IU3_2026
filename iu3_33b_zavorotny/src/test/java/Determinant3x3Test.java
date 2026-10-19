package ru.university.acsd.matrices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки знака, вырожденности и точной арифметики. */
class Determinant3x3Test {
    @Test
    void shouldReturnOneForIdentityMatrix() {
        int[][] matrix = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        assertEquals(BigInteger.ONE, Determinant3x3.calculate(matrix));
    }

    @Test
    void shouldCalculateGeneralMatrix() {
        int[][] matrix = {{1, 2, 3}, {0, 4, 5}, {1, 0, 6}};
        assertEquals(BigInteger.valueOf(22), Determinant3x3.calculate(matrix));
    }

    @Test
    void shouldReturnZeroForDependentRows() {
        int[][] matrix = {{1, 2, 3}, {2, 4, 6}, {7, 8, 9}};
        assertEquals(BigInteger.ZERO, Determinant3x3.calculate(matrix));
    }

    @Test
    void shouldReturnNegativeDeterminantAfterRowSwap() {
        int[][] matrix = {{0, 1, 0}, {1, 0, 0}, {0, 0, 1}};
        assertEquals(BigInteger.valueOf(-1), Determinant3x3.calculate(matrix));
    }

    @Test
    void shouldAvoidOverflowForExtremeDiagonalValues() {
        int minimum = Integer.MIN_VALUE;
        int[][] matrix = {{minimum, 0, 0}, {0, minimum, 0}, {0, 0, minimum}};
        BigInteger expected = BigInteger.valueOf(minimum).pow(3);
        assertEquals(expected, Determinant3x3.calculate(matrix));
    }

    @Test
    void shouldSubtractLargeProductsWithoutOverflow() {
        int minimum = Integer.MIN_VALUE;
        int maximum = Integer.MAX_VALUE;
        int[][] matrix = {{1, 0, 0}, {0, minimum, maximum}, {0, minimum, minimum}};
        BigInteger expected =
                BigInteger.valueOf(minimum).multiply(BigInteger.valueOf(minimum).subtract(BigInteger.valueOf(maximum)));
        assertEquals(expected, Determinant3x3.calculate(matrix));
    }

    @Test
    void shouldRejectWrongRowCount() {
        assertThrows(
                IllegalArgumentException.class, () -> Determinant3x3.calculate(new int[][] {{1, 2, 3}, {4, 5, 6}}));
    }

    @Test
    void shouldRejectWrongColumnCount() {
        assertThrows(IllegalArgumentException.class, () -> Determinant3x3.calculate(new int[][] {{1}, {2}, {3}}));
    }

    @Test
    void shouldRejectEmptyMatrix() {
        assertThrows(IllegalArgumentException.class, () -> Determinant3x3.calculate(new int[0][]));
    }

    @Test
    void shouldPrintDeterminant() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner("1 2 3 0 4 5 1 0 6");
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            Determinant3x3.run(scanner, output);
        }
        assertEquals("22", buffer.toString(StandardCharsets.UTF_8).strip());
    }

    @Test
    void shouldRejectNullMatrix() {
        assertThrows(IllegalArgumentException.class, () -> Determinant3x3.calculate(null));
    }

    @Test
    void shouldRejectNullRow() {
        int[][] matrix = {{1, 0, 0}, null, {0, 0, 1}};
        assertThrows(IllegalArgumentException.class, () -> Determinant3x3.calculate(matrix));
    }
}
