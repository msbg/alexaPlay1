package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class WhatIsItQuestion extends AbstractTransition {
    public WhatIsItQuestion() {
        super(GameState.WhatIsIt, GameState.WhatIsItQuestion);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentFreeText);
    }
    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        String userThoughtOf = request.getStringFromComponents();
        request.setSessionString(WhatIsItQuestion.class.toString(),userThoughtOf );

        String original = request.getDataNode().getValue();

        return StateGeneric.makeFullFatResponse("What Yes No question would distinguish "+original+" from " +userThoughtOf+ "?");
    }
}
