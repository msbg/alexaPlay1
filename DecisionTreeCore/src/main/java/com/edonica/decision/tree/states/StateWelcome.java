package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.GameState;
import com.edonica.decision.tree.model.IntentName;
import com.edonica.decision.tree.model.RequestContext;


class StateWelcome extends StateBase {
    StateWelcome() {
        super(GameState.Welcome);
        addTransition(new Transition(IntentName.IntentNewGame, GameState.FirstWhatIsIt) {
            @Override
            protected boolean isValidTransition(RequestContext context) {
                return context.getDataNode() == null;
            }

            @Override
            String getTransitionPrefix(RequestContext request) {
                return "OK.  My mind is blank and I need to learn things.";
            }
        });

        addTransition(new Transition(IntentName.IntentNewGame, GameState.IsItA) {
            @Override
            protected boolean isValidTransition(RequestContext context) {
                return context.getDataNode() !=null && !context.getDataNode().hasChildren();
            }

            @Override
            String getTransitionPrefix(RequestContext request) {
                return "Well I only know one thing right now so this game will be quite short.  Please think of an object and answer this question.";
            }
        });

        addTransition(new Transition(IntentName.IntentNewGame, GameState.Question) {
            @Override
            protected boolean isValidTransition(RequestContext context) {
                return context.getDataNode() !=null && context.getDataNode().hasChildren();
            }

            @Override
            String getTransitionPrefix(RequestContext request) {
                return "Please think of an object and answer this question.";
            }
        });

    }

    @Override
    public String getText(RequestContext context) {
        return "Say New Game to play";
    }
}
