package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class WhatQuestion extends AbstractTransition {
    public WhatQuestion() {
        super(GameState.WhatQuestion, GameState.WhatQuestionConfirm);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentFreeText);
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        String userQuestion = request.getStringFromComponents();
        request.setSessionString(SessionKey.UserQuestion,userQuestion );
        return SpeechHelpers.makeFullFatResponse("I heard: " + userQuestion + ".  Is that right?");
    }
}
