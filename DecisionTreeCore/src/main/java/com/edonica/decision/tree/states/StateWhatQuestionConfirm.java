package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.*;

public class StateWhatQuestionConfirm extends StateBase {
    StateWhatQuestionConfirm() {
        super(GameState.WhatQuestionConfirm);
        addTransition(new Transition(IntentName.AMAZON_NoIntent, GameState.WhatQuestion) {
            @Override
            String getTransitionPrefix(RequestContext context) {
                return "OK, let's try again.";
            }
        });
        addTransition(new Transition(IntentName.AMAZON_YesIntent, GameState.WhatIsItQuestionAnswer));
    }

    @Override
    public String getText(RequestContext request) {
        String userQuestion = request.getSessionString(SessionKey.UserQuestion);
        return "I heard: " + userQuestion + ".  Is that right?";
    }
}
