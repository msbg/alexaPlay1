package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class WhatIsItConfirmYes extends AbstractTransition {
    public WhatIsItConfirmYes() {
        super(GameState.WhatIsItConfirm, GameState.WhatQuestion);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.AMAZON_YesIntent);
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        String original = request.getDataNode().getValue();
        String userThoughtOf = request.getSessionString(SessionKey.ObjectName);
        return SpeechHelpers.makeFullFatResponse("What Yes No question would distinguish "+original+" from " +userThoughtOf+ "?");
    }
}
