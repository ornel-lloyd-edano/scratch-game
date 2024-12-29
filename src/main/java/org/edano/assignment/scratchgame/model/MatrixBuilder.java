package org.edano.assignment.scratchgame.model;

import org.edano.assignment.scratchgame.config.Config;

import java.util.ArrayList;
import java.util.Random;

public class MatrixBuilder {
    private final Config config;

    public MatrixBuilder(Config config) {
        this.config = config;
    }

    public Matrix build() {
        final var numRows = config.rows().orElseGet(()-> {
            final var maxRowIndex = config.probabilities().standardSymbols()
                    .stream().map(symbol -> symbol.row()).max(Integer::compareTo)
                    .orElse(0);
            return maxRowIndex + 1;
        });
        final var numColumns = config.columns().orElseGet(()-> {
            final var maxColIndex = config.probabilities().standardSymbols()
                    .stream().map(symbol -> symbol.column()).max(Integer::compareTo)
                    .orElse(0);
            return maxColIndex + 1;
        });

        final var symbols = new Symbol[numRows][numColumns];
        for(int r = 0; r < numRows; r++) {
            for(int c = 0; c < numColumns; c++) {
                final var row = r;
                final var column = c;
                final var symbolProbability = config.probabilities().standardSymbols()
                        .stream().filter(symbol -> symbol.row() == row && symbol.column() == column).findAny().get();
                final var symbolRaffleBox = new ArrayList<String>();
                symbolProbability.symbols().entrySet().stream().forEach( entry -> {
                    final var symbolOnly = entry.getKey();
                    final var numDuplicates = entry.getValue();
                    for(int n = 0; n < numDuplicates; n++) {
                        symbolRaffleBox.add(symbolOnly);
                    }
                });
                // get a random element from the symbolRaffleBag
                final var randomPosition = new Random().nextInt(symbolRaffleBox.size());
                final var randomSymbol = symbolRaffleBox.get(randomPosition);
                symbols[row][column] = new Symbol(config.symbols().get(randomSymbol), randomSymbol);

            }
        }

        // create a bonus
        final var bonusSymbolRaffleBox = new ArrayList<String>();
        config.probabilities().bonusSymbols().symbols().entrySet().stream().forEach( entry -> {
            final var symbolOnly = entry.getKey();
            final var numDuplicates = entry.getValue();
            for(int n = 0; n < numDuplicates; n++) {
                bonusSymbolRaffleBox.add(symbolOnly);
            }
        });
        final var randomBonusPosition = new Random().nextInt(bonusSymbolRaffleBox.size());
        final var randomBonusSymbol = bonusSymbolRaffleBox.get(randomBonusPosition);
        final var randomRow = new Random().nextInt(numRows);
        final var randomCol = new Random().nextInt(numColumns);

        symbols[randomRow][randomCol] = new Symbol(config.symbols().get(randomBonusSymbol), randomBonusSymbol);
        return new Matrix(numRows, numColumns, symbols);
    }
}
