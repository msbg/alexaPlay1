package com.edonica.decision.tree.states;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class StateIsItA extends StateBase {
    StateIsItA() {
        super(GameState.IsItA);

        addTransition(new Transition(IntentName.AMAZON_NoIntent, GameState.WhatIsIt));

        addTransition(new Transition(IntentName.AMAZON_YesIntent, GameState.Welcome){
            @Override
            String getTransitionPrefix(RequestContext request) {
                return "Great!";
            }
        });
    }

    @Override
    public String getText(RequestContext context) {
        DataNode node = context.getDataNode();
        return "Is it " + node.getValue() + "?";
    }
}
