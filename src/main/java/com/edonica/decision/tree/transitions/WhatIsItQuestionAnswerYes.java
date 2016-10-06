package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class WhatIsItQuestionAnswerYes extends AbstractTransition {
    public WhatIsItQuestionAnswerYes() {
        super(GameState.WhatIsItQuestionAnswer, GameState.Welcome, IntentName.IntentYes);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return true;
    }


    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext context) {
        String userAnimal = context.getSessionString(WhatIsItQuestion.class.toString());
        String userQuestion = context.getSessionString(WhatIsItQuestionAnswer.class.toString());

        context.getDataNode().addAlternative( userQuestion, userAnimal, true );

        return StateGeneric.makeFullFatResponse("So Yes for " + userAnimal + " when asked " + userQuestion);
    }
}
