package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;


public class NewGameNoData extends AbstractTransition {
    public NewGameNoData() {
        super(GameState.Welcome, GameState.FirstWhatIsIt, IntentName.IntentNewGame);
    }

    @Override
    protected boolean isValidTransition(DataNode dn) {
        return dn==null;
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return StateGeneric.makeFullFatResponse("What were you thinking of?");
    }
}
