package com.edonica.decision.tree.states;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.GameState;
import com.edonica.decision.tree.model.IntentName;
import com.edonica.decision.tree.model.RequestContext;
import com.edonica.decision.tree.model.SessionKey;

import java.util.ArrayList;
import java.util.List;

public abstract class StateBase {
    public StateBase(GameState gameState) {
        this.gameState = gameState;
        this.transitions = new ArrayList<Transition>();
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public abstract String getText(RequestContext context);

    void addTransition(Transition transition) {
        transitions.add(transition);
    }

    public String handleRequest(RequestContext request) {
        IntentName intent = request.getIntent();

        for (Transition transition : transitions) {

            //Find the one for this intent
            if( transition.handlesIntent(intent)) {

                //Check other validity constraints
                if( transition.isValidTransition(request)) {

                    //Perform the actual action
                    transition.handleRequest(request);

                    //Get any perfix text
                    String actionText = transition.getTransitionPrefix(request);

                    if( transition.isReset()) {
                        request.resetState();
                    } else {
                        //Update our state
                        request.setSessionString(SessionKey.GameState, transition.getTo().toString());
                    }

                    return actionText;
                }
            }
        }
        return "Sorry, I have no idea what you're talking about.";
    }

    final GameState gameState;
    final List<Transition> transitions;
}
