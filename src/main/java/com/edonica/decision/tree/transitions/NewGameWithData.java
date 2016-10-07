package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.SpeechHelpers;

public class NewGameWithData extends AbstractTransition {
    public NewGameWithData() {
        super(GameState.Welcome, GameState.Question);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentNewGame)
                && context.getDataNode() != null
                && context.getDataNode().hasChildren();
    }
    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return SpeechHelpers.makeFullFatResponse("Please think of an object and answer this question: " + request.getDataNode().getQuestion() + "?");
    }
}
