package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.SpeechHelpers;

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

        //TODO - confirm user question

        String userAnimal = request.getSessionString(WhatIsItQuestion.class.toString());
        return SpeechHelpers.makeFullFatResponse("You asked: " + userQuestion + ".  And the answer for "+userAnimal+" would be?");
    }
}
