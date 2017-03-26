package com.edonica.alexa.ghost.model;


import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

public class GhostStateMachine {
    public GhostStateContext getGhostStateContext() {
        return ghostStateContext;
    }

    final GhostStateContext ghostStateContext;
    
    //Return vars for Alexa
    private String speechText;
    private boolean endConversation;
    
    Words words = new Words();

    public GhostStateMachine(GhostStateContext ghostStateContext) {
        this.ghostStateContext = ghostStateContext;
    }


    public void fireTrigger(AlexaIntent trigger) {
        StateMachineConfig<GhostGameState, AlexaIntent> stateMachineConfig = getStateMachineConfig();
        StateMachine<GhostGameState, AlexaIntent> stateMachine = new StateMachine<>(ghostStateContext.getGhostGameState(), stateMachineConfig);
        stateMachine.fire(trigger);
        ghostStateContext.setGhostGameState(stateMachine.getState());
    }
    
    
    private StateMachineConfig<GhostGameState, AlexaIntent> getStateMachineConfig() {
        StateMachineConfig<GhostGameState, AlexaIntent> ghostStateMachineConfig = new StateMachineConfig<>();
        ghostStateMachineConfig.configure(GhostGameState.GameNotYetStarted)
                .permit(AlexaIntent.Session_start, GhostGameState.GameSessionStarted);
        ghostStateMachineConfig.configure(GhostGameState.GameSessionStarted)
                .permit(AlexaIntent.Session_launch, GhostGameState.NewGame)
                .onEntry(this::newGame);
        ghostStateMachineConfig.configure(GhostGameState.NewGame)
                .permit(AlexaIntent.Yes, GhostGameState.GameInProgress)
                .permit(AlexaIntent.No, GhostGameState.GameInProgress)
                .onEntryFrom(AlexaIntent.Yes, this::humanStart)
                .onEntryFrom(AlexaIntent.No, this::alexaTurn);
        ghostStateMachineConfig.configure(GhostGameState.GameInProgress)
                .permitDynamic(AlexaIntent.newLetter, this::alexaTurn)
                .permit(AlexaIntent.Challenge, GhostGameState.EndGame)
                .onEntryFrom(AlexaIntent.Challenge, this::alexaRespondChallenge);
        ghostStateMachineConfig.configure(GhostGameState.Alexa_request_challenge)
                .permitDynamic(AlexaIntent.HumanChallengeReponse, this::humanChallengeResponse);
        ghostStateMachineConfig.configure(GhostGameState.EndGame)
                .permit(AlexaIntent.Yes, GhostGameState.NewGame)
                .onEntryFrom(AlexaIntent.Yes, this::newGame)
                .permit(AlexaIntent.No, GhostGameState.Finish)
                .onEntryFrom(AlexaIntent.No, this::goodBye);
        return ghostStateMachineConfig;
    }
    
    private void newGame() {
        speechText = "Would you like to start?";
    }
    private void humanStart() {
        speechText = "You start.  What is your letter?";
    }
    private GhostGameState alexaTurn() {
        
        Character newLetter = getNextLetter();
        if(newLetter==null) {
            if(isWord()) {
                speechText = "I think that's a word - it's " + ghostStateContext.getWordSoFar() + ". Would you like to play again?";
                return  GhostGameState.EndGame;
            } else {
                speechText = "Challenge!  I don't think that starts a word.  What word were you thinking of? ";
                return GhostGameState.Alexa_request_challenge;
            }
        }else {
            ghostStateContext.setWordSoFar(ghostStateContext.getWordSoFar() + newLetter);
            speechText = "I'll add the letter " + newLetter + ". What is your letter?";
            return GhostGameState.GameInProgress;
        }
        
    }

    private void alexaRespondChallenge() {
        String word = getFullWord();
        speechText = "I was thinking of the word " + word +  ".  Would you like to play again?";
    }
    
    


    private GhostGameState humanChallengeResponse() {
        //if word given exists then:
        //GhostGameState.Human_won;
        //otherwise..
        speechText = "That word doesn't start with... Would you like to play again?";
        return GhostGameState.EndGame;
    }
    
    private void goodBye() {
        speechText = "OK!  Goodbye";
        endConversation = true;
    }

    public String getSpeechText() {
        return speechText;
    }
    
    enum GhostGameState {
        GameNotYetStarted,
        GameSessionStarted,
        NewGame,
        GameInProgress,
        Alexa_request_challenge,
        EndGame,
        Finish
    }


    public boolean isEndConversation() {
        return endConversation;
    }

    private Character getNextLetter() {
        String word = words.getPossibleWord(ghostStateContext.getWordSoFar());
        if(word.length()>ghostStateContext.getWordSoFar().length()) {
            String nextLetter = word.substring(ghostStateContext.getWordSoFar().length());
            return nextLetter.toCharArray()[0];
        }
        return null;
    }

    private boolean isWord() {
        return words.isWord(this.ghostStateContext.getWordSoFar());
    }


    private String getFullWord() {
        String word = words.getPossibleWord(this.ghostStateContext.getWordSoFar());
        return word;
    }

}
