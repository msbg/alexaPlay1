package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.*;

public class StateConfirmReset extends StateBase {
    StateConfirmReset() {
        super(GameState.ConfirmReset);

        addTransition(new Transition(IntentName.AMAZON_YesIntent, GameState.Welcome) {

            @Override
            void handleRequest(RequestContext context) {
                DataNode.resetUser(context);
            }

            @Override
            String getTransitionPrefix(RequestContext context) {
                return "Your data has been cleared.";
            }
        });

        addTransition(new Transition(IntentName.AMAZON_NoIntent, GameState.Welcome) {
            @Override
            String getTransitionPrefix(RequestContext context) {
                return "Self destruct cancelled.";
            }

        });
    }

    @Override
    public String getText(RequestContext request) {
        return "Do you want to reset your tree?  Say Yes to confirm";
    }
}
