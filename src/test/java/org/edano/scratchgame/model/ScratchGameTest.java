package org.edano.scratchgame.model;

import org.edano.assignment.scratchgame.model.ScratchGame;
import org.edano.assignment.scratchgame.json.Formatter;
import org.edano.assignment.scratchgame.model.MatrixBuilder;
import org.edano.assignment.scratchgame.model.Symbol;
import org.edano.assignment.scratchgame.model.WinCombination;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class ScratchGameTest {

    @Test
    public void testPlayWin() throws Exception {
        final var winCombinations = new ArrayList<WinCombination>();
        winCombinations.add(new WinCombination("same_symbol_3_times", 1.0, 3));
        winCombinations.add(new WinCombination("same_symbol_4_times", 1.5, 4));
        winCombinations.add(new WinCombination("same_symbol_5_times", 2.0, 5));
        winCombinations.add(new WinCombination("same_symbol_6_times", 3.0, 6));
        winCombinations.add(new WinCombination("same_symbol_7_times", 5.0, 7));
        winCombinations.add(new WinCombination("same_symbol_8_times", 10.0, 8));
        winCombinations.add(new WinCombination("same_symbol_9_times", 20.0, 9));
        winCombinations.add(new WinCombination("same_symbols_horizontally", 2.0, "linear_symbols", "horizontally_linear_symbols", List.of(
                List.of("0:0", "0:1", "0:2"),
                List.of("1:0", "1:1", "1:2"),
                List.of("2:0", "2:1", "2:2")
        )));
        winCombinations.add(new WinCombination("same_symbols_vertically", 2.0, "linear_symbols", "vertically_linear_symbols", List.of(
                List.of("0:0", "1:0", "2:0"),
                List.of( "0:1", "1:1", "2:1"),
                List.of( "0:2", "1:2", "2:2")
        )));
        winCombinations.add(new WinCombination("same_symbols_diagonally_left_to_right", 5.0, "linear_symbols", "ltr_diagonally_linear_symbols", List.of(
                List.of("0:0", "1:1", "2:2")
        )));
        winCombinations.add(new WinCombination("same_symbols_diagonally_right_to_left", 5.0, "linear_symbols", "rtl_diagonally_linear_symbols", List.of(
                List.of( "0:2", "1:1", "2:0")
        )));


        final var gameService = new ScratchGame(winCombinations);

        Symbol[][] scratchPad = {
                {Symbol.getStandard("A", 5.0 ), Symbol.getStandard("A", 5.0 ), Symbol.getStandard("B", 3.0 )},
                {Symbol.getStandard("A", 5.0 ), Symbol.getBonus("+1000", 1000), Symbol.getStandard("B", 3.0 )},
                {Symbol.getStandard("A", 5.0 ), Symbol.getStandard("A", 5.0 ), Symbol.getStandard("B", 3.0 )}
        };
        final var result = gameService.play(scratchPad, 100);
        assertEquals(3600, result.reward());
        assertIterableEquals( List.of(
                new WinCombination("same_symbol_5_times", 2.0, 5),
                new WinCombination("same_symbols_vertically", 2.0, "linear_symbols", "vertically_linear_symbols", List.of(
                        List.of("0:0", "1:0", "2:0"),
                        List.of( "0:1", "1:1", "2:1"),
                        List.of( "0:2", "1:2", "2:2")
                ))
        ), result.appliedWinningCombinations().get(Symbol.getStandard("A", 5.0 )));
        assertIterableEquals( List.of(
                new WinCombination("same_symbol_3_times", 1.0, 3),
                new WinCombination("same_symbols_vertically", 2.0, "linear_symbols", "vertically_linear_symbols", List.of(
                        List.of("0:0", "1:0", "2:0"),
                        List.of( "0:1", "1:1", "2:1"),
                        List.of( "0:2", "1:2", "2:2")
                ))
        ), result.appliedWinningCombinations().get(Symbol.getStandard("B", 3.0 )));

        assertEquals( List.of(Symbol.getBonus("+1000", 1000)), result.appliedBonusSymbols());
    }

    @Test
    public void testPlayLose() throws Exception {
        final var winCombinations = new ArrayList<WinCombination>();
        winCombinations.add(new WinCombination("same_symbol_3_times", 1.0, 3));
        winCombinations.add(new WinCombination("same_symbol_4_times", 1.5, 4));
        winCombinations.add(new WinCombination("same_symbol_5_times", 2.0, 5));
        winCombinations.add(new WinCombination("same_symbol_6_times", 3.0, 6));
        winCombinations.add(new WinCombination("same_symbol_7_times", 5.0, 7));
        winCombinations.add(new WinCombination("same_symbol_8_times", 10.0, 8));
        winCombinations.add(new WinCombination("same_symbol_9_times", 20.0, 9));
        winCombinations.add(new WinCombination("same_symbols_horizontally", 2.0, "linear_symbols", "horizontally_linear_symbols", List.of(
                List.of("0:0", "0:1", "0:2"),
                List.of("1:0", "1:1", "1:2"),
                List.of("2:0", "2:1", "2:2")
        )));
        winCombinations.add(new WinCombination("same_symbols_vertically", 2.0, "linear_symbols", "vertically_linear_symbols", List.of(
                List.of("0:0", "1:0", "2:0"),
                List.of( "0:1", "1:1", "2:1"),
                List.of( "0:2", "1:2", "2:2")
        )));
        winCombinations.add(new WinCombination("same_symbols_diagonally_left_to_right", 5.0, "linear_symbols", "ltr_diagonally_linear_symbols", List.of(
                List.of("0:0", "1:1", "2:2")
        )));
        winCombinations.add(new WinCombination("same_symbols_diagonally_right_to_left", 5.0, "linear_symbols", "rtl_diagonally_linear_symbols", List.of(
                List.of( "0:2", "1:1", "2:0")
        )));

        final var gameService = new ScratchGame(winCombinations);

        Symbol[][] scratchPad = {
                {Symbol.getStandard("A", 5.0 ), Symbol.getStandard("B", 3.0 ), Symbol.getStandard("C", 2.5 )},
                {Symbol.getStandard("E", 1.2 ), Symbol.getStandard("B", 3.0 ), Symbol.getBonus("5x", 5.0)},
                {Symbol.getStandard("F", 1.0 ), Symbol.getStandard("D", 2.0 ), Symbol.getStandard("C", 2.5 )}
        };
        final var results = gameService.play(scratchPad, 100);
        assertEquals(0, results.reward());
        assertEquals(true, results.appliedWinningCombinations().isEmpty());
        assertEquals(true, results.appliedBonusSymbols().isEmpty());
    }

    @Test
    public void testMultipleGamesFromConfig() throws Exception {
        final var config = Formatter.readConfigJson(new String(getClass().getClassLoader().getResourceAsStream("config.json").readAllBytes())).get();
        for(int game=0; game < 99; game++) {
            final var matrix = new MatrixBuilder(config).build().symbols();
            final var winCombinations = config.winCombinations().entrySet().stream().map( entry -> {
                return new WinCombination(entry.getValue(), entry.getKey());
            }).toList();
            final var bet = new Random().nextInt(1000);
            final var gameService = new ScratchGame(winCombinations);
            final var gameResults = gameService.play(matrix, bet);
            final var display = Formatter.writePlayResultsAsJson(gameResults);
            assertEquals(display.isPresent(), true);
        }
    }
}
