package org.edano.assignment.scratchgame.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.edano.assignment.scratchgame.config.Config;
import org.edano.assignment.scratchgame.model.PlayResults;

import java.util.Optional;

public class Formatter {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module()); // enable Optional type compatibility
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        mapper.enable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
    }

    public static Optional<Config> readConfigJson(String json) {
        try {
            final var config = mapper.readValue(json, Config.class);
            return Optional.of(config);
        } catch (Exception err) {
            err.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<String> writePlayResultsAsJson(PlayResults playResults) {
        try {
            final var output = new SimpleOutput(playResults.getSimpleMatrix(), playResults.reward(),
                    playResults.getSimplifiedAppliedWinCombos(),
                    playResults.appliedBonusSymbols().stream().findAny().map(bonus-> bonus.name()).orElse(""));
            final var json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output);
            return Optional.of(json);
        } catch (Exception err) {
            return Optional.empty();
        }
    }
}
