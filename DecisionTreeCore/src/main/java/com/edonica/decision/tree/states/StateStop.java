package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.GameState;
import com.edonica.decision.tree.model.IntentName;
import com.edonica.decision.tree.model.RequestContext;
import com.edonica.decision.tree.model.SessionKey;

public class StateStop extends StateBase {
    StateStop() {
        super(GameState.Stop);
    }

    @Override
    public String getText(RequestContext context) {
        return "Goodbye.";
    }
}
