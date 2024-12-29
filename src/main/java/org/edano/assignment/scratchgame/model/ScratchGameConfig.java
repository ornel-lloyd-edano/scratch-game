package org.edano.assignment.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public record ScratchGameConfig(
        Optional<Integer> columns,
        Optional<Integer> rows,
        List<Symbol> symbols,
        List<Object> probabilities,
        List<Object> winCombinations) {
}
