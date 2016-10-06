package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;


public class NewGameNoData extends AbstractTransition {
    public NewGameNoData() {
        super(GameState.Welcome, GameState.FirstWhatIsIt);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentNewGame) && context.getDataNode()==null;
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return StateGeneric.makeFullFatResponse("What were you thinking of?");
    }
}
