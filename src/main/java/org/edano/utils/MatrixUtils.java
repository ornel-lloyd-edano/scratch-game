package org.edano.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatrixUtils {

    public static <T> boolean isWithinBounds(T[][] matrix, int row, int col) {
        return (row <= matrix.length - 1) && (col <= matrix[0].length - 1);
    }

    public static <T> boolean isExactlySizeOf(T[][] matrix, int numRows, int numCols) {
        return numRows == matrix.length  && numCols == matrix[0].length;
    }

    public static <T> List<T> flattenMatrix(T[][] matrix) {
        final var list = new ArrayList<T>();
        for(int row = 0; row < matrix.length; row++) {
            for(int col = 0; col < matrix[row].length; col++) {
                list.add(matrix[row][col]);
            }
        }
        return List.copyOf(list);
    }

    public static <T> Set<T> flattenAsSet(List<List<T>> coveredAreas) {
        final var result = new HashSet<T>();
        coveredAreas.forEach( rows -> {
            rows.forEach( rowColPos -> {
                result.add(rowColPos);
            });
        });
        return Set.copyOf(result);
    }

    public static <T> Set<String> getPattern(T elem, T[][] matrix) {
        final var set = new HashSet<String>();
        for(int row = 0; row < matrix.length; row++) {
            for(int col = 0; col < matrix[row].length; col++) {
                if (elem.equals(matrix[row][col])) {
                    set.add(String.format("%d:%d", row, col));
                }
            }
        }
        return Set.copyOf(set);
    }
}
