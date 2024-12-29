package org.edano.assignment.scratchgame.model;

import org.edano.assignment.scratchgame.config.WinCombinationProps;

import java.util.List;
import java.util.Optional;

public record WinCombination(String name, double rewardMultiplier, ComboType when, ComboGroup group,
                             Optional<Integer> count,
                             Optional<List<List<String>>> coveredAreas) {

    public WinCombination(WinCombinationProps props, String name) {
        this(name, props.rewardMultiplier(), ComboType.valueOf(props.when().toUpperCase()),
                ComboGroup.valueOf(props.group().toUpperCase()), props.count(), props.coveredAreas());
    }

    public WinCombination(String name, double rewardMultiplier, int count) {
        this(name, rewardMultiplier, ComboType.SAME_SYMBOLS, ComboGroup.SAME_SYMBOLS, Optional.of(count), Optional.empty());
    }

    public WinCombination(String name, double rewardMultiplier, String when, String group, List<List<String>> coveredAreas) {
        this(name, rewardMultiplier, ComboType.valueOf(when.toUpperCase()), ComboGroup.valueOf(group.toUpperCase()),
                Optional.empty(), Optional.of(coveredAreas));
    }

}
