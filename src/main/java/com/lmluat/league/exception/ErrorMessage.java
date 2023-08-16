package com.lmluat.league.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

    public final static String TOURNAMENT_NAME_NULL_OR_BLANK = "Tournament name cannot be null or blank";
    public final static String KEY_TOURNAMENT_NAME_NULL_OR_BLANK = "exception.input.validation.tournament.name.blank.or.null";

    public static final String TOURNAMENT_NAME_LENGTH_CONSTRAINT = "Tournament Name must have at least 3 characters";
    public static final String KEY_TOURNAMENT_NAME_LENGTH_CONSTRAINT = "exception.input.validation.tournament.name.length.invalid";
    public final static String TOURNAMENT_NOT_FOUND = "Tournament not found";
    public final static String KEY_TOURNAMENT_NOT_FOUND = "exception.resource.not.found.tournament";
    public final static String TOURNAMENT_NAME_DUPLICATED = "Tournament Name is duplicated";
    public final static String KEY_TOURNAMENT_NAME_DUPLICATED = "exception.input.validation.tournament.name.duplicated";

    public final static String TEAM_NOT_FOUND = "Team not found";
    public final static String KEY_TEAM_NOT_FOUND = "exception.resource.not.found.team";

    public final static String TEAM_NAME_DUPLICATED = "Team Name is duplicated";
    public final static String KEY_TEAM_NAME_DUPLICATED = "exception.input.validation.team.name.duplicated";

    public final static String PLAYER_INGAME_NAME_DUPLICATED = "Player Ingame Name is duplicated";
    public final static String KEY_PLAYER_INGAME_NAME_DUPLICATED = "exception.input.validation.player.ingame.name.duplicated";
    public static final String COACH_NOT_FOUND = "Coach not found";
    public static final String KEY_COACH_NOT_FOUND = "exception.resource.not.found.coach";
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String KEY_RESOURCE_NOT_FOUND = "exception.resource.not.found.resource";

    public static final String TEAM_DETAIL_NOT_FOUND = "Team Detail not found";
    public static final String KEY_TEAM_DETAIL_NOT_FOUND = "exception.resource.not.found.team.detail";

    public static final String MATCH_NOT_FOUND = "Match not found";
    public static final String KEY_MATCH_NOT_FOUND = "exception.resource.not.found.match";

    public static final String TWO_TEAM_DUPLICATED = "Two team duplicated";
    public static final String KEY_TWO_TEAM_DUPLICATED = "exception.input.validation.two.team.duplicated";

    public static final String INPUT_DATA_NULL_OR_BLANK = "Input data cannot be null or blank";
    public static final String KEY_INPUT_DATA_NULL_OR_BLANK = "exception.input.validation.input.data";

    public static final String INVALID_GAME_ID = "Invalid Game Id";
    public static final String KEY_INVALID_GAME_ID = "Invalid Game Id";

    public static final String INVALID_INPUT_DATA = "Invalid input data";
    public static final String KEY_INVALID_INPUT_DATA = "exception.input.validation.input.data";

    public static final String MATCH_DETAIL_NOT_FOUND = "Match Detail not found";
    public static final String KEY_MATCH_DETAIL_NOT_FOUND = "exception.resource.not.found.match.detail";
    public static final String TOURNAMENT_NAME_CONSTRAINT = "Tournament name must start with 'minhluat-' and have at least 3 characters and at most 1000 characters.";
    public static final String KEY_TOURNAMENT_NAME_CONSTRAINT = "exception.input.validation.tournament.name.constraint";

    private ErrorMessage() {

    }

    public static Map<String, String> errorKeyAndMessageMap() {
        Map<String, String> errorMap = new HashMap<>();

        errorMap.put(TOURNAMENT_NAME_NULL_OR_BLANK,KEY_TOURNAMENT_NAME_NULL_OR_BLANK);
        errorMap.put(TOURNAMENT_NAME_LENGTH_CONSTRAINT,KEY_TOURNAMENT_NAME_LENGTH_CONSTRAINT);
        errorMap.put(INPUT_DATA_NULL_OR_BLANK,KEY_INPUT_DATA_NULL_OR_BLANK);
        errorMap.put(INVALID_GAME_ID,KEY_INVALID_GAME_ID);
        errorMap.put(TOURNAMENT_NAME_CONSTRAINT,KEY_TOURNAMENT_NAME_CONSTRAINT);

        return errorMap;
    }
}
