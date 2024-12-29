package org.edano.scratchgame.config;

import org.edano.assignment.scratchgame.config.*;
import org.edano.assignment.scratchgame.json.Formatter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadConfigJsonTest {

    @Test
    public void testReadConfigJson() throws Exception {
        final var configJsonBytes = getClass().getClassLoader().getResourceAsStream("config.json").readAllBytes();
        final var configJson = new String(configJsonBytes);
        final var config = Formatter.readConfigJson(configJson);

        final var symbols = new HashMap<String, SymbolProps>();
        symbols.put("A", new SymbolProps(5.0, "standard"));
        symbols.put( "B", new SymbolProps(3.0, "standard"));
        symbols.put("C", new SymbolProps(2.5, "standard"));
        symbols.put("D", new SymbolProps(2.0, "standard"));
        symbols.put( "E", new SymbolProps(1.2, "standard"));
        symbols.put( "F", new SymbolProps(1.0, "standard"));
        symbols.put( "10x", new SymbolProps(10.0, "bonus", "multiply_reward"));
        symbols.put( "5x", new SymbolProps(5.0, "bonus", "multiply_reward"));
        symbols.put( "+1000", new SymbolProps(1000, "bonus", "extra_bonus"));
        symbols.put("+500", new SymbolProps(500, "bonus", "extra_bonus"));
        symbols.put( "MISS", new SymbolProps());

        final var probabilities = new ProbabilitiesProps(
                List.of(
                      new StandardSymbolProbabilitiesProps(0, 0, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbabilitiesProps(0, 1, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbabilitiesProps(0, 2, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbabilitiesProps(1, 0, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbabilitiesProps(1, 1, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbabilitiesProps(1, 2, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbabilitiesProps(2, 0, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbabilitiesProps(2, 1, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbabilitiesProps(2, 2, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6))
                        ),
                new BonusSymbolsProps(Map.of("10x", 1, "5x", 2, "+1000", 3, "+500", 4, "MISS", 5))
        );

        final var winCombinations = new HashMap<String, WinCombinationProps>();
        winCombinations.put("same_symbol_3_times", new WinCombinationProps(1.0, "same_symbols", 3, "same_symbols"));
        winCombinations.put("same_symbol_4_times", new WinCombinationProps(1.5, "same_symbols", 4, "same_symbols"));
        winCombinations.put("same_symbol_5_times", new WinCombinationProps(2.0, "same_symbols", 5, "same_symbols"));
        winCombinations.put("same_symbol_6_times", new WinCombinationProps(3.0, "same_symbols", 6, "same_symbols"));
        winCombinations.put("same_symbol_7_times", new WinCombinationProps(5.0, "same_symbols", 7, "same_symbols"));
        winCombinations.put("same_symbol_8_times", new WinCombinationProps(10.0, "same_symbols", 8, "same_symbols"));
        winCombinations.put("same_symbol_9_times", new WinCombinationProps(20.0, "same_symbols", 9, "same_symbols"));

        winCombinations.put("same_symbols_horizontally", new WinCombinationProps(2.0, "linear_symbols", "horizontally_linear_symbols",
                List.of(
                        List.of("0:0", "0:1", "0:2"),
                        List.of("1:0", "1:1", "1:2"),
                        List.of("2:0", "2:1", "2:2")
                )));
        winCombinations.put("same_symbols_vertically", new WinCombinationProps(2.0, "linear_symbols", "vertically_linear_symbols",
                List.of(
                        List.of("0:0", "1:0", "2:0"),
                        List.of( "0:1", "1:1", "2:1"),
                        List.of( "0:2", "1:2", "2:2")
                )));
        winCombinations.put("same_symbols_diagonally_left_to_right", new WinCombinationProps(5.0, "linear_symbols", "ltr_diagonally_linear_symbols",
                List.of(
                        List.of("0:0", "1:1", "2:2")
                )));
        winCombinations.put("same_symbols_diagonally_right_to_left", new WinCombinationProps(5.0, "linear_symbols", "rtl_diagonally_linear_symbols",
                List.of(
                        List.of( "0:2", "1:1", "2:0")
                )));

        final var expected = new Config(3, 3, symbols,
                probabilities, winCombinations
        );
        assertEquals(config.get(), expected);
    }
}
