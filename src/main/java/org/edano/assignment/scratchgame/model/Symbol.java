package org.edano.assignment.scratchgame.model;

import org.edano.assignment.scratchgame.config.SymbolProps;

import java.util.Optional;

public record Symbol(String name, SymbolType type, Optional<Double> rewardMultiplier,
                     Optional<Integer> extra, Optional<ImpactType> impact) {

    public Symbol(SymbolProps symbolProps, String name) {
        this(name, SymbolType.valueOf(symbolProps.type().toUpperCase()), symbolProps.rewardMultiplier(), symbolProps.extra(),
                symbolProps.impact().map(impact -> ImpactType.valueOf(impact.toUpperCase())));
    }

    public static Symbol getStandard(String name, double rewardMultiplier) {
        return new Symbol(name, SymbolType.STANDARD, Optional.of(rewardMultiplier), Optional.empty(), Optional.empty());
    }

    public static Symbol getBonus(String name, int extra) {
        return new Symbol(name, SymbolType.BONUS, Optional.empty(), Optional.of(extra), Optional.of(ImpactType.EXTRA_BONUS));
    }

    public static Symbol getBonus(String name, double rewardMultiplier) {
        return new Symbol(name, SymbolType.BONUS, Optional.of(rewardMultiplier), Optional.empty(), Optional.of(ImpactType.MULTIPLY_REWARD));
    }
}
