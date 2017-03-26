package com.edonica.alexa.ghost.model;

public class GhostStateContext {
    public static final String GHOST_STATE_CONTEXT = "GHOST_STATE_CONTEXT";

    public GhostStateMachine.GhostGameState getGhostGameState() {
        return ghostGameState;
    }

    public void setGhostGameState(GhostStateMachine.GhostGameState ghostGameState) {
        this.ghostGameState = ghostGameState;
    }

    public String getWordSoFar() {
        return wordSoFar;
    }

    public void setWordSoFar(String wordSoFar) {
        this.wordSoFar = wordSoFar;
    }

    //Actual game state
    private GhostStateMachine.GhostGameState ghostGameState = GhostStateMachine.GhostGameState.GameNotYetStarted;
    private String wordSoFar;
}
