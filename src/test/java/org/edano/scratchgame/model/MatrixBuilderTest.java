package org.edano.scratchgame.model;

import org.edano.assignment.scratchgame.model.MatrixBuilder;
import org.edano.assignment.scratchgame.json.Formatter;
import org.edano.assignment.scratchgame.model.Symbol;
import org.edano.assignment.scratchgame.model.SymbolType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixBuilderTest {

    @Test
    public void testMatrixBuilder() throws Exception {

        final var configJsonBytes = getClass().getClassLoader().getResourceAsStream("config.json").readAllBytes();
        final var configJson = new String(configJsonBytes);
        final var config = Formatter.readConfigJson(configJson).get();

        final var matrix = new MatrixBuilder(config).build();
        // matrix should be filled with symbols (no nulls) as per the size of the matrix
        assertEquals(matrix.isMatrixValid(), true);

        // should have only 1 bonus symbol
        final var bonusSymbolCount = countBonusSymbols(matrix.symbols());
        assertEquals(bonusSymbolCount, 1);
    }

    private int countBonusSymbols(Symbol[][] symbols) {
        int bonusSymbolCount = 0;
        for(int row = 0; row < symbols.length; row++) {
            for(int column = 0; column < symbols[row].length; column++) {
                if (symbols[row][column].type() == SymbolType.BONUS) bonusSymbolCount++;
            }
        }
        return bonusSymbolCount;
    }
}
