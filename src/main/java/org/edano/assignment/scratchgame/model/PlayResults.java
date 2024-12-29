package org.edano.assignment.scratchgame.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record PlayResults(Symbol[][] matrix, double reward, Map<Symbol, List<WinCombination>> appliedWinningCombinations, List<Symbol> appliedBonusSymbols) {
    public String[][] getSimpleMatrix() {
        final var result = new String[matrix.length][matrix[0].length];
        for(int row = 0; row < matrix.length; row++) {
            for(int col = 0; col < matrix[row].length; col++) {
                result[row][col] = matrix[row][col].name();
            }
        }
        return result;
    }

    public Map<Character, List<String>> getSimplifiedAppliedWinCombos() {
        return appliedWinningCombinations.entrySet().stream().map( entry -> {
            final var symbol = entry.getKey().name().charAt(0);
            final var appliedWinCombos = entry.getValue().stream().map(WinCombination::name).toList();
            return Map.entry(symbol, appliedWinCombos);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
