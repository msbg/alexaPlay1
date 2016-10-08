package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class WhatIsIt extends AbstractTransition {
    public WhatIsIt() {
        super(GameState.WhatIsIt, GameState.WhatIsItConfirm);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentFreeText);
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        String userThoughtOf = request.getStringFromComponents();
        request.setSessionString(SessionKey.ObjectName,userThoughtOf );

        return SpeechHelpers.makeFullFatResponse("I heard "+userThoughtOf+ ".  Is that right?");
    }
}
