package com.example;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки направления заполнения и граничных размеров. */
class SnakeMatrixTest {
    @Test
    void shouldAlternateDirectionForRectangularMatrix() {
        int[][] expected = {{1, 2, 3, 4}, {8, 7, 6, 5}, {9, 10, 11, 12}};
        int[][] actual = SnakeMatrix.generate(3, 4);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFillSingleRow() {
        assertArrayEquals(new int[][] {{1, 2, 3}}, SnakeMatrix.generate(1, 3));
    }

    @Test
    void shouldFillSingleColumn() {
        assertArrayEquals(new int[][] {{1}, {2}, {3}}, SnakeMatrix.generate(3, 1));
    }

    @Test
    void shouldFillSingleCell() {
        assertArrayEquals(new int[][] {{1}}, SnakeMatrix.generate(1, 1));
    }

    @Test
    void shouldSupportZeroRows() {
        assertEquals(0, SnakeMatrix.generate(0, 4).length);
    }

    @Test
    void shouldSupportZeroColumns() {
        assertArrayEquals(new int[][] {{}, {}}, SnakeMatrix.generate(2, 0));
    }

    @Test
    void shouldRejectNegativeRows() {
        assertThrows(IllegalArgumentException.class, () -> SnakeMatrix.generate(-1, 2));
    }

    @Test
    void shouldRejectNegativeColumns() {
        assertThrows(IllegalArgumentException.class, () -> SnakeMatrix.generate(2, -1));
    }

    @Test
    void shouldRejectValuesBeyondIntegerRangeBeforeAllocation() {
        assertThrows(IllegalArgumentException.class, () -> SnakeMatrix.generate(50_000, 50_000));
    }

    @Test
    void shouldPrintSnakeWithoutTrailingSpaces() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner("2 3");
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            SnakeMatrix.run(scanner, output);
        }
        String expected = "1 2 3\n6 5 4\n";
        String actual = buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        assertEquals(expected, actual);
    }
}
