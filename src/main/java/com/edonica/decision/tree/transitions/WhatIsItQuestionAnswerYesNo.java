package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

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
        String userAnimal = context.getSessionString(SessionKey.ObjectName);
        String userQuestion = context.getSessionString(SessionKey.UserQuestion);
        boolean answerForUserAnimal = context.isIntent(IntentName.IntentYes);

        context.getDataNode().addAlternative( userQuestion, userAnimal, answerForUserAnimal );
        context.resetState();

        String stringYesNo = (answerForUserAnimal?"Yes":"No");
        return SpeechHelpers.makeFullFatResponse("So "+stringYesNo +" for " + userAnimal + " when asked: " + userQuestion + ".  I'll remember that.  Say New Game to start again");
    }
}
