package org.edano.assignment.scratchgame.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;

public record Config(Optional<Integer> columns,
                     Optional<Integer> rows,
                     Map<String, SymbolProps> symbols,
                     ProbabilitiesProps probabilities,
                     @JsonProperty("win_combinations")
                     Map<String, WinCombinationProps> winCombinations) {

    public Config(int cols, int rows, Map<String, SymbolProps> symbols, ProbabilitiesProps probabilities,  Map<String, WinCombinationProps> winCombinations) {
        this(Optional.of(cols), Optional.of(rows), symbols, probabilities, winCombinations);
    }
}
