package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class NewGameSingleData extends AbstractTransition {
    public NewGameSingleData() {
        super(GameState.Welcome, GameState.IsItA, IntentName.IntentNewGame);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.getDataNode() !=null && !context.getDataNode().hasChildren();
    }
    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return StateGeneric.makeFullFatResponse("Is it a " + request.getDataNode().getValue());
    }
}
