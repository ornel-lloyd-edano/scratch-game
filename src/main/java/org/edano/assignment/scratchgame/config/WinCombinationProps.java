package org.edano.assignment.scratchgame.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public record WinCombinationProps(
        @JsonProperty("reward_multiplier")
        double rewardMultiplier,
        String when,
        String group,
        Optional<Integer> count,
        @JsonProperty("covered_areas")
        Optional<List<List<String>>> coveredAreas
) {
        public WinCombinationProps(double rewardMultiplier, String when, int count, String group) {
                this(rewardMultiplier, when, group, Optional.of(count), Optional.empty());
        }

        public WinCombinationProps(double rewardMultiplier, String when, String group, List<List<String>> coveredAreas) {
                this(rewardMultiplier, when, group, Optional.empty(), Optional.of(coveredAreas));
        }
}
