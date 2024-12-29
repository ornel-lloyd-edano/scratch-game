package org.edano.assignment;

import org.edano.assignment.scratchgame.model.ScratchGame;
import org.edano.assignment.scratchgame.json.Formatter;
import org.edano.assignment.scratchgame.model.MatrixBuilder;
import org.edano.assignment.scratchgame.model.WinCombination;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public record GameInput(String configJson, Double betAmount) {}

    public static void main(String[] args) {

        final var gameInput = getInput(args);

        Formatter.readConfigJson(gameInput.configJson).ifPresentOrElse( config -> {
            final var winCombinations = config.winCombinations().entrySet().stream().map( entry -> {
                return new WinCombination(entry.getValue(), entry.getKey());
            }).toList();
            final var game = new ScratchGame(winCombinations);
            final var matrix = new MatrixBuilder(config).build();
            final var gameResult = game.play(matrix.symbols(), gameInput.betAmount);
            Formatter.writePlayResultsAsJson(gameResult).ifPresentOrElse( gameResultDisplay -> {
                System.out.println(gameResultDisplay);
            }, ()-> System.out.println("Failed to display game results"));
        }, ()-> System.out.println(String.format("Failed to parse the config file contents", gameInput.configJson)));
    }
    private static GameInput getInput(String[] args) {
        String configContent = null;
        Double betAmount = 0.0;
        for(int idx=0; idx < args.length; idx++) {
            if (args[idx].equalsIgnoreCase("--config")) {
                final var configLocation = args[idx + 1];
                Path path = Paths.get(configLocation);
                try {
                    configContent = Files.readString(path);
                } catch (IOException err) {
                    System.out.println(String.format("File %s does not exist", configLocation));
                }
            }

            if (args[idx].equalsIgnoreCase("--betting-amount")) {
                String betAmountArg = args[idx + 1];
                try {
                    betAmount = Double.parseDouble(betAmountArg);
                } catch (NumberFormatException err) {
                    System.out.println(String.format("Invalid betting amount: %s", betAmountArg));
                }
            }
        }
        return new GameInput(configContent, betAmount);
    }
}