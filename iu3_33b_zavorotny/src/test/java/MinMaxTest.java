package ru.university.acsd.matrices;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки экстремумов и ограничения 1 < n < 100000. */
class MinMaxTest {
    @Test
    void shouldFindMixedExtremes() {
        int[] values = {3, -5, 9, 0};
        int[] expected = {-5, 9};
        assertArrayEquals(expected, MinMax.find(values));
    }

    @Test
    void shouldHandleTwoElements() {
        int[] values = {7, 2};
        int[] expected = {2, 7};
        assertArrayEquals(expected, MinMax.find(values));
    }

    @Test
    void shouldHandleNegativeValues() {
        int[] values = {-8, -2, -10};
        int[] expected = {-10, -2};
        assertArrayEquals(expected, MinMax.find(values));
    }

    @Test
    void shouldHandleEqualValues() {
        int[] values = {4, 4, 4};
        int[] expected = {4, 4};
        assertArrayEquals(expected, MinMax.find(values));
    }

    @Test
    void shouldHandleZeroValues() {
        int[] values = {0, 0};
        int[] expected = {0, 0};
        assertArrayEquals(expected, MinMax.find(values));
    }

    @Test
    void shouldHandleIntegerBounds() {
        int[] values = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        int[] expected = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        assertArrayEquals(expected, MinMax.find(values));
    }

    @Test
    void shouldAcceptMaximumAllowedLength() {
        int[] values = new int[99_999];
        values[values.length - 1] = 10;
        assertArrayEquals(new int[] {0, 10}, MinMax.find(values));
    }

    @Test
    void shouldPreserveInput() {
        int[] values = {3, 1, 2};
        MinMax.find(values);
        assertArrayEquals(new int[] {3, 1, 2}, values);
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(IllegalArgumentException.class, () -> MinMax.find(null));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> MinMax.find(new int[0]));
    }

    @Test
    void shouldRejectSingleElement() {
        assertThrows(IllegalArgumentException.class, () -> MinMax.find(new int[] {1}));
    }

    @Test
    void shouldRejectUpperExclusiveBound() {
        assertThrows(IllegalArgumentException.class, () -> MinMax.find(new int[100_000]));
    }

    @Test
    void shouldPrintExpectedResult() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner("4 3 -5 9 0");
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            MinMax.run(scanner, output);
        }
        String actual = buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        assertEquals("-5 9\n", actual);
    }

    @Test
    void shouldRejectInvalidLengthBeforeReadingElements() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner("1");
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            assertThrows(IllegalArgumentException.class, () -> MinMax.run(scanner, output));
        }
    }
}
