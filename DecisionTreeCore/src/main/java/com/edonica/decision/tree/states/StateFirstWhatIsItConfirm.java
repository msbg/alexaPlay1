package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.*;

public class StateFirstWhatIsItConfirm extends StateBase {
    StateFirstWhatIsItConfirm() {
        super(GameState.FirstWhatIsItConfirm);

        addTransition(new Transition(IntentName.AMAZON_NoIntent, GameState.FirstWhatIsIt) {
            @Override
            String getTransitionPrefix(RequestContext context) {
                return "OK.  Please try saying it again.";
            }
        });

        addTransition(new Transition(IntentName.AMAZON_YesIntent, GameState.Welcome) {
            @Override
            String getTransitionPrefix(RequestContext request) {
                String newItem = request.getSessionString(SessionKey.ObjectName);
                return "You added " + newItem + ", your first object in this game.";
            }

            @Override
            void handleRequest(RequestContext request) {
                String newItem = request.getSessionString(SessionKey.ObjectName);

                DataNode dn = new DataNode();
                //First node is stored against the user ID rather than a GUID
                dn.setId(request.getUserId());
                dn.setValue(newItem);
                dn.save();
            }
        });

    }

    @Override
    public String getText(RequestContext request) {
        String newItem = request.getSessionString(SessionKey.ObjectName);
        return "I heard " + newItem + ", is that right?";
    }
}
