package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.*;

public class StateWhatIsItQuestionAnswer extends StateBase {
    StateWhatIsItQuestionAnswer() {
        super(GameState.WhatIsItQuestionAnswer);

        addTransition(new Transition(new IntentName[]{IntentName.AMAZON_YesIntent,IntentName.AMAZON_NoIntent}, GameState.Welcome){

            @Override
            void handleRequest(RequestContext context) {
                String userAnimal = context.getSessionString(SessionKey.ObjectName);
                String userQuestion = context.getSessionString(SessionKey.UserQuestion);
                boolean answerForUserAnimal = context.isIntent(IntentName.AMAZON_YesIntent);

                context.getDataNode().addAlternative( userQuestion, userAnimal, answerForUserAnimal );
            }

            @Override
            String getTransitionPrefix(RequestContext context) {
                String userAnimal = context.getSessionString(SessionKey.ObjectName);
                String userQuestion = context.getSessionString(SessionKey.UserQuestion);
                boolean answerForUserAnimal = context.isIntent(IntentName.AMAZON_YesIntent);

                String stringYesNo = (answerForUserAnimal?"Yes":"No");
                return "So "+stringYesNo +" for " + userAnimal + " when asked: " + userQuestion + ".  I'll remember that.";
            }

            @Override
            boolean isReset() {
                return true;
            }
        });

    }

    @Override
    public String getText(RequestContext context) {
        String userAnimal = context.getSessionString(SessionKey.ObjectName);
        String userQuestion = context.getSessionString(SessionKey.UserQuestion);
        return "You asked: " + userQuestion + ".  The answer for "+userAnimal+" would be?";
    }
}
