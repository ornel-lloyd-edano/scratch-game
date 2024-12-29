package org.edano.assignment.scratchgame.model;

import java.util.Optional;

import static org.edano.utils.MatrixUtils.isExactlySizeOf;
import static org.edano.utils.MatrixUtils.isWithinBounds;

public record Matrix(int numRows, int numCols, Symbol[][] symbols) {

    public Optional<Symbol> getSymbol(int row, int col) {
        return Optional.ofNullable(symbols).flatMap( nonNullSymbols -> {
            if (isWithinBounds(nonNullSymbols, row, col)) {
                return Optional.ofNullable(nonNullSymbols[row][col]);
            } else {
                return Optional.empty();
            }
        });
    }

    public boolean isMatrixValid() {
        boolean basicCondition = (symbols != null) && isExactlySizeOf(symbols, numRows, numCols);
        if (basicCondition) {
            boolean noNullSymbols = true; // assumption
            outerLoop:
            for(Symbol[] row : symbols) {
                for(Symbol col : row) {
                    if (col == null) {
                        noNullSymbols = false;
                        break outerLoop;
                    }
                }
            }
            return noNullSymbols;
        } else {
            return false;
        }
    }
}
