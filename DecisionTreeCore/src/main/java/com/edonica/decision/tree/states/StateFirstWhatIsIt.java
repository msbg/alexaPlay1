package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.*;

public class StateFirstWhatIsIt extends StateBase {

    StateFirstWhatIsIt() {
        super(GameState.FirstWhatIsIt);
        addTransition(new Transition(IntentName.IntentFreeText, GameState.FirstWhatIsItConfirm) {
            @Override
            protected boolean isValidTransition(RequestContext context) {
                return context.getDataNode()==null;
            }

            @Override
            protected void handleRequest(RequestContext request) {
                String newItem = request.getStringFromComponents();
                request.setSessionString(SessionKey.ObjectName, newItem);
            }
        });

        }

    @Override
    public String getText(RequestContext context) {
        return "Please think of an object and tell me what it is.  Using more than one word to describe it will help my voice recognition.";
    }
}
