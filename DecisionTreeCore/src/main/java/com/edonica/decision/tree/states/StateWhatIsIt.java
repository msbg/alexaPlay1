package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.*;

public class StateWhatIsIt extends StateBase {
    StateWhatIsIt() {
        super(GameState.WhatIsIt);

        addTransition(new Transition(IntentName.IntentFreeText, GameState.WhatIsItConfirm) {
            @Override
            void handleRequest(RequestContext request) {
                String userThoughtOf = request.getStringFromComponents();
                request.setSessionString(SessionKey.ObjectName, userThoughtOf);
            }
        });
    }

    @Override
    public String getText(RequestContext context) {
        return "What was it?  Add an, or a, to help me understand.";
    }
}
