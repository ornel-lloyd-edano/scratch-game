package org.edano.assignment.scratchgame.model;

import java.util.*;
import java.util.stream.Collectors;

import static org.edano.utils.MatrixUtils.*;

public class ScratchGame {

    private final List<WinCombination> winCombinations;

    public ScratchGame(List<WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    public PlayResults play(Symbol[][] matrix, double bet) {

        final var symbols = flattenMatrix(matrix);

        final var sameSymbolWinCombinations = winCombinations.stream().filter( winCombinationProps -> {
            return winCombinationProps.when() == ComboType.SAME_SYMBOLS;
        }).map( winCombinationProps -> {
            return Map.entry(winCombinationProps.count().get(), winCombinationProps);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        final var sameSymbolWinComboProps = symbols.stream()
                .collect(Collectors.groupingBy(
                symbol -> symbol, // group by symbol
                Collectors.summingInt(symbol -> 1) // count occurrences by summing 1 for each
        )).entrySet().stream().filter( entry -> entry.getValue() >= 3) // getting only symbols which occur 3 times or more
                .map( entry -> Map.entry(entry.getKey(), Math.min(9,  entry.getValue()))) // in case the matrix is larger than 3x3 and a symbol occurs more than 9 times, we consider it as 9 anyway
                .map( entry -> {
                    return Optional.ofNullable(sameSymbolWinCombinations.get(entry.getValue())).map(winCombinationProps -> {
                        return Map.entry(entry.getKey(), winCombinationProps);
                    });
                }).filter(Optional::isPresent)
                .map(Optional::get) // check if this frequency count is eligible for winning (is in the config), if it is then get the rewardMultiplier otherwise do not include the symbol
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        final var linearPatternsSymbolWinCombinations = winCombinations.stream().filter( winCombination -> {
            return winCombination.when() == ComboType.LINEAR_SYMBOLS;
        }).map( winCombination -> {
            return winCombination.coveredAreas().map(linearPatterns -> {
                return linearPatterns.stream().map(pattern -> {
                    return Map.entry(Set.copyOf(pattern), winCombination);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }).orElse(Map.of());
        }).flatMap(map -> map.entrySet().stream()).toList();

        final var distinctStandardSymbols = symbols.stream().distinct()
                .filter(symbol -> symbol.rewardMultiplier().isPresent()).toList();

        final var appliedWinningCombo = new HashMap<Symbol, List<WinCombination>>();
        final var standardSymbolsTotalReward = distinctStandardSymbols.stream().reduce( 0.0, (accumulatedReward, symbol) -> {
            final var symbolRewardMultiplier = symbol.rewardMultiplier().orElse(1.0);
            final var symbolDuplicatesRewardMultiplier = Optional.ofNullable(sameSymbolWinComboProps.get(symbol))
                    .map(winCombinationProps -> {
                        final var existingWinCombinationsApplied = Optional.ofNullable(appliedWinningCombo.get(symbol)).orElse(List.of());
                        final var updatedWinCombinationsApplied = new ArrayList(existingWinCombinationsApplied);
                        updatedWinCombinationsApplied.add(winCombinationProps);
                        appliedWinningCombo.put(symbol, updatedWinCombinationsApplied);
                        return winCombinationProps.rewardMultiplier();
                    }).orElse(0.0);
            final var symbolPattern = getPattern(symbol, matrix);
            final var symbolArrangementRewardMultiplier = getLinearPatternWinComboMultiplier(symbolPattern, linearPatternsSymbolWinCombinations, symbol, appliedWinningCombo).orElse(1.0);
            final var subtotal = bet * symbolRewardMultiplier * symbolDuplicatesRewardMultiplier * symbolArrangementRewardMultiplier;
            return accumulatedReward + subtotal;
        }, (accumulatedReward1, accumulatedReward2) -> {
            return accumulatedReward1 + accumulatedReward2;
        });

        final var bonusSymbols = symbols.stream().filter( symbol -> symbol.type() == SymbolType.BONUS).toList();
        final var appliedBonusSymbols = new ArrayList<Symbol>();
        final var totalRewards = bonusSymbols.stream().reduce( standardSymbolsTotalReward, (accumulatedReward, bonusSymbol) -> {
            return bonusSymbol.impact().map( impactType -> {
                if (standardSymbolsTotalReward > 0) {
                    appliedBonusSymbols.add(bonusSymbol);
                    if (impactType == ImpactType.EXTRA_BONUS) {
                        return accumulatedReward + bonusSymbol.extra().orElse(0);
                    } else if (impactType == ImpactType.MULTIPLY_REWARD) {
                        return accumulatedReward * bonusSymbol.rewardMultiplier().orElse(1.0);
                    } else {
                        return accumulatedReward;
                    }
                } else {
                    return accumulatedReward;
                }
            }).orElse(accumulatedReward);
        }, (accumulatedReward1, accumulatedReward2) -> {
            return accumulatedReward1 + accumulatedReward2;
        });

        return new PlayResults(matrix, totalRewards, appliedWinningCombo, appliedBonusSymbols);
    }

    private Optional<Double> getLinearPatternWinComboMultiplier(Set<String> symbolPattern, List<Map.Entry<Set<String>, WinCombination>> winningCombinations,
                                                                Symbol symbol, Map<Symbol, List<WinCombination>> appliedWinningCombo) {
        final var rewardMultipliers = new ArrayList<Double>();
        winningCombinations.stream().forEach(winningCombination -> {
            if (symbolPattern.containsAll(winningCombination.getKey())) {
                rewardMultipliers.add(winningCombination.getValue().rewardMultiplier());

                final var existingWinCombinationsApplied = Optional.ofNullable(appliedWinningCombo.get(symbol)).orElse(List.of());
                final var updatedWinCombinationsApplied = new ArrayList<WinCombination>(existingWinCombinationsApplied);
                updatedWinCombinationsApplied.add(winningCombination.getValue());
                appliedWinningCombo.put(symbol, updatedWinCombinationsApplied);

            }
        });
        return rewardMultipliers.stream().reduce((a, b) -> a * b);
    }
}
