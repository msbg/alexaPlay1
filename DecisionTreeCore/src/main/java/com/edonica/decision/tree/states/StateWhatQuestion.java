package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.*;

public class StateWhatQuestion extends StateBase {
    StateWhatQuestion() {
        super(GameState.WhatQuestion);

        addTransition(new Transition(IntentName.IntentFreeText, GameState.WhatQuestionConfirm) {
            @Override
            void handleRequest(RequestContext request) {
                String userQuestion = request.getStringFromComponents();
                request.setSessionString(SessionKey.UserQuestion,userQuestion );
            }
        });
    }

    @Override
    public String getText(RequestContext request) {
        String original = request.getDataNode().getValue();
        String userThoughtOf = request.getSessionString(SessionKey.ObjectName);
        return "What Yes No question would distinguish "+original+" from " +userThoughtOf+ "?";
    }
}
