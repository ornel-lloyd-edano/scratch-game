package org.edano.assignment.scratchgame.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ProbabilitiesProps(
        @JsonProperty("standard_symbols")
        List<StandardSymbolProbabilitiesProps> standardSymbols,
        @JsonProperty("bonus_symbols")
        BonusSymbolsProps bonusSymbols
) {
}
