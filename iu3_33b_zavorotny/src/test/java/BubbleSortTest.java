package com.example;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки сортировки, счётчика обменов и досрочного выхода. */
class BubbleSortTest {
    @Test
    void shouldSortMixedValues() {
        int[] values = {3, 1, 2};
        long actual = BubbleSort.sort(values);
        assertArrayEquals(new int[] {1, 2, 3}, values);
        assertEquals(2L, actual);
    }

    @Test
    void shouldCountAllExchangesForReverseOrder() {
        int[] values = {4, 3, 2, 1};
        long actual = BubbleSort.sort(values);
        assertArrayEquals(new int[] {1, 2, 3, 4}, values);
        assertEquals(6L, actual);
    }

    @Test
    void shouldLeaveSortedArrayWithoutExchanges() {
        int[] values = {1, 2, 3, 4};
        long actual = BubbleSort.sort(values);
        assertArrayEquals(new int[] {1, 2, 3, 4}, values);
        assertEquals(0L, actual);
    }

    @Test
    void shouldNotSwapEqualElements() {
        int[] values = {5, 5, 5};
        long actual = BubbleSort.sort(values);
        assertArrayEquals(new int[] {5, 5, 5}, values);
        assertEquals(0L, actual);
    }

    @Test
    void shouldCountOnlyStrictInversionsWithDuplicates() {
        int[] values = {3, 1, 2, 1};
        long actual = BubbleSort.sort(values);
        assertArrayEquals(new int[] {1, 1, 2, 3}, values);
        assertEquals(4L, actual);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] values = {};
        long actual = BubbleSort.sort(values);
        assertArrayEquals(new int[] {}, values);
        assertEquals(0L, actual);
    }

    @Test
    void shouldHandleSingleElement() {
        int[] values = {7};
        long actual = BubbleSort.sort(values);
        assertArrayEquals(new int[] {7}, values);
        assertEquals(0L, actual);
    }

    @Test
    void shouldCompareIntegerBoundsWithoutSubtraction() {
        int[] values = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        long actual = BubbleSort.sort(values);
        assertArrayEquals(new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, values);
        assertEquals(3L, actual);
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(IllegalArgumentException.class, () -> BubbleSort.sort(null));
    }

    @Test
    void shouldMatchInversionCountOnRandomArrays() {
        Random random = new Random(15);
        for (int sample = 0; sample < 100; sample++) {
            int[] values = new int[20];
            for (int index = 0; index < values.length; index++) {
                values[index] = random.nextInt(21) - 10;
            }
            long expectedSwaps = countInversions(values);
            int[] expected = values.clone();
            Arrays.sort(expected);
            long actualSwaps = BubbleSort.sort(values);
            assertArrayEquals(expected, values);
            assertEquals(expectedSwaps, actualSwaps);
        }
    }

    private static long countInversions(int[] values) {
        long count = 0;
        for (int left = 0; left < values.length; left++) {
            for (int right = left + 1; right < values.length; right++) {
                if (values[left] > values[right]) {
                    count++;
                }
            }
        }
        return count;
    }

    @Test
    void shouldPrintExpectedResult() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner("3 3 1 2");
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            BubbleSort.run(scanner, output);
        }
        String actual = buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        assertEquals("1 2 3\n2\n", actual);
    }

    @Test
    void shouldPrintEmptyArrayAndZeroExchanges() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner("0");
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            BubbleSort.run(scanner, output);
        }
        String actual = buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        assertEquals("\n0\n", actual);
    }

    @Test
    void shouldRejectNegativeInputLength() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner("-1");
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            assertThrows(IllegalArgumentException.class, () -> BubbleSort.run(scanner, output));
        }
    }
}
