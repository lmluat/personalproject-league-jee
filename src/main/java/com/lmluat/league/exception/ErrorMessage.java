package com.lmluat.league.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

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


    private ErrorMessage() {

    }

    static Map<String, String> errorKeyAndMessageMap() {
        Map<String, String> errorMap = new HashMap<>();

        errorMap.put(TOURNAMENT_NOT_FOUND,KEY_TOURNAMENT_NOT_FOUND);

        return errorMap;
    }
}
