package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class WhatQuestionConfirmYes extends AbstractTransition {
    public WhatQuestionConfirmYes() {
        super(GameState.WhatQuestionConfirm, GameState.WhatIsItQuestionAnswer);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.AMAZON_YesIntent);
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        String userQuestion = request.getSessionString(SessionKey.UserQuestion);
        String userAnimal = request.getSessionString(SessionKey.ObjectName);
        return SpeechHelpers.makeFullFatResponse("You asked: " + userQuestion + ".  The answer for "+userAnimal+" would be?");
    }
}
