package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class WhatIsItQuestionAnswer extends AbstractTransition {
    public WhatIsItQuestionAnswer() {
        super(GameState.WhatIsItQuestion, GameState.WhatIsItQuestionAnswer);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentFreeText);
    }


    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {

        String userQuestion = request.getStringFromComponents();
        request.setSessionString(WhatIsItQuestionAnswer.class.toString(),userQuestion );

        String userAnimal = request.getSessionString(WhatIsItQuestion.class.toString());

        return StateGeneric.makeFullFatResponse("And the answer for "+userAnimal+" would be?");
    }
}
