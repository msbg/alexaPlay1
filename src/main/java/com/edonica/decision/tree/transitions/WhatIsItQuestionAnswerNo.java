package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class WhatIsItQuestionAnswerNo extends AbstractTransition {
    public WhatIsItQuestionAnswerNo() {
        super(GameState.WhatIsItQuestionAnswer, GameState.Welcome, IntentName.IntentNo);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return true;
    }


    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext context) {
        String userAnimal = context.getSessionString(WhatIsItQuestion.class.toString());
        String userQuestion = context.getSessionString(WhatIsItQuestionAnswer.class.toString());

        context.getDataNode().addAlternative( userQuestion, userAnimal, false );

        return StateGeneric.makeFullFatResponse("So No for " + userAnimal + " when asked " + userQuestion);
    }
}
