package org.edano.assignment.scratchgame.json;

import java.util.List;
import java.util.Map;

public record SimpleOutput(String[][] matrix, Double reward,
                           Map<Character, List<String>> appliedWinningCombinations, String appliedBonusSymbol) {
}
