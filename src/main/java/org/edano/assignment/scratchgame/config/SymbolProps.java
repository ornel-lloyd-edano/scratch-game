package org.edano.assignment.scratchgame.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public record SymbolProps(@JsonProperty(value = "reward_multiplier")
                          Optional<Double> rewardMultiplier,
                          String type,
                          Optional<Integer> extra,
                          Optional<String> impact) {

    public SymbolProps(double rewardMultiplier,  String type) {
        this(Optional.of(rewardMultiplier), type, Optional.empty(), Optional.empty());
    }

    public SymbolProps(double rewardMultiplier,  String type, String impact) {
        this(Optional.of(rewardMultiplier), type, Optional.empty(), Optional.of(impact));
    }

    public SymbolProps(int extra,  String type, String impact) {
        this(Optional.empty(), type, Optional.of(extra), Optional.of(impact));
    }

    public SymbolProps() {
        this(Optional.empty(), "bonus", Optional.empty(), Optional.of("miss"));
    }
}
