package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.*;

public class StateWhatIsItConfirm extends StateBase {
    StateWhatIsItConfirm() {
        super(GameState.WhatIsItConfirm);

        addTransition(new Transition(IntentName.AMAZON_NoIntent, GameState.WhatIsIt) {
            @Override
            String getTransitionPrefix(RequestContext context) {
                return "OK, let's try again.";
            }
        });

        addTransition(new Transition(IntentName.AMAZON_YesIntent, GameState.WhatQuestion));
    }

    @Override
    public String getText(RequestContext request) {
        String userThoughtOf = request.getSessionString(SessionKey.ObjectName);
        return "I heard "+userThoughtOf+ ".  Is that right?";
    }
}
