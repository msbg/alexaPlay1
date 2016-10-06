package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class WhatIsItQuestionAnswerYesNo extends AbstractTransition {
    public WhatIsItQuestionAnswerYesNo() {
        super(GameState.WhatIsItQuestionAnswer, GameState.Welcome);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentYes) || context.isIntent(IntentName.IntentNo);
    }


    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext context) {
        String userAnimal = context.getSessionString(WhatIsItQuestion.class.toString());
        String userQuestion = context.getSessionString(WhatIsItQuestionAnswer.class.toString());

        boolean answerForUserAnimal = context.isIntent(IntentName.IntentYes);

        context.getDataNode().addAlternative( userQuestion, userAnimal, answerForUserAnimal );

        context.resetState();

        return StateGeneric.makeFullFatResponse("So "+(answerForUserAnimal?"Yes":"No") +" for " + userAnimal + " when asked " + userQuestion + ".  Say New Game to start again");
    }
}
