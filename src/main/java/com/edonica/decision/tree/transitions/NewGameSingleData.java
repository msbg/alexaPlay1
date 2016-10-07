package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.SpeechHelpers;

public class NewGameSingleData extends AbstractTransition {
    public NewGameSingleData() {
        super(GameState.Welcome, GameState.IsItA);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentNewGame)
                && context.getDataNode() !=null
                && !context.getDataNode().hasChildren();
    }
    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return SpeechHelpers.makeFullFatResponse("OK.  I only know about one thing right now.  Is it " + request.getDataNode().getValue());
    }
}
